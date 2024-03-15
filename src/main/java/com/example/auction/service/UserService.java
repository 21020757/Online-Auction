package com.example.auction.service;


import com.example.auction.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO addUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
}
