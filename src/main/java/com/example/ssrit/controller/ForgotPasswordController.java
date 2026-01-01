package com.example.ssrit.controller;

import com.example.ssrit.service.ForgotPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class ForgotPasswordController {

    private final ForgotPasswordService service;

    public ForgotPasswordController(ForgotPasswordService service) {
        this.service = service;
    }

    /**
     * SEND OTP TO EMAIL
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {

        String email = request.get("email");
        service.sendOtp(email);

        return ResponseEntity.ok(
                Map.of("message", "OTP sent to email"));
    }

    /**
     * RESET PASSWORD USING OTP
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody Map<String, String> request) {

        String email = request.get("email");
        String otp = request.get("otp");
        String password = request.get("password");
        String confirmPassword = request.get("confirmPassword");

        if (!password.equals(confirmPassword)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Passwords do not match"));
        }

        service.resetPassword(email, otp, password);

        return ResponseEntity.ok(
                Map.of("message", "Password reset successful"));
    }
}
