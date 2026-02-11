package com.acoreweb.acoreapi.service;

import com.acoreweb.acoreapi.dto.ProjectDto;
import com.acoreweb.acoreapi.dto.ProjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectService {

    // Naya project create karna (Image file ke sath)
    ProjectDto createProject(ProjectRequest projectRequest, MultipartFile imageFile);

    // Project update karna (Optional: nayi image file ke sath)
    ProjectDto updateProject(Long id, ProjectRequest projectRequest, MultipartFile imageFile);

    // Sare projects get karna
    List<ProjectDto> getAllProjects();

    // Ek single project get karna
    ProjectDto getProjectById(Long id);

    // Project delete karna
    void deleteProject(Long id);
}