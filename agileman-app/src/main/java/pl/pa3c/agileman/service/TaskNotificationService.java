package pl.pa3c.agileman.service;

import static javax.mail.Message.RecipientType.TO;
import static pl.pa3c.agileman.service.EmailConstant.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.sun.mail.smtp.SMTPTransport;

import lombok.extern.slf4j.Slf4j;
import pl.pa3c.agileman.TaskUpdateEvent;
import pl.pa3c.agileman.model.task.Task;
import pl.pa3c.agileman.model.task.Type;
import pl.pa3c.agileman.repository.usertask.UserTaskRepository;

@Service
@Slf4j
public class TaskNotificationService {

	@Autowired
	private UserTaskRepository userTaskRepository;

	private final StringBuilder sb = new StringBuilder();

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
	@Async
	public void afterTransaction(TaskUpdateEvent event) throws IllegalAccessException, MessagingException {

		final List<String> emails = userTaskRepository
				.findAllByTaskIdAndTypeIn(event.getNewState().getId(), Arrays.asList(Type.OBSERVER)).stream()
				.map(x -> x.getUser().getEmail()).collect(Collectors.toList());

		if (emails.isEmpty()) {
			return;
		}

		final Map<String, List<String>> taskDifferences = findTaskDifferences(event.getOldState(), event.getNewState());
		final String content = buildHTMLContent(event.getOldState(), taskDifferences);
		
		sendNotificationEmail(content, emails);
	}

	public String buildHTMLContent(Task task, Map<String, List<String>> changes) {
		sb.setLength(0);

		sb.append(HTML_EMAIL_BEGIN);
		sb.append("<h5>");
		sb.append(task.getTitle());
		sb.append("</h5><br>");
		sb.append(HTML_TABLE_START);
		sb.append(HTML_HEADER_ROW);
		changes.forEach((key, value) -> sb.append("<tr><td>").append(splitCamelCase(key)).append("</td><td>")
				.append(value.get(0)).append("</td><td>").append(value.get(1)).append("</td><tr>"));
		sb.append(HTML_TABLE_END).append(HTML_EMAIL_FOOTER);

		return sb.toString();
	}

	public void sendNotificationEmail(String text, List<String> emails) throws MessagingException {
		final Message message = createEmail(text, buildAddressesString(emails));
		final SMTPTransport smtpTransport = (SMTPTransport) getEmailSession()
				.getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
		smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
		smtpTransport.sendMessage(message, message.getAllRecipients());
		smtpTransport.close();
	}

	private String buildAddressesString(List<String> emails) {
		sb.setLength(0);

		emails.forEach(x -> {
			sb.append(x).append(",");
		});

		return sb.toString().substring(0, sb.length() - 1);
	}

	private Message createEmail(String htmlContent, String addresses) throws MessagingException {
		final Message message = new MimeMessage(getEmailSession());
		message.setFrom(new InternetAddress(FROM_EMAIL));
		message.setRecipients(TO, InternetAddress.parse(addresses));
		message.setSubject(EMAIL_SUBJECT);
		message.setContent(htmlContent, HTML_CONTENT);
		message.setSentDate(new Date());
		message.saveChanges();
		return message;
	}

	private Map<String, List<String>> findTaskDifferences(Task oldTask, Task newTask) throws IllegalAccessException {

		final Map<String, List<String>> notMatchProperties = new HashMap<>();

		List<Field> fields = this.getFields(oldTask);

		for (Field f : fields) {
			f.setAccessible(true);
			final Object oldValue = f.get(oldTask);
			final Object newValue = f.get(newTask);

			if (oldValue == null) {
				if (newValue != null) {
					notMatchProperties.put(f.getName(), Arrays.asList(oldValue.toString(), newValue.toString()));
				}
				continue;
			}

			if (!oldValue.equals(newValue)) {
				notMatchProperties.put(f.getName(), Arrays.asList(oldValue.toString(), newValue.toString()));
			}
			f.setAccessible(false);
		}
		return notMatchProperties;
	}

	private <T> List<Field> getFields(T t) {
		final List<Field> fields = new ArrayList<>();
		Class<?> clazz = t.getClass();
		while (clazz != Object.class) {
			fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
			clazz = clazz.getSuperclass();
		}
		return fields;
	}

	private Session getEmailSession() {
		final Properties properties = System.getProperties();
		properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
		properties.put(SMTP_AUTH, true);
		properties.put(SMTP_PORT, DEFAULT_PORT);
		properties.put(SMTP_STARTTLS_ENABLE, true);
		properties.put(SMTP_STARTTLS_REQUIRED, true);
		return Session.getInstance(properties, null);
	}

	static String splitCamelCase(String s) {
		return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
	}

}
