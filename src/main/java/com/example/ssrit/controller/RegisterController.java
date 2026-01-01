package com.example.ssrit.controller;

import com.example.ssrit.dto.RegisterRequestDto;
import com.example.ssrit.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // allow frontend access
public class RegisterController {

    private final RegisterService service;

    public RegisterController(RegisterService service) {
        this.service = service;
    }

    /**
     * REGISTER USER (Form + File Upload)
     */
    @PostMapping(value = "/register", consumes = "multipart/form-data")
    public ResponseEntity<?> register(@ModelAttribute RegisterRequestDto request) {

        try {
            service.register(request);
            return ResponseEntity.ok(
                    Map.of("message", "Registration successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
