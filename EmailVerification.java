package com.teamvoid.gocircle;

import io.github.cdimascio.dotenv.Dotenv;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailVerification {
    private final String fromMail;
    private final String password;
    private final Properties props;
    private final Session session;
    private final String message;
    String verificationCode;

    public EmailVerification(String emailAddress, String userName) {
        this.fromMail = Dotenv.load().get("DB_gmailId");
        this.password = Dotenv.load().get("DB_gmailPassword");
        props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromMail, password);
            }
        });
       verificationCode = generateCode();
        message = "Dear " + userName + ",\n\n"
                + "Thank you for registering with GoCircle. To verify your account, please enter the following code in the verification page:\n\n"
                + verificationCode + "\n\n"
                + "Best regards,\n"
                + "GoCircle Team";
        sendVerificationCode(emailAddress, verificationCode);
        System.out.println(verificationCode);
    }

    public String generateCode() {
        String verCode = String.valueOf(new Random().nextInt(000000, 999999));
        return verCode;
    }

    public void sendVerificationCode(String emailAddress, String verificationCode) {
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(fromMail);
            mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(emailAddress));
            mimeMessage.setSubject("Verification mail");
            mimeMessage.setText(message);
            Transport.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Creating mail connection");
        }
    }
}
