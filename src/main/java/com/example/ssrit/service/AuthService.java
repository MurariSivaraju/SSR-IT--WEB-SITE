package com.example.ssrit.service;

import com.example.ssrit.entity.UserRegistration;
import com.example.ssrit.repository.RegisterRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final RegisterRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(RegisterRepository repository,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void login(String email, String password) {

        UserRegistration user = repository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Email not registered"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // ✅ Login successful
    }
}
