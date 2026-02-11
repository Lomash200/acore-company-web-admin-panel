package com.acoreweb.acoreapi.repository;

import com.acoreweb.acoreapi.model.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {
    // Isme koi custom method ki zarurat nahi hai abhi.
    // JpaRepository ke 'findAll', 'save', 'delete' kaafi hain.
}