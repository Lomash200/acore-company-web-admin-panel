package com.acoreweb.acoreapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "testimonials")
@Data
@NoArgsConstructor
public class Testimonial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String quote; // Jo user ne kaha

    @Column(nullable = false)
    private String personName; // User ka naam

    private String personPosition; // e.g., "CEO, TechCorp"

    // Yahan @Data use kar sakte hain kyunki koi relationship nahi hai,
    // toh StackOverflowError nahi aayega.
}