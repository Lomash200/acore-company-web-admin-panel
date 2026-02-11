package com.acoreweb.acoreapi.repository;

import com.acoreweb.acoreapi.model.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    // Ismein abhi koi custom method ki zaroorat nahi hai.
}