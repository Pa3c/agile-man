package pl.pa3c.agileman.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailConstant {
    public static final String SIMPLE_MAIL_TRANSFER_PROTOCOL = "smtps";
    public static final String USERNAME = "pa3c.agileman@gmail.com";
    public static final String PASSWORD = "PA3C.agil3man";
    public static final String FROM_EMAIL = "task-service@agileman.com";
    public static final String CC_EMAIL = "";
    public static final String EMAIL_SUBJECT = "Task changes";
    public static final String GMAIL_SMTP_SERVER = "smtp.gmail.com";
    public static final String SMTP_HOST = "mail.smtp.host";
    public static final String SMTP_AUTH = "mail.smtp.auth";
    public static final String SMTP_PORT = "mail.smtp.port";
    public static final int DEFAULT_PORT = 465;
    public static final String SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String SMTP_STARTTLS_REQUIRED = "mail.smtp.starttls.required";
    public static final String HTML_CONTENT = "text/html; charset=utf-8";
    public static final String HTML_TABLE_START = "<table border=\"1\">";
    public static final String HTML_HEADER_ROW = "<tr><th>Property Name</th><th>Old</th><th>New</th><tr>";
    public static final String HTML_TABLE_END = "</table>";
    public static final String HTML_EMAIL_BEGIN = "Information, <br> there where changes in the task that You observed <br>";
    public static final String HTML_EMAIL_FOOTER = "<br>-------<br>The Notification Service.";
}
