package com.acoreweb.acoreapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ProjectDto {

    private Long id;
    private String projectName;
    private String description;
    private String imageUrl; // Yahan par apan URL bhejenge
    private String status;
    private String clientName;
    private LocalDate startDate;
    private LocalDate endDate;

    // Response me apan sirf naam bhejenge
    private Set<String> technologies;
}