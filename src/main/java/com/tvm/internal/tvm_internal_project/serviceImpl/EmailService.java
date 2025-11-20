package com.tvm.internal.tvm_internal_project.serviceImpl;

import com.tvm.internal.tvm_internal_project.DTO.WishesDto;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
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

   public void sendBirthdayWishes(List<WishesDto> wishesList){

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
                FileSystemResource logoFile = new FileSystemResource(new File("src/main/resources/static/images/TVM Infotech Logo.jpg"));
                helper.addInline("logo", logoFile, "image/png");
                mailSender.send(message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

   }



   public void sendAnniversaryWishes(List<WishesDto> wishesList){

   }



}
