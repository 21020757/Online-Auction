package com.example.auction.config;

import com.example.auction.model.Role;
import com.example.auction.model.User;
import com.example.auction.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
public class SecurityConfig {
    @Autowired
    private RoleRepository roleRepository;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admUser = User.builder()
                .email("admin")
                .password(passwordEncoder().encode("admin"))
                .roles(List.of(roleRepository.findByName("ROLE_ADMIN")))
                .build();

        UserDetails nomUser = User.builder()
                .email("user")
                .password(passwordEncoder().encode("user"))
                .roles(List.of(roleRepository.findByName("ROLE_USER")))
                .build();
        return new InMemoryUserDetailsManager(admUser, nomUser);
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {
            return configuration.getAuthenticationManager();
    }
}
