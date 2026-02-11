package com.acoreweb.acoreapi.service;

import com.acoreweb.acoreapi.dto.JobOpeningDto;
import com.acoreweb.acoreapi.dto.JobOpeningRequest;

import java.util.List;

public interface JobOpeningService {

    JobOpeningDto createJobOpening(JobOpeningRequest request);

    JobOpeningDto updateJobOpening(Long id, JobOpeningRequest request);

    List<JobOpeningDto> getAllJobOpenings();

    JobOpeningDto getJobOpeningById(Long id);

    void deleteJobOpening(Long id);
}