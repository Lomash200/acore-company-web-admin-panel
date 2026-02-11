package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.JobOpeningDto;
import com.acoreweb.acoreapi.service.JobOpeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/jobs") // URL 'public' hai
public class PublicJobOpeningController {

    @Autowired
    private JobOpeningService jobOpeningService; // Wahi service reuse kar rahe hain

    // ----- Public: Sari Jobs Get Karna (List) -----
    @GetMapping
    public ResponseEntity<List<JobOpeningDto>> getAllJobOpenings() {
        List<JobOpeningDto> jobs = jobOpeningService.getAllJobOpenings();
        return ResponseEntity.ok(jobs);
    }

    // ----- Public: Ek Job Get Karna (Details) -----
    @GetMapping("/{id}")
    public ResponseEntity<JobOpeningDto> getJobOpeningById(@PathVariable Long id) {
        JobOpeningDto job = jobOpeningService.getJobOpeningById(id);
        return ResponseEntity.ok(job);
    }
}