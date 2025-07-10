package com.tvm.internal.tvm_internal_project.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String to, String fullName, String email, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yourgmail@gmail.com");
        message.setTo(to);
        message.setSubject("Welcome to TVM Infotech!");
        String loginLink = "http://localhost:4200/login";
        StringBuilder sb = new StringBuilder();
        sb.append("Hello ").append(fullName).append(",\n\n");
        sb.append("Your account has been created successfully.\n\n");
        sb.append("Here are your login details:\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Password: ").append(password).append("\n\n");
        sb.append("You can log in here: ").append(loginLink).append("\n\n");
        sb.append("Please keep this information safe.\n\n");
        sb.append("Thank you!");

        message.setText(sb.toString());

        mailSender.send(message);
    }


}
