package com.acoreweb.acoreapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
@Data
@NoArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String candidateName;

    @Column(nullable = false)
    private String candidateEmail;

    private String candidatePhone;

    // Jis job ke liye apply kiya
    private String appliedForPosition;

    // Resume file ka path/URL
    private String resumeUrl;

    // Status tracking ke liye (e.g., New, Review, Shortlisted)
    private String status = "New";

    @Column(updatable = false)
    private LocalDateTime applicationDate;

    @PrePersist
    protected void onCreate() {
        this.applicationDate = LocalDateTime.now();
    }
}