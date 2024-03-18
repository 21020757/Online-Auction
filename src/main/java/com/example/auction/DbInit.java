package com.example.auction;

import com.example.auction.model.Role;
import com.example.auction.model.User;
import com.example.auction.repository.RoleRepository;

import com.example.auction.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DbInit implements InitializingBean {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final List<Role> roles;
    @Autowired
    public DbInit(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        roles = new ArrayList<>();

    }

    @Override
    public void afterPropertiesSet(){
        //CREATE ROLES
        if (roleRepository.findByName("ROLE_ADMIN") == null
                && roleRepository.findByName("ROLE_USER") == null) {
            Role admRole = new Role("ROLE_ADMIN");
            Role userRole = new Role("ROLE_USER");
            roles.add(admRole);
            roles.add(userRole);
            roleRepository.saveAllAndFlush(roles);
        }

        //CREATE DEMO USERS

    }
}
