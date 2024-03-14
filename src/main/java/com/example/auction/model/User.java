package com.example.auction.model;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

@Entity(name = "User")
public class User {
    @Id
//    @SequenceGenerator(
//            name = "user_sequence",
//            sequenceName = "user_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "user_sequence"
//    )
    @Column(name = "id",
            updatable = false)
    private Long id;
    @Column(name = "full_name",
            nullable = false,
            columnDefinition = "TEXT")
    private String fullName;
    @Column(name = "user_name",
            nullable = false,
            columnDefinition = "TEXT")
    private String username;
    @Column(name = "password",
            nullable = false,
            columnDefinition = "TEXT")
    private String password;
    @Column(name = "email",
            nullable = false,
            unique = true)
    private String email;
    @Column(name = "phone_number",
            nullable = false,
            columnDefinition = "TEXT")
    private String phoneNumber;
    @Column(name = "address",
            nullable = false,
            columnDefinition = "TEXT")
    private String address;
    @Column(name = "role")
    @Value("USER")
    private String role;

    public User(long id, String fullName, String username, String password,
                String email, String phoneNumber, String address, String role) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
