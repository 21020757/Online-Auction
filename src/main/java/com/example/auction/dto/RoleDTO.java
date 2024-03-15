package com.example.auction.dto;

import com.example.auction.model.User;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Builder
public class RoleDTO {
    private Long id;
    private String name;
    private List<User> users;
}
