package com.example.auction.service.impl;

import com.example.auction.converter.Converter;
import com.example.auction.dto.RoleDTO;
import com.example.auction.model.Role;
import com.example.auction.repository.RoleRepository;
import com.example.auction.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private Converter converter;

    @Override
    public RoleDTO addRole(String name) {
        Role role = Role.builder()
                .name(name)
                .build();
        roleRepository.save(role);
        return converter.roleToDTO(role);
    }

    @Override
    public RoleDTO findRole(String name) {
        Role role = roleRepository.findByName(name);
        return converter.roleToDTO(role);
    }

    @Override
    public List<RoleDTO> addAll(List<Role> roles) {
        roleRepository.saveAllAndFlush(roles);
        List<RoleDTO> roleDTOS = new ArrayList<>();
        for (Role role: roles) {
            roleDTOS.add(converter.roleToDTO(role));
        }
        return roleDTOS;
    }
}
