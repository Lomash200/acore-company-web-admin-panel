package com.acoreweb.acoreapi.dto;

import lombok.Data;
import java.util.List;

@Data
public class JobOpeningRequest {

    private String positionTitle;
    private String department;
    private String employmentType;
    private String workMode;
    private String location;
    private String experienceLevel;
    private String jobDescription;

    // React se 'text' ki list bhejenge
    // e.g., ["Java", "Spring Boot", "SQL"]
    private List<String> requirements;

    // e.g., ["Develop APIs", "Test code"]
    private List<String> responsibilities;
}