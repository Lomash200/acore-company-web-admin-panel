package com.acoreweb.acoreapi.repository;

import com.acoreweb.acoreapi.model.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // 'findAll' call karte time 'technologies' ko bhi sath me fetch karega
    @Override
    @EntityGraph(attributePaths = {"technologies"})
    List<Project> findAll();
}