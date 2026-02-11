package com.acoreweb.acoreapi.model;

import jakarta.persistence.*;
import lombok.Getter; // Import kar
import lombok.NoArgsConstructor;
import lombok.Setter; // Import kar

@Entity
@Table(name = "job_requirements")
@Getter // @Data ki jagah yeh
@Setter // @Data ki jagah yeh
@NoArgsConstructor
public class Requirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_opening_id", nullable = false)
    private JobOpening jobOpening;

    public Requirement(String text) {
        this.text = text;
    }
}