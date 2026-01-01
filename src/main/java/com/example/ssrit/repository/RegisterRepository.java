package com.example.ssrit.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ssrit.entity.UserRegistration;

public interface RegisterRepository extends JpaRepository<UserRegistration, Long> {

    Optional<UserRegistration> findByEmail(String email);
   

    boolean existsByEmail(String email);
}
