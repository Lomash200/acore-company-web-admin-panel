package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.ProjectDto;
import com.acoreweb.acoreapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public/projects") // URL 'public' hai
public class PublicProjectController {

    @Autowired
    private ProjectService projectService; // Wahi service reuse kar rahe hain

    // ----- Public: Sare Projects Get Karna (List) -----
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    // ----- Public: Ek Project Get Karna (Details) -----
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        ProjectDto project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }
}