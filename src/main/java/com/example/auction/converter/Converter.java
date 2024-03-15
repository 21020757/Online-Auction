package com.example.auction.converter;

import com.example.auction.dto.RoleDTO;
import com.example.auction.dto.UserDTO;
import com.example.auction.model.Role;
import com.example.auction.model.User;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    //DTO
    public UserDTO userToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .password(user.getPassword())
                .roles(user.getRoles())
                .address(user.getAddress())
                .build();
    }

    public RoleDTO roleToDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .users(role.getUsers())
                .build();
    }
    //Entity

}
