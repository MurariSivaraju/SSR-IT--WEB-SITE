package com.example.ssrit.service;

import com.example.ssrit.dto.ContactRequest;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log =
            LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final String adminEmail;
    private final String fromEmail;

    public EmailService(
            JavaMailSender mailSender,
            @Value("${app.admin.email:}") String adminEmail,
            @Value("${spring.mail.username:}") String fromEmail) {

        this.mailSender = mailSender;
        this.adminEmail = adminEmail;
        this.fromEmail = fromEmail;
    }

    /* =====================================================
       LOG CONFIG AT STARTUP (VERY IMPORTANT)
       ===================================================== */
    @PostConstruct
    public void logEmailConfig() {
        log.info("EmailService initialized");
        log.info("Admin email: {}", adminEmail);
        log.info("From email (SMTP username): {}", fromEmail);
    }

    /* =====================================================
       OTP EMAIL (FOR FORGOT PASSWORD)
       ===================================================== */
    public void sendOtp(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom(fromEmail);
        message.setSubject("Password Reset OTP");
        message.setText(
                "Your OTP for password reset is: " + otp +
                "\n\nThis OTP is valid for 5 minutes."
        );

        try {
            mailSender.send(message);
            log.info("OTP email sent successfully to {}", toEmail);
        } catch (MailException ex) {
            log.error("Failed to send OTP email to {}", toEmail, ex);
            throw new RuntimeException("Unable to send OTP email");
        }
    }

    /* =====================================================
       CONTACT FORM EMAIL (ADMIN)
       ===================================================== */
    public void sendContactMessage(ContactRequest req) {

        if (adminEmail == null || adminEmail.isBlank()) {
            log.warn("Admin email not configured. Contact message logged only.");
            logContact(req);
            return;
        }

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(adminEmail);
        msg.setFrom(fromEmail);
        msg.setReplyTo(req.getEmail());
        msg.setSubject("New Contact Message – " + req.getName());

        msg.setText(buildContactMessage(req));

        try {
            mailSender.send(msg);
            log.info("Contact email sent to admin {}", adminEmail);
        } catch (MailException ex) {
            log.error("Failed to send contact email to admin {}", adminEmail, ex);
            logContact(req); // fallback logging
        }
    }

    /* =====================================================
       HELPERS
       ===================================================== */
    private String buildContactMessage(ContactRequest req) {
        return """
               You have received a new contact message.

               Name   : %s
               Email  : %s
               Phone  : %s

               Message:
               %s

               ---- End ----
               """.formatted(
                req.getName(),
                req.getEmail(),
                req.getPhoneNumber(),
                req.getMessage()
        );
    }

    private void logContact(ContactRequest req) {
        log.info(
                "CONTACT (NOT EMAILED) | Name={} | Email={} | Phone={} | Message={}",
                req.getName(),
                req.getEmail(),
                req.getPhoneNumber(),
                req.getMessage()
        );
    }
}
