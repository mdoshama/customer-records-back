package com.example.mdsbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;
    private String username;
    private String role;

}
