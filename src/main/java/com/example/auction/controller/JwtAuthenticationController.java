package com.example.auction.controller;

import com.example.auction.dto.LoginRequest;
import com.example.auction.dto.LoginResponse;
import com.example.auction.dto.SignupRequest;
import com.example.auction.event.RegistrationCompleteEvent;
import com.example.auction.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JwtAuthenticationController {
    private final AuthenticationService authenticationService;
    private final ApplicationEventPublisher publisher;
    private final RedisTemplate<String, String> template;

    public JwtAuthenticationController(AuthenticationService authenticationService,
                                       ApplicationEventPublisher publisher,
                                       RedisTemplate<String, String> template) {
        this.authenticationService = authenticationService;
        this.publisher = publisher;
        this.template = template;
    }


    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody
                                                  SignupRequest signupRequest, final HttpServletRequest request) {
        LoginResponse response = authenticationService.register(signupRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(signupRequest, applicationUrl(request)));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody
                                               LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }

    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token) {
        String verificationToken = template.opsForValue().get("verificationToken");

        return "";
    }

    public String applicationUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName() + ":"
                + request.getServerPort()
                + request.getContextPath();
    }
}
