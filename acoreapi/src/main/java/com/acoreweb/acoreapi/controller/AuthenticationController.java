package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.*;
import com.acoreweb.acoreapi.model.User;
import com.acoreweb.acoreapi.repository.UserRepository;
import com.acoreweb.acoreapi.security.JwtService;
import com.acoreweb.acoreapi.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final String ADMIN_EMAIL = "acoreithub@gmail.com";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // -----------------------------------------------------
    // LOGIN (Only ONE ADMIN)
    // -----------------------------------------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // ❌ Block all non-admin emails
        if (!request.getUsername().equals(ADMIN_EMAIL)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new MessageResponse("Access denied: Only admin can login."));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;

        String token = jwtService.generateToken(userDetails);

        LoginResponse response = new LoginResponse(
                token,
                user.getUsername(),
                user.getRole()
        );

        return ResponseEntity.ok(response);
    }

    // -----------------------------------------------------
    //  REGISTRATION IS BLOCKED (ADMIN ONLY)
    // -----------------------------------------------------
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new MessageResponse("Registration is disabled. Only admin is allowed."));
    }

    // -----------------------------------------------------
    // FORGOT PASSWORD → SEND OTP
    // -----------------------------------------------------
    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {

        if (!request.getEmail().equals(ADMIN_EMAIL)) {
            return ResponseEntity.status(403)
                    .body(new MessageResponse("Access denied: Only admin can reset password."));
        }

        User user = userRepository.findByUsername(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Admin account not found."));

        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        user.setOtpCode(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        userRepository.save(user);

        // Send OTP
        emailService.sendEmail(user.getUsername(), "Admin Password Reset OTP", "Your OTP is: " + otp);

        return ResponseEntity.ok(new MessageResponse("OTP sent successfully."));
    }

    // -----------------------------------------------------
    // VERIFY OTP
    // -----------------------------------------------------
    @PostMapping("/verify-otp")
    public ResponseEntity<MessageResponse> verifyOtp(@RequestBody VerifyOtpRequest request) {

        if (!request.getEmail().equals(ADMIN_EMAIL)) {
            return ResponseEntity.status(403)
                    .body(new MessageResponse("Invalid email."));
        }

        User user = userRepository.findByUsername(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Admin not found."));

        if (user.getOtpCode() == null || !user.getOtpCode().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP.");
        }

        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired.");
        }

        return ResponseEntity.ok(new MessageResponse("OTP verified."));
    }

    // -----------------------------------------------------
    // RESET PASSWORD
    // -----------------------------------------------------
    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody ResetPasswordRequest request) {

        if (!request.getEmail().equals(ADMIN_EMAIL)) {
            return ResponseEntity.status(403)
                    .body(new MessageResponse("Invalid email."));
        }

        User user = userRepository.findByUsername(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Admin not found."));

        if (user.getOtpExpiry() == null || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired or invalid.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setOtpCode(null);
        user.setOtpExpiry(null);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Password reset successfully."));
    }
}
