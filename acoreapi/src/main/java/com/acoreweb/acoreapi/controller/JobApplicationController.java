package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.model.JobApplication;
import com.acoreweb.acoreapi.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/public/applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService applicationService;

    // 🔹 0. PUBLIC — Create Job Application (POST)
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createApplication(
            @RequestPart("applicationData") String applicationDataJson,
            @RequestPart("resumeFile") MultipartFile resumeFile
    ) {
        try {
            JobApplication saved = applicationService.processJobApplication(applicationDataJson, resumeFile);
            return ResponseEntity.status(201).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // 🔹 1. Get all applications
    @GetMapping
    public ResponseEntity<List<JobApplication>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    // 🔹 2. Get single application by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getApplication(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    // 🔹 3. Delete application
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}
