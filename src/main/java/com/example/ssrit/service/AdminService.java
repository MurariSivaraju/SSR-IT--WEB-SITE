package com.example.ssrit.service;

import com.example.ssrit.entity.UserRegistration;
import com.example.ssrit.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* =============================
       USERS
       ============================= */
    public Page<UserRegistration> getUsers(String search, int page, int size) {
        return userRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        search, search, PageRequest.of(page, size));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public UserRegistration getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id));
    }

    /* =============================
       ANALYTICS
       ============================= */
    public Map<String, Long> getStats() {
        return Map.of(
                "totalUsers", userRepository.count(),
                "freshers", userRepository.countByWorkStatus("Fresher"),
                "experienced", userRepository.countByWorkStatus("Experienced")
        );
    }

    /* =============================
       RESUME
       ============================= */
    public File getResumeFile(Long id) {
        UserRegistration user = getUser(id);
        if (user.getResumePath() == null || user.getResumePath().isBlank()) {
            return null;
        }
        return new File(user.getResumePath());
    }
}
