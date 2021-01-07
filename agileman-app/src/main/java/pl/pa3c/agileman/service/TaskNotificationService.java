package pl.pa3c.agileman.service;

import static javax.mail.Message.RecipientType.TO;
import static pl.pa3c.agileman.service.EmailConstant.DEFAULT_PORT;
import static pl.pa3c.agileman.service.EmailConstant.EMAIL_SUBJECT;
import static pl.pa3c.agileman.service.EmailConstant.FROM_EMAIL;
import static pl.pa3c.agileman.service.EmailConstant.GMAIL_SMTP_SERVER;
import static pl.pa3c.agileman.service.EmailConstant.PASSWORD;
import static pl.pa3c.agileman.service.EmailConstant.SIMPLE_MAIL_TRANSFER_PROTOCOL;
import static pl.pa3c.agileman.service.EmailConstant.SMTP_AUTH;
import static pl.pa3c.agileman.service.EmailConstant.SMTP_HOST;
import static pl.pa3c.agileman.service.EmailConstant.SMTP_PORT;
import static pl.pa3c.agileman.service.EmailConstant.SMTP_STARTTLS_ENABLE;
import static pl.pa3c.agileman.service.EmailConstant.SMTP_STARTTLS_REQUIRED;
import static pl.pa3c.agileman.service.EmailConstant.USERNAME;

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
	public void afterTransaction(TaskUpdateEvent event)
			throws IllegalAccessException, MessagingException {

		final List<String> emails = userTaskRepository
				.findAllByTaskIdAndTypeIn(event.getNewState().getId(), Arrays.asList(Type.OBSERVER)).stream()
				.map(x -> x.getUser().getEmail()).collect(Collectors.toList());
		
		if(emails.isEmpty()) {
			return;
		}

		final Map<String, List<String>> taskDifferences = findTaskDifferences(event.getOldState(), event.getNewState());

		String content = buildContent(taskDifferences);
		sendNotificationEmail(content, emails);

	}

	public String buildContent(Map<String, List<String>> changes) {
		sb.setLength(0);

		changes.forEach((key, value) -> {
			sb.append("\n" + key + " old = " + value.get(0) + " new = " + value.get(1) + "\n");
		});

		return sb.toString();

	}

	public void sendNotificationEmail(String text, List<String> emails) throws MessagingException {
		final Message message = createEmail(text, buildAddressesString(emails));
		final SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
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

	private Message createEmail(String text, String addresses) throws MessagingException {
		final Message message = new MimeMessage(getEmailSession());
		message.setFrom(new InternetAddress(FROM_EMAIL));
		message.setRecipients(TO, InternetAddress.parse(addresses));
		message.setSubject(EMAIL_SUBJECT);
		message.setText("Information" + ", \n \n there where changes in the task that You observed \n \n" + text
				+ "\n \n The Notification Service");
		message.setSentDate(new Date());
		message.saveChanges();
		return message;
	}

	private Map<String, List<String>> findTaskDifferences(Task oldTask, Task newTask)
			throws IllegalArgumentException, IllegalAccessException {

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

}
