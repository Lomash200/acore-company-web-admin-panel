package com.acoreweb.acoreapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String projectName;

    @Column(length = 2000)
    private String description;

    // Yahan apan image ka path/URL save karenge
    private String imageUrl;

    // "active" ya "completed"
    private String status;

    // Kis client ke liye tha
    private String clientName;

    private LocalDate startDate;
    private LocalDate endDate;

    // ----- Many-to-Many Relationship (Bilkul Blog/Tag jaisa) -----
    @ManyToMany
    @JoinTable(
            name = "project_technologies", // Nayi 'join table' ka naam
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id")
    )
    private Set<Technology> technologies = new HashSet<>();

}