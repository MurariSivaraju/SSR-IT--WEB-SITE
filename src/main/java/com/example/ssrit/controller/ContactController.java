package com.example.ssrit.controller;

import com.example.ssrit.dto.ContactRequest;
import com.example.ssrit.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin("*") // optional if calling from frontend
public class ContactController {

    private final EmailService emailService;

    public ContactController(EmailService emailService) {
        this.emailService = emailService;
    }

    // ========== GET API ==========
    @GetMapping
    public ResponseEntity<String> getContactStatus() {
        return ResponseEntity.ok("Contact API is working successfully!");
    }

    // ========== POST API ==========
    @PostMapping
    public ResponseEntity<String> sendContact(@Valid @RequestBody ContactRequest contactRequest) {
        emailService.sendContactMessage(contactRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Message sent successfully!");
    }
}
