package com.example.auction.controller;

import com.example.auction.dto.LoginRequest;
import com.example.auction.dto.LoginResponse;
import com.example.auction.dto.SignupRequest;
import com.example.auction.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthenticationController {
    private final AuthenticationService authenticationService;

    public JwtAuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody
                                                  SignupRequest request) throws Exception {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody
                                                  LoginRequest request) throws Exception {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
