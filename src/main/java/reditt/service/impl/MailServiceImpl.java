package reditt.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@EnableAsync
@Service
public class MailServiceImpl {

    @Async
    public void sendMail(String token, String recipient) {
        String from = "reditt@email.com";
        final String username = "0aa43d7d7fc71a";
        final String password = "abf952c5156779";
        String host = "smtp.mailtrap.io";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            MimeBodyPart mbp = new MimeBodyPart();
            mbp.setText("<a href=http://localhost:8080/api/auth/accountVerification/" + token + ">Verify account</a>",
                    "UTF-8", "html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mbp);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject("Reditt registration confirmation");
            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
