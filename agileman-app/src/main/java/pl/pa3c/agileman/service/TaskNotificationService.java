package pl.pa3c.agileman.service;

import static javax.mail.Message.RecipientType.TO;
import static pl.pa3c.agileman.service.EmailConstant.DEFAULT_PORT;
import static pl.pa3c.agileman.service.EmailConstant.EMAIL_SUBJECT;
import static pl.pa3c.agileman.service.EmailConstant.FROM_EMAIL;
import static pl.pa3c.agileman.service.EmailConstant.GMAIL_SMTP_SERVER;
import static pl.pa3c.agileman.service.EmailConstant.HTML_CONTENT;
import static pl.pa3c.agileman.service.EmailConstant.HTML_EMAIL_BEGIN;
import static pl.pa3c.agileman.service.EmailConstant.HTML_EMAIL_FOOTER;
import static pl.pa3c.agileman.service.EmailConstant.HTML_HEADER_ROW;
import static pl.pa3c.agileman.service.EmailConstant.HTML_TABLE_END;
import static pl.pa3c.agileman.service.EmailConstant.HTML_TABLE_START;
import static pl.pa3c.agileman.service.EmailConstant.PASSWORD;
import static pl.pa3c.agileman.service.EmailConstant.SIMPLE_MAIL_TRANSFER_PROTOCOL;
import static pl.pa3c.agileman.service.EmailConstant.SMTP_AUTH;
import static pl.pa3c.agileman.service.EmailConstant.SMTP_HOST;
import static pl.pa3c.agileman.service.EmailConstant.SMTP_PORT;
import static pl.pa3c.agileman.service.EmailConstant.SMTP_STARTTLS_ENABLE;
import static pl.pa3c.agileman.service.EmailConstant.SMTP_STARTTLS_REQUIRED;
import static pl.pa3c.agileman.service.EmailConstant.USERNAME;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.sun.mail.smtp.SMTPTransport;

import pl.pa3c.agileman.model.task.UserTask;

@Service
public class TaskNotificationService {

	private final StringBuilder sb = new StringBuilder();

	public void buildAndSendMail(List<UserTask> userTasks,String taskTitle, Map<String, List<String>> taskDifferences)
			throws MessagingException {

		final List<String> emails = userTasks.stream().map(x -> x.getUser().getEmail()).collect(Collectors.toList());
		final String content = buildHTMLContent(taskTitle, taskDifferences);

		sendNotificationEmail(content, emails);
	}

	public String buildHTMLContent(String taskTitle, Map<String, List<String>> changes) {
		sb.setLength(0);

		sb.append(HTML_EMAIL_BEGIN);
		sb.append("<h5>");
		sb.append(taskTitle);
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
