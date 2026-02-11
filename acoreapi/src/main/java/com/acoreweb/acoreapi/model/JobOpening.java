package com.acoreweb.acoreapi.model;

import jakarta.persistence.*;
import lombok.Getter; // Import kar
import lombok.NoArgsConstructor;
import lombok.Setter; // Import kar
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "job_openings")
@Getter // @Data ki jagah yeh
@Setter // @Data ki jagah yeh
@NoArgsConstructor
public class JobOpening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ... baki sare fields ...
    @Column(nullable = false)
    private String positionTitle;

    private String department;
    private String employmentType;
    private String workMode;
    private String location;
    private String experienceLevel;

    @Column(length = 5000)
    private String jobDescription;

    private LocalDate postedDate;

    @OneToMany(
            mappedBy = "jobOpening",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Requirement> requirements = new HashSet<>();

    @OneToMany(
            mappedBy = "jobOpening",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Responsibility> responsibilities = new HashSet<>();

    // Helper methods
    public void addRequirement(Requirement requirement) {
        requirements.add(requirement);
        requirement.setJobOpening(this);
    }

    public void addResponsibility(Responsibility responsibility) {
        responsibilities.add(responsibility);
        responsibility.setJobOpening(this);
    }
}