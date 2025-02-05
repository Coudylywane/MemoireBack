package com.example.construction.services;

import com.example.construction.models.Project;
import com.example.construction.response.ProjectResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.construction.request.ProjectRequestDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    public Page<Project> getAllProject(Pageable pageable) {
        return null;
    }

    public Optional<Project> getProjectById(String projectId) {
        return null;
    }

    public Project createProject(ProjectRequestDto projectRequestDto) {
        return null;
    }

    public Project addSitesToProject(ProjectRequestDto projectRequestDto) {
        return null;
    }

    public ProjectResponseDto mapProjectToResponseDto(Project updatedProject) {
        return null;
    }

   public Project getById(String projectId) {
        return null;
    }

}
