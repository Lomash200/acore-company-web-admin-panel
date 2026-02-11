package com.acoreweb.acoreapi.repository;

import com.acoreweb.acoreapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Login ke time user ko username se find karne ke liye
    Optional<User> findByUsername(String username);

    // --- YEH NAYA METHOD ADD KARNA HAI ---
    // Forgot Password token se user find karne ke liye

    Optional<User> findByOtpCode(String otpCode);



}