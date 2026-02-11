package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.JobOpeningDto;
import com.acoreweb.acoreapi.dto.JobOpeningRequest;
import com.acoreweb.acoreapi.service.JobOpeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/jobs")
@CrossOrigin(origins = "http://localhost:5174") // Admin dashboard ko allow karega
public class JobOpeningController {

    @Autowired
    private JobOpeningService jobOpeningService;

    // ----- Naya Job Create Karna (POST) -----
    // Yeh simple JSON lega
    @PostMapping
    public ResponseEntity<JobOpeningDto> createJobOpening(@RequestBody JobOpeningRequest request) {
        JobOpeningDto createdJob = jobOpeningService.createJobOpening(request);
        return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
    }

    // ----- Sare Jobs Get Karna (GET) -----
    @GetMapping
    public ResponseEntity<List<JobOpeningDto>> getAllJobOpenings() {
        List<JobOpeningDto> jobs = jobOpeningService.getAllJobOpenings();
        return ResponseEntity.ok(jobs);
    }

    // ----- Ek Job Get Karna (GET) -----
    @GetMapping("/{id}")
    public ResponseEntity<JobOpeningDto> getJobOpeningById(@PathVariable Long id) {
        JobOpeningDto job = jobOpeningService.getJobOpeningById(id);
        return ResponseEntity.ok(job);
    }

    // ----- Job Update Karna (PUT) -----
    @PutMapping("/{id}")
    public ResponseEntity<JobOpeningDto> updateJobOpening(
            @PathVariable Long id,
            @RequestBody JobOpeningRequest request) {

        JobOpeningDto updatedJob = jobOpeningService.updateJobOpening(id, request);
        return ResponseEntity.ok(updatedJob);
    }

    // ----- Job Delete Karna (DELETE) -----
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOpening(@PathVariable Long id) {
        jobOpeningService.deleteJobOpening(id);
        return ResponseEntity.noContent().build();
    }
}