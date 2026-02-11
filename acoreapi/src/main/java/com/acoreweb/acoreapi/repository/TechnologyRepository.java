package com.acoreweb.acoreapi.repository;

import com.acoreweb.acoreapi.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {

    // Duplicate technology na banan'e ke liye
    Optional<Technology> findByName(String name);
}