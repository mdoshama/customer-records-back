package com.example.mdsbackend.users;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) {
        if(!usersRepository.existsByUsername("admin")) {
            Users admin = new Users();
            admin.setName("Administrator");
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.Admin);
            usersRepository.save(admin);
            System.out.println("Admin has been created");
        }else {
            System.out.println("Admin already exists");
        }
    }
}
