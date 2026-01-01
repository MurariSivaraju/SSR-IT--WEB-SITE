package com.example.ssrit.repository;

import com.example.ssrit.entity.UserRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<UserRegistration, Long> {

    Optional<UserRegistration> findByEmail(String email);

    boolean existsByEmail(String email);

    // 🔍 SEARCH + PAGINATION (THIS FIXES YOUR ERROR)
    Page<UserRegistration>
    findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String name,
            String email,
            Pageable pageable
    );

    // 📊 ANALYTICS
    long countByWorkStatus(String workStatus);
}

