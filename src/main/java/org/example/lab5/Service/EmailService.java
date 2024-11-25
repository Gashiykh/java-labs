package org.example.lab5.Service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Service
public class EmailService {

    private final String host = "smtp.mail.ru";
    private final int port = 465;
    private final String username = "gashiykh@bk.ru";
    private final String password = "1TvfgSyRL8usdt9877hv";

    public void sendEmail(String to, String subject, String messageContent) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );
            message.setSubject(subject);
            message.setText(messageContent);

            Transport.send(message);

            System.out.println("Сообщение успешно отправлено " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при отправке: " + e.getMessage());
        }
    }
}
