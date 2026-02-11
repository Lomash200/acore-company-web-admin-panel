package com.acoreweb.acoreapi.service.impl;

import com.acoreweb.acoreapi.dto.ProjectDto;
import com.acoreweb.acoreapi.dto.ProjectRequest;
import com.acoreweb.acoreapi.model.Project;
import com.acoreweb.acoreapi.model.Technology;
import com.acoreweb.acoreapi.repository.ProjectRepository;
import com.acoreweb.acoreapi.repository.TechnologyRepository;
import com.acoreweb.acoreapi.service.FileStorageService;
import com.acoreweb.acoreapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;     // << IMPORTANT
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional     // << FULL SERVICE AB TRANSACTIONAL HAI
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Value("${app.upload-url}")
    private String uploadUrl;

    // -------------------- DTO MAPPER --------------------
    private ProjectDto mapToDto(Project project) {
        ProjectDto dto = new ProjectDto();
        dto.setId(project.getId());
        dto.setProjectName(project.getProjectName());
        dto.setDescription(project.getDescription());
        dto.setClientName(project.getClientName());
        dto.setStatus(project.getStatus());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());

        if (project.getImageUrl() != null) {
            String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(uploadUrl + "/")
                    .path(project.getImageUrl())
                    .toUriString();
            dto.setImageUrl(imageUrl);
        }

        if (project.getTechnologies() != null) {
            dto.setTechnologies(
                    project.getTechnologies().stream()
                            .map(Technology::getName)
                            .collect(Collectors.toSet())
            );
        }

        return dto;
    }

    // -------------------- PROCESS TECHNOLOGIES --------------------
    private Set<Technology> processTechnologies(Set<String> techNames) {
        Set<Technology> technologies = new HashSet<>();
        if (techNames != null) {
            for (String techName : techNames) {
                Technology tech = technologyRepository.findByName(techName)
                        .orElseGet(() -> technologyRepository.save(new Technology(techName)));
                technologies.add(tech);
            }
        }
        return technologies;
    }

    // -------------------- CREATE PROJECT --------------------
    @Override
    public ProjectDto createProject(ProjectRequest projectRequest, MultipartFile imageFile) {

        String fileName = null;

        try {
            fileName = fileStorageService.storeFile(
                    imageFile,
                    "projects",
                    projectRequest.getProjectName()
            );
        } catch (Exception e) {
            System.err.println("IMAGE UPLOAD FAILED: " + e.getMessage());
        }

        Set<Technology> technologies = processTechnologies(projectRequest.getTechnologies());

        Project project = new Project();
        project.setProjectName(projectRequest.getProjectName());
        project.setDescription(projectRequest.getDescription());
        project.setClientName(projectRequest.getClientName());
        project.setStatus(projectRequest.getStatus());
        project.setStartDate(projectRequest.getStartDate());
        project.setEndDate(projectRequest.getEndDate());
        project.setTechnologies(technologies);
        project.setImageUrl(fileName);

        // IMPORTANT → saveAndFlush ensures commit immediately
        Project savedProject = projectRepository.saveAndFlush(project);

        System.out.println(">>> PROJECT SAVED with ID = " + savedProject.getId());

        return mapToDto(savedProject);
    }

    // -------------------- GET ALL --------------------
    @Override
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // -------------------- GET BY ID --------------------
    @Override
    public ProjectDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        return mapToDto(project);
    }

    // -------------------- DELETE --------------------
    @Override
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        if (project.getImageUrl() != null) {
            fileStorageService.deleteFile(project.getImageUrl());
        }

        projectRepository.delete(project);
    }

    // -------------------- UPDATE --------------------
    @Override
    public ProjectDto updateProject(Long id, ProjectRequest projectRequest, MultipartFile imageFile) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        project.setProjectName(projectRequest.getProjectName());
        project.setDescription(projectRequest.getDescription());
        project.setClientName(projectRequest.getClientName());
        project.setStatus(projectRequest.getStatus());
        project.setStartDate(projectRequest.getStartDate());
        project.setEndDate(projectRequest.getEndDate());

        Set<Technology> technologies = processTechnologies(projectRequest.getTechnologies());
        project.setTechnologies(technologies);

        if (imageFile != null && !imageFile.isEmpty()) {

            if (project.getImageUrl() != null) {
                fileStorageService.deleteFile(project.getImageUrl());
            }

            String newFileName = fileStorageService.storeFile(
                    imageFile,
                    "projects",
                    projectRequest.getProjectName()
            );

            project.setImageUrl(newFileName);
        }

        Project updated = projectRepository.saveAndFlush(project);

        System.out.println(">>> PROJECT UPDATED ID = " + updated.getId());

        return mapToDto(updated);
    }
}
