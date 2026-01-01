package com.example.ssrit.service;

import com.example.ssrit.dto.RegisterRequestDto;
import com.example.ssrit.entity.UserRegistration;
import com.example.ssrit.repository.RegisterRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class RegisterService {

    private final RegisterRepository repository;
    private final PasswordEncoder passwordEncoder;

    // ✅ ABSOLUTE PATH (Windows)
    private static final String UPLOAD_DIR = "C:/uploads/resumes/";

    public RegisterService(RegisterRepository repository,
                           PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(RegisterRequestDto request) throws IOException {

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        if (repository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        String resumePath = saveResume(request.getResume());

        UserRegistration user = new UserRegistration();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setMobile(request.getMobile());
        user.setHighestQualification(request.getHighestQualification());
        user.setWorkStatus(request.getWorkStatus());
        user.setYearOfExperience(request.getYearOfExperience());
        user.setResumePath(resumePath);

        repository.save(user);
    }

    private String saveResume(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            return null;
        }

        // 1️⃣ Ensure directory exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 2️⃣ Create safe unique filename
        String fileName = UUID.randomUUID() + "_" +
                StringUtils.cleanPath(file.getOriginalFilename());

        // 3️⃣ Save file
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        return filePath.toString();
    }
}
