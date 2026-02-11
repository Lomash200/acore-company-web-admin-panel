package com.acoreweb.acoreapi.controller;

import com.acoreweb.acoreapi.dto.ProjectDto;
import com.acoreweb.acoreapi.dto.ProjectRequest;
import com.acoreweb.acoreapi.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // Date/Time ke liye
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/projects")
@CrossOrigin(origins = "http://localhost:5174") // Admin dashboard ko allow karega
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // ObjectMapper ko configure karna padega taaki 'LocalDate' (startDate, endDate) ko samjhe
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // ----- Naya Project Create Karna (POST) -----
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProjectDto> createProject(
            @RequestParam("projectData") String projectDataString,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        // JSON string ko ProjectRequest object me convert karna
        ProjectRequest projectRequest = objectMapper.readValue(projectDataString, ProjectRequest.class);

        // Service ko call karke project create karna
        ProjectDto createdProject = projectService.createProject(projectRequest, imageFile);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    // ----- Sare Projects Get Karna (GET) -----
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<ProjectDto> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    // ----- Ek Project Get Karna (GET) -----
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        ProjectDto project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    // ----- Project Update Karna (PUT) -----
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProjectDto> updateProject(
            @PathVariable Long id,
            @RequestParam("projectData") String projectDataString,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {

        ProjectRequest projectRequest = objectMapper.readValue(projectDataString, ProjectRequest.class);
        ProjectDto updatedProject = projectService.updateProject(id, projectRequest, imageFile);
        return ResponseEntity.ok(updatedProject);
    }

    // ----- Project Delete Karna (DELETE) -----
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}