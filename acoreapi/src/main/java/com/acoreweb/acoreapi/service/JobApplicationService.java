package com.acoreweb.acoreapi.service;

import com.acoreweb.acoreapi.model.JobApplication;
import com.acoreweb.acoreapi.repository.JobApplicationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository applicationRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String uploadDirectory = "resumes";

    // 🔹 1. CREATE — Public Job Application Submit
    public JobApplication processJobApplication(String applicationDataJson,
                                                MultipartFile resumeFile) throws JsonProcessingException {

        // Convert JSON string to object
        JobApplication application =
                objectMapper.readValue(applicationDataJson, JobApplication.class);

        // Generate clean filename
        String fileName = StringUtils.cleanPath(
                application.getCandidateName() + "_" + application.getAppliedForPosition()
        );

        // Save file
        String resumeUrl =
                fileStorageService.storeFile(resumeFile, uploadDirectory, fileName);

        // Set saved path
        application.setResumeUrl(resumeUrl);

        // Save application
        return applicationRepository.save(application);
    }

    // 🔹 2. GET ALL (Admin)
    public List<JobApplication> getAllApplications() {
        return applicationRepository.findAll();
    }

    // 🔹 3. GET ONE BY ID (Admin)
    public JobApplication getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found: " + id));
    }

    // 🔹 4. DELETE (Admin)
    public void deleteApplication(Long id) {
        JobApplication application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found: " + id));

        // Resume file delete bhi karna hai (optional but good)
        if (application.getResumeUrl() != null) {
            fileStorageService.deleteFile(application.getResumeUrl());
        }

        applicationRepository.delete(application);
    }
}
