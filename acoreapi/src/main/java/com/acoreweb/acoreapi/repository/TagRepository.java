package com.acoreweb.acoreapi.repository;

import com.acoreweb.acoreapi.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // Yeh method apan ko tag name se find karne me help karega
    // Taaki apan duplicate tag na banayein
    Optional<Tag> findByName(String name);
}