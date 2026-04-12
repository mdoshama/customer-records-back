package com.example.mdsbackend.auth;

import com.example.mdsbackend.dtos.LoginRequest;
import com.example.mdsbackend.dtos.LoginResponse;
import com.example.mdsbackend.users.Users;
import com.example.mdsbackend.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final JwtUtil jwtUtil;


    public LoginResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);

        Users users = usersRepository.findByUsername(userDetails.getUsername()).orElseThrow(()-> new RuntimeException("User not found"));

        return new LoginResponse(token, users.getUsername(), users.getRole().name());

    }


}
