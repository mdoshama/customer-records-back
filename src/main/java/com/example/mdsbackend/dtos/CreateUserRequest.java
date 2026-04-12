package com.example.mdsbackend.dtos;

import com.example.mdsbackend.users.Role;
import lombok.Data;

@Data
public class CreateUserRequest {
    private String name;
    private String username;
    private String password;
    private Role role;
}
