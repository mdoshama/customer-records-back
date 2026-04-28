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
    public UserResponse updateUser(Long id, CreateUserRequest request) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setRole(request.getRole());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return mapToResponse(usersRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (!usersRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        usersRepository.deleteById(id);
    }

    private UserResponse mapToResponse(Users user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole().name()
        );
    }




}
