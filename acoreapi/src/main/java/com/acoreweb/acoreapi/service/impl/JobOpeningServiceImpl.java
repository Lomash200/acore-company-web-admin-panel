package com.acoreweb.acoreapi.service.impl;

import com.acoreweb.acoreapi.dto.JobOpeningDto;
import com.acoreweb.acoreapi.dto.JobOpeningRequest;
import com.acoreweb.acoreapi.model.JobOpening;
import com.acoreweb.acoreapi.model.Requirement;
import com.acoreweb.acoreapi.model.Responsibility;
import com.acoreweb.acoreapi.repository.JobOpeningRepository;
import com.acoreweb.acoreapi.service.JobOpeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import this

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobOpeningServiceImpl implements JobOpeningService {

    @Autowired
    private JobOpeningRepository jobOpeningRepository;

    // ----- Helper: Model ko DTO me convert karna -----
    private JobOpeningDto mapToDto(JobOpening job) {
        JobOpeningDto dto = new JobOpeningDto();
        dto.setId(job.getId());
        dto.setPositionTitle(job.getPositionTitle());
        dto.setDepartment(job.getDepartment());
        dto.setEmploymentType(job.getEmploymentType());
        dto.setWorkMode(job.getWorkMode());
        dto.setLocation(job.getLocation());
        dto.setExperienceLevel(job.getExperienceLevel());
        dto.setJobDescription(job.getJobDescription());
        dto.setPostedDate(job.getPostedDate());

        // Requirements (text) ki list nikalna
        dto.setRequirements(job.getRequirements().stream()
                .map(Requirement::getText)
                .collect(Collectors.toList()));

        // Responsibilities (text) ki list nikalna
        dto.setResponsibilities(job.getResponsibilities().stream()
                .map(Responsibility::getText)
                .collect(Collectors.toList()));

        return dto;
    }

    // ----- Service Methods -----

    @Override
    @Transactional // Zaruri hai One-to-Many ke liye
    public JobOpeningDto createJobOpening(JobOpeningRequest request) {
        JobOpening job = new JobOpening();
        job.setPositionTitle(request.getPositionTitle());
        job.setDepartment(request.getDepartment());
        job.setEmploymentType(request.getEmploymentType());
        job.setWorkMode(request.getWorkMode());
        job.setLocation(request.getLocation());
        job.setExperienceLevel(request.getExperienceLevel());
        job.setJobDescription(request.getJobDescription());
        job.setPostedDate(LocalDate.now());

        // Requirements ki list (String) ko Requirement (Objects) me badalna
        if (request.getRequirements() != null) {
            request.getRequirements().stream()
                    .map(Requirement::new) // new Requirement("text")
                    .forEach(job::addRequirement); // Helper method use kiya
        }

        // Responsibilities ki list (String) ko Responsibility (Objects) me badalna
        if (request.getResponsibilities() != null) {
            request.getResponsibilities().stream()
                    .map(Responsibility::new) // new Responsibility("text")
                    .forEach(job::addResponsibility); // Helper method use kiya
        }

        // Job save karne se (CascadeType.ALL ke karan)
        // requirements aur responsibilities bhi save ho jayenge.
        JobOpening savedJob = jobOpeningRepository.save(job);
        return mapToDto(savedJob);
    }

    @Override
    public List<JobOpeningDto> getAllJobOpenings() {
        return jobOpeningRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public JobOpeningDto getJobOpeningById(Long id) {
        JobOpening job = jobOpeningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobOpening not found with id: " + id));
        return mapToDto(job);
    }

    @Override
    @Transactional
    public JobOpeningDto updateJobOpening(Long id, JobOpeningRequest request) {
        JobOpening job = jobOpeningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobOpening not found with id: " + id));

        // Basic fields update karna
        job.setPositionTitle(request.getPositionTitle());
        job.setDepartment(request.getDepartment());
        job.setEmploymentType(request.getEmploymentType());
        job.setWorkMode(request.getWorkMode());
        job.setLocation(request.getLocation());
        job.setExperienceLevel(request.getExperienceLevel());
        job.setJobDescription(request.getJobDescription());

        // Purani requirements/responsibilities clear karna
        // 'orphanRemoval = true' ke karan yeh DB se bhi delete ho jayengi
        job.getRequirements().clear();
        job.getResponsibilities().clear();

        // Nayi requirements add karna
        if (request.getRequirements() != null) {
            request.getRequirements().stream()
                    .map(Requirement::new)
                    .forEach(job::addRequirement);
        }

        // Nayi responsibilities add karna
        if (request.getResponsibilities() != null) {
            request.getResponsibilities().stream()
                    .map(Responsibility::new)
                    .forEach(job::addResponsibility);
        }

        JobOpening updatedJob = jobOpeningRepository.save(job);
        return mapToDto(updatedJob);
    }

    @Override
    public void deleteJobOpening(Long id) {
        JobOpening job = jobOpeningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("JobOpening not found with id: " + id));

        // 'CascadeType.ALL' ke karan, job delete karne se
        // usse jude requirements aur responsibilities bhi delete ho jayenge.
        jobOpeningRepository.delete(job);
    }
}