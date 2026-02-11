package com.acoreweb.acoreapi.repository;

import com.acoreweb.acoreapi.model.JobOpening;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOpeningRepository extends JpaRepository<JobOpening, Long> {

    // 'findAll' call karte time 'requirements' aur 'responsibilities'
    // ko bhi sath me fetch karega (Lazy loading error se bachne ke liye)
    @Override
    @EntityGraph(attributePaths = {"requirements", "responsibilities"})
    List<JobOpening> findAll();
}