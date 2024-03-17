package com.example.auction.service;


import com.example.auction.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO findByEmail(String email);
}
