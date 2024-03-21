package com.example.auction.service;

import com.example.auction.dto.LoginRequest;
import com.example.auction.dto.LoginResponse;
import com.example.auction.dto.SignupRequest;
import com.example.auction.exception.ErrorMessages;
import com.example.auction.exception.err.PasswordsNotMatchException;
import com.example.auction.exception.err.UserNotFoundException;
import com.example.auction.jwt.JwtHelper;
import com.example.auction.model.User;
import com.example.auction.repository.RoleRepository;
import com.example.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;
    private final AuthenticationManager authenticationManager;
    private final RedisTemplate<String, String> template;

    @Autowired
    public AuthenticationService(UserRepository userRepository,
                                 RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                                 JwtHelper jwtHelper,
                                 AuthenticationManager authenticationManager,
                                 RedisTemplate<String, String> template) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtHelper = jwtHelper;
        this.authenticationManager = authenticationManager;
        this.template = template;
    }

    @Cacheable("token")
    public LoginResponse register(SignupRequest request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new PasswordsNotMatchException(ErrorMessages.PASSWORD_NOT_MATCH);
        }

        User user = userRepository.findByEmail(request.getEmail());
        if (user != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Mail address already used!");
        }
        user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .roles(List.of(roleRepository.findByName("ROLE_USER")))
                .build();

        userRepository.save(user);
        String token = jwtHelper.generateToken(user);

        template.opsForValue().set("verificationToken", token);
        template.opsForValue().set("email", request.getEmail());

        return new LoginResponse(user.getEmail(), token);
    }

    public LoginResponse authenticate(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new UserNotFoundException(ErrorMessages.USER_NOT_FOUND);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        String token = jwtHelper.generateToken(user);
        return new LoginResponse(user.getEmail(), token);
    }
}
