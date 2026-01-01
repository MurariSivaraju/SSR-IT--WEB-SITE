package com.example.ssrit.service;

import com.example.ssrit.entity.UserRegistration;
import com.example.ssrit.repository.RegisterRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class ForgotPasswordService {

    private final RegisterRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public ForgotPasswordService(RegisterRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 EmailService emailService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // SEND OTP
    public void sendOtp(String email) {

        UserRegistration user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not registered"));

        String otp = String.valueOf(new Random().nextInt(900000) + 100000);

        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

        repository.save(user);
        emailService.sendOtp(email, otp);
    }

    // RESET PASSWORD
    public void resetPassword(String email, String otp, String newPassword) {

        UserRegistration user = repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        if (user.getOtp() == null ||
            user.getOtpExpiry() == null ||
            user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!otp.equals(user.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOtp(null);
        user.setOtpExpiry(null);

        repository.save(user);
    }
}
