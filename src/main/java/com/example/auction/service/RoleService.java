package com.example.auction.service;

import com.example.auction.dto.RoleDTO;
import com.example.auction.dto.UserDTO;
import com.example.auction.model.Role;

import java.util.List;

public interface RoleService {
    RoleDTO addRole(String name);
    RoleDTO findRole(String name);
    List<RoleDTO> addAll(List<Role> roles);
}
