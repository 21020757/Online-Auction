package com.example.auction.model;

import com.example.auction.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Getter
@Setter
@Builder
public class User implements UserDetails {
    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "id",
            updatable = false)
    private Long id;
    @Column(name = "full_name",
            nullable = false,
            columnDefinition = "TEXT")
    private String fullName;
    @Column(name = "password",
            nullable = false,
            columnDefinition = "TEXT")
    private String password;
    @Column(name = "email",
            nullable = false
    )
    private String email;
    @Column(name = "phone_number",
            nullable = false,
            columnDefinition = "TEXT")
    private String phoneNumber;
    @Column(name = "address",
            nullable = false,
            columnDefinition = "TEXT")
    private String address;
    private boolean isEnabled;
    @Column(name = "role")
    @Value("USER")
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public User(Long id, String fullName, String password,
                String email, String phoneNumber, String address,
                boolean isEnabled, List<Role> roles) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isEnabled = isEnabled;
        this.roles = roles;
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getRoles().toString()));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
