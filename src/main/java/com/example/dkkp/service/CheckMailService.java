package com.example.dkkp.service;

import com.example.dkkp.dao.EmailCheckDao;
import com.example.dkkp.model.Email_Check_Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Random;

public class CheckMailService {
    private final EmailCheckDao checkmail;


    public CheckMailService(EntityManager entityManager) {
        this.checkmail = new EmailCheckDao(entityManager);
    }

    public void createToken(String email) throws Exception {
            checkmail.deleteTokensByEmail(email);
            Random random = new Random();
            LocalDateTime DATE_END = LocalDateTime.now().plusMinutes(30);
            int randomNumber = 100000 + random.nextInt(900000);
            String token = String.valueOf(randomNumber);
            Email_Check_Entity check = new Email_Check_Entity(email, token, DATE_END);
            checkmail.createToken(check);
            sendVerificationEmail(check);
    }

    public boolean checkToken(String mail, String token) {
        if (token != null) {
            System.out.println("dc");
            if (checkmail.checkToken(mail, token)) {
                checkmail.deleteTokensByEmail(mail);
                return true;
            }
            return false;
        } else {
            throw new RuntimeException("Id token is null");
        }
    }

    public static void sendVerificationEmail(Email_Check_Entity checkMail) throws Exception {
        String host = "smtp.gmail.com";  // SMTP server của Gmail
        String fromEmail = "your-email@gmail.com"; // Thay bằng email của bạn
        String password = "your-email-password"; // Thay bằng mật khẩu của bạn hoặc App password

        // Cấu hình SMTP server
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true"); // Kích hoạt TLS
        properties.put("mail.smtp.auth", "true");

        // Tạo session với thông tin đăng nhập
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Tạo đối tượng MimeMessage
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(checkMail.getEMAIL()));
            message.setSubject("Email Verification Token");

            // Nội dung email với token xác minh
            String body = "Hello,\n\n" +
                    "Please use the following token to verify your account:\n\n" +
                    "Verification Token: " + checkMail.getTOKEN() + "\n\n" +
                    "Thank you!";
            message.setText(body);

            // Gửi email
            Transport.send(message);
        } catch (MessagingException mex) {
            throw new Exception("Failed to send verification email", mex);
        }
    }

}