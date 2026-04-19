package com.example.mdsbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerRequest {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
