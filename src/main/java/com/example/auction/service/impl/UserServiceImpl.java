package com.example.auction.service.impl;

import com.example.auction.converter.Converter;
import com.example.auction.dto.UserDTO;
import com.example.auction.model.User;
import com.example.auction.repository.UserRepository;
import com.example.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<UserDTO> getAllUsers() {
        List<UserDTO> list = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for(User user: users) {
            list.add(converter.userToDTO(user));
        }
        return list;
    }

    @Override
    public UserDTO findByEmail(String email) {
        return converter.userToDTO(userRepository.findByEmail(email));
    }


}
