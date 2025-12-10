package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.DTO.WishesDto;
import com.tvm.internal.tvm_internal_project.exception.ResourceNotFound;
import com.tvm.internal.tvm_internal_project.response.ResponseStructure;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public ResponseEntity<ResponseStructure<String>> sendRegistrationEmail(String to, String fullName, String email, String password) {

        if (to == null || to.isEmpty()) {
            throw new ResourceNotFound("Email not found!");
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("yourgmail@gmail.com");
            message.setTo(to);
            message.setSubject("Welcome to TVM Infotech!");

            String loginLink = "http://localhost:4200/login";

            StringBuilder sb = new StringBuilder();
            sb.append("Hello ").append(fullName).append(",\n\n");
            sb.append("Your account has been created successfully.\n\n");
            sb.append("Email: ").append(email).append("\n");
            sb.append("Password: ").append(password).append("\n\n");
            sb.append("Login here: ").append(loginLink).append("\n\n");
            sb.append("Thank you!");

            message.setText(sb.toString());
            mailSender.send(message);

            ResponseStructure<String> response = new ResponseStructure<>();
            response.setBody("Email Sent Successfully");
            response.setMessage("Registration email has been sent");
            response.setStatusCode(HttpStatus.OK.value());

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send registration email");
        }
    }

   public ResponseEntity<ResponseStructure<String>> sendBirthdayWishes(List<WishesDto> wishesList){

       if (wishesList == null || wishesList.isEmpty()) {
           throw new ResourceNotFound("No users found for birthday wishes");
       }

       try {

        wishesList.stream().forEach(wishes->{
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(wishes.getEmail());
                helper.setSubject("🎉 Happy Birthday, " + wishes.getName() + "!");
                String htmlContent =
                        "<div style='font-family: Arial, sans-serif; text-align: center; background-color:#fffafc; padding:20px; border-radius:10px;'>"
                                + "  <h2 style='color:#ff4081;'>🎉 Happy Birthday, " + wishes.getName() + "! 🎂</h2>"
                                + "  <p style='font-size:15px; color:#333;'>"
                                + "    On your <b>Birthday</b>, we wish you a wonderful year ahead and hope you accomplish "
                                + "    all the amazing goals you’ve set for yourself."
                                + "  </p>"
                                + "  <p style='font-size:15px; color:#333;'>"
                                + "    May the coming years be filled with <b>happiness</b>, <b>peace</b>, and <b>love</b>. 💖"
                                + "  </p>"
                                + "  <img src='cid:photo' width='200' height='200' "
                                + "       style='border-radius:50%; margin-top:15px; box-shadow:0 0 8px rgba(0,0,0,0.1);'>"
                                + "  <p style='margin-top:20px; color:#555; font-size:14px;'>"
                                + "    Have an amazing year ahead! 🥳"
                                + "  </p>"
                                + "  <hr style='border:none; border-top:1px solid #eee; margin:20px 0;'>"
                                + "  <div style='display:flex; align-items:center; justify-content:center; gap:8px;'>"
                                + "    <p style='font-size:14px; color:#444; margin:0;'>"
                                + "      Thanks &amp; Regards,<br>"
                                + "      <b>Your Team</b>"
                                + "    </p> <br>"

                                + "    <img src='cid:logo' width='60' style='vertical-align:middle;'>"
                                + "  </div>"
                                + "</div>";


                helper.setText(htmlContent, true);
                ByteArrayResource imageResource = new ByteArrayResource(wishes.getpSizePhoto());
                helper.addInline("photo", imageResource, "image/jpeg");
                FileSystemResource logo = new FileSystemResource(new File("src/main/resources/static/images/TVM Infotech Logo.jpg"));
                helper.addInline("logo", logo);
                mailSender.send(message);
            } catch (Exception ex) {
                throw new RuntimeException("Failed while sending birthday email");
            }
        });
           ResponseStructure<String> response = new ResponseStructure<>();
           response.setBody("Birthday Emails Sent");
           response.setMessage("Emails sent successfully to all users");
           response.setStatusCode(HttpStatus.OK.value());
           return new ResponseEntity<>(response, HttpStatus.OK);
       }
       catch (Exception e) {
           throw new RuntimeException("Error while sending birthday wishes");
       }
   }

    public ResponseEntity<ResponseStructure<String>> sendAnniversaryWishes(List<WishesDto> wishesList) {
        throw new ResourceNotFound("Anniversary feature not implemented");
    }
}
