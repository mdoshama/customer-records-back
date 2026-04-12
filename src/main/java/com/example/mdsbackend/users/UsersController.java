package com.example.mdsbackend.users;

import com.example.mdsbackend.dtos.CreateUserRequest;
import com.example.mdsbackend.dtos.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final UsersService usersService;

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usersService.createUser(request));
    }

    @GetMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }
}
