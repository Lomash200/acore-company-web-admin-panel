package com.acoreweb.acoreapi.service.impl;

import com.acoreweb.acoreapi.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender; // Yeh Spring Boot automatic configure kar dega properties se

    @Override
    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            // 'from' set karne ki zarurat nahi,
            // yeh 'spring.mail.username' se automatic utha lega

            mailSender.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            // Production me yahan proper logging honi chahiye
        }
    }
}