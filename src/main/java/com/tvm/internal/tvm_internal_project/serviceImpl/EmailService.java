package com.tvm.internal.tvm_internal_project.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String to, String fullName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yourgmail@gmail.com");
        message.setTo(to);
        message.setSubject("Welcome to Our App!");
        message.setText("Hello " + fullName + ",\n\nYour account has been created successfully.\n\nThank you!");

        mailSender.send(message);
    }
}
