package com.example.auction.event.listener;

import com.example.auction.dto.SignupRequest;
import com.example.auction.dto.UserDTO;
import com.example.auction.event.RegistrationCompleteEvent;
import io.lettuce.core.output.ScanOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@Slf4j
@RequiredArgsConstructor
public class RegistrationCompleteEventListener
        implements ApplicationListener<RegistrationCompleteEvent> {

    @Autowired
    private final RedisTemplate<String, String> template;

//    public RegistrationCompleteEventListener() {
//        this.template = template;
//    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {


        //1. Get the newly registered user
        SignupRequest request = event.getRequest();

        //2. Get token for the user
        String verificationToken = template.opsForValue().get("verificationToken");
        System.out.println(verificationToken);
        //4. Build the verification url to be sent to the user
        String url = event.getApplicationUrl() + "/register/verifyEmail?token=" + verificationToken;
        //5. Send the email

        log.info("Click the link to verify your registration : {}", url);
    }
}
