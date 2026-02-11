package com.acoreweb.acoreapi.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class JobOpeningDto {

    private Long id;
    private String positionTitle;
    private String department;
    private String employmentType;
    private String workMode;
    private String location;
    private String experienceLevel;
    private String jobDescription;
    private LocalDate postedDate;

    // Response me apan sirf text ki list bhejenge
    private List<String> requirements;
    private List<String> responsibilities;
}