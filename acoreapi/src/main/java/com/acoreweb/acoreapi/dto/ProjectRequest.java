package com.acoreweb.acoreapi.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.Set;

@Data
public class ProjectRequest {

    private String projectName;
    private String description;
    private String status;
    private String clientName;
    private LocalDate startDate;
    private LocalDate endDate;

    // React se 'names' ki list bhejenge
    // e.g., ["react", "nodejs", "mongodb"]
    private Set<String> technologies;
}