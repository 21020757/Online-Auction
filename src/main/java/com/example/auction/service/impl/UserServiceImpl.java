package com.example.auction.service.impl;

import com.example.auction.converter.Converter;
import com.example.auction.dto.UserDTO;
import com.example.auction.model.User;
import com.example.auction.repository.UserRepository;
import com.example.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Converter converter;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, Converter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());
        if (user != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
        } else {
            user = User.builder()
                    .id(userDTO.getId())
                    .fullName(userDTO.getFullName())
                    .password(userDTO.getPassword())
                    .email(userDTO.getEmail())
                    .phoneNumber(userDTO.getPhoneNumber())
                    .address(userDTO.getAddress())
                    .roles(userDTO.getRoles())
                    .build();
            userRepository.save(user);
        }
        return converter.userToDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<UserDTO> list = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for(User user: users) {
            list.add(converter.userToDTO(user));
        }
        return list;
    }


}
