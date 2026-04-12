package com.example.mdsbackend.users;

import com.example.mdsbackend.dtos.CreateUserRequest;
import com.example.mdsbackend.dtos.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser (CreateUserRequest createUserRequest) {
        if (usersRepository.existsByUsername(createUserRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        Users users = new Users();
        users.setName(createUserRequest.getName());
        users.setUsername(createUserRequest.getUsername());
        users.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        users.setRole(createUserRequest.getRole());

        Users saved  = usersRepository.save(users);
        return new UserResponse(saved.getId(), saved.getName(), saved.getUsername(),saved.getRole().name());
    }
    public List<UserResponse> getAllUsers() {
        return usersRepository.findAll()
                .stream()
                .map(u -> new UserResponse(u.getId(), u.getName(), u.getUsername(), u.getRole().name()))
                .toList();
    }




}
