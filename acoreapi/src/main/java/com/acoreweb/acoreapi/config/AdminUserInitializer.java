package com.acoreweb.acoreapi.config;

import com.acoreweb.acoreapi.model.User;
import com.acoreweb.acoreapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // FIXED ADMIN EMAIL
        String adminEmail = "acoreithub@gmail.com";

        // Check if admin exists
        if (userRepository.findByUsername(adminEmail).isEmpty()) {

            System.out.println("Admin user not found, creating one...");

            User adminUser = new User();
            adminUser.setUsername(adminEmail);
            adminUser.setPassword(passwordEncoder.encode("Admin@123")); // NEW STRONG PASS
            adminUser.setRole("ROLE_ADMIN");

            userRepository.save(adminUser);

            System.out.println("ADMIN CREATED: " + adminEmail + " | Password: Admin@123");
        } else {
            System.out.println("Admin user already exists: " + adminEmail);
        }
    }
}
