package org.da;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


/**
 * @author Abhishek
 */
public class SendMail {
    public static final String SMTP_HOST = "smtp.host";
    public static final String SMTP_FROM = "smtp.from";
    public static final String SMTP_PASSWORD = "smtp.password";
    public static final String SMTP_PORT = "smtp.port";
    public static final String SMTP_AUTH = "smtp.auth";


    public static void sendMail(String to, String subject, String content, Properties smtpDetails) throws MessagingException {
        Properties props = new Properties ();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpDetails.getProperty(SMTP_HOST, "smtp.gmail.com"));
        props.put("mail.smtp.user", smtpDetails.getProperty(SMTP_FROM, "username"));
        props.put("mail.smtp.password", smtpDetails.getProperty(SMTP_PASSWORD, "password"));
        props.put("mail.smtp.port", smtpDetails.getProperty(SMTP_PORT, "587"));
        props.put("mail.smtp.auth", smtpDetails.getProperty(SMTP_AUTH, "true"));
        sendMailInternal(to, props.getProperty("mail.smtp.user"), subject, content, props);
    }

    public static void sendBySMVITGMail(String to, String subject, String content) throws MessagingException {
        Properties props = new Properties ();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "username");
        props.put("mail.smtp.password", "password");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        sendMailInternal(to, props.getProperty("mail.smtp.user"), subject, content, props);
    }

    private static void sendMailInternal(String toAddressString, String from, String subject, String content, Properties props) throws MessagingException{
        /*String host = "smtp.gmail.com";
        String from = "username";
        String pass = "password";*/
        /*Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true"); // added this line
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");*/

        String[] to = {toAddressString}; // added this line

        Session session = Session.getDefaultInstance(props, null);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));

        InternetAddress[] toAddresses = new InternetAddress[to.length];

        // To get the array of addresses
        for (int i = 0; i < to.length; i++) { // changed from a while loop
            toAddresses[i] = new InternetAddress(to[i]);
        }
        for (InternetAddress toAddress : toAddresses) { // changed from a while loop
            message.addRecipient(Message.RecipientType.TO, toAddress);
        }
        message.setSubject(subject);
        message.setText(content);
        Transport transport = session.getTransport("smtp");
        transport.connect(props.getProperty("mail.smtp.host"), from, props.getProperty("mail.smtp.password"));
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
