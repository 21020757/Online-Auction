package com.example.auction.event.listener;

import com.example.auction.dto.SignupRequest;
import com.example.auction.dto.UserDTO;
import com.example.auction.event.RegistrationCompleteEvent;
import com.example.auction.model.User;
import io.lettuce.core.output.ScanOutput;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Objects;


@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationCompleteEventListener
        implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private final RedisTemplate<String, String> template;
    private final JavaMailSender mailSender;
    private SignupRequest user;
//    public RegistrationCompleteEventListener() {
//        this.template = template;
//    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {


        //1. Get the newly registered user
        user = event.getRequest();

        //2. Get token for the user
        String verificationToken = template.opsForValue().get("verificationToken");
        System.out.println(verificationToken);
        //4. Build the verification url to be sent to the user
        String url = event.getApplicationUrl() + "/verifyEmail?token=" + verificationToken;
        //5. Send the email
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Click the link to verify your registration : {}", url);
    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "User Registration Portal Service";
        String content = "<p> Hi, " + user.getFullName() + ", </p>" +
                "<p>Thank you for registering with us," + "" +
                "Please, follow the link below to complete your registration.</p>" +
                "<a href=\"" + url + "\">Verify your email to activate your account</a>" +
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("dailycodework@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        mailSender.send(message);
    }
}
