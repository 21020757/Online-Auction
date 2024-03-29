package com.example.auction.dto;

import com.example.auction.model.Role;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String fullName;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean isEnabled;
    private List<Role> roles;
}
