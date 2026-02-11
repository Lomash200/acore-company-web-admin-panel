package com.acoreweb.acoreapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "technologies")
@Getter
@Setter
@NoArgsConstructor
public class Technology {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // 'mappedBy' batata hai ki Project class is relationship ko manage karegi
    @ManyToMany(mappedBy = "technologies")
    private Set<Project> projects = new HashSet<>();

    // Helper constructor
    public Technology(String name) {
        this.name = name;
    }
}