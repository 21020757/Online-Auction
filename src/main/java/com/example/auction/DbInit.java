package com.example.auction;

import com.example.auction.model.Role;
import com.example.auction.repository.RoleRepository;
import com.example.auction.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DbInit implements InitializingBean {
    private final RoleServiceImpl roleService;

    @Autowired
    public DbInit(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    List<Role> roles = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        //CREATE ROLE
        if (roleService.findRole("ROLE_ADMIN") == null
                && roleService.findRole("ROLE_USER") == null) {
            roles.add(new Role("ROLE_ADMIN"));
            roles.add(new Role("ROLE_USER"));
            roleService.addAll(roles);
        }
    }
}
