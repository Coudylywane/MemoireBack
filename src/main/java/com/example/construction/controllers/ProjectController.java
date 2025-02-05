package com.example.construction.controllers;

import com.example.construction.dto.ProjectDetailDto;
import com.example.construction.dto.ProjectDto;
import com.example.construction.mapper.MapStructMapper;
import com.example.construction.models.Project;
import com.example.construction.request.ProjectRequestDto;
import com.example.construction.response.ProjectResponseDto;
import com.example.construction.services.ProjectService;
import com.example.construction.utils.RequestHeaderParser;
import com.example.construction.utils.Util;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;


import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@Slf4j
public class ProjectController {
    private final ProjectService projectService;
    private final MapStructMapper mapStructMapper;

    public ProjectController(ProjectService projectService, MapStructMapper mapStructMapper){
        this.projectService = projectService;
        this.mapStructMapper = mapStructMapper;
    }

    @GetMapping()
    @Operation(summary = "Find project ", description = "Retrieve a collection project")
    @ApiResponse(responseCode = "200", description = "Success")
    @ApiResponse(responseCode = "400", description = "Bad request", content = {})
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<Page<ProjectDto>> getAllProjects(
            @Parameter(name = "memberNumber", description= "memberNumber")
            @RequestParam(value = "memberNumber", required = false) String memberNumber,
            @Parameter(name = "page", description = "Page number to display")
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @Parameter(name = "perPage", description = "Number of element to show by page")
            @RequestParam(value = "perPage", required = false, defaultValue = "25") int perPage,
            @Parameter(name = "orderBy", description = "Name of the field to order")
            @RequestParam(value = "orderBy", required = false, defaultValue = "") String orderBy,
            @Parameter(name = "direction", description = "Order ASC or DESC")
            @RequestParam(value = "direction", required = false, defaultValue = "") String direction) {

        if (!RequestHeaderParser.verifyUserName(memberNumber)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        log.debug("Recupération des projects");
        try {
            Pageable pageable = Util.getPageable(page, perPage, orderBy, direction);
            Page<Project> result = projectService.getAllProject(pageable);
            return ResponseEntity.status(HttpStatus.OK).body(result.map(mapStructMapper::projectToProjectDto));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Page.empty());
        }
    }

    @GetMapping("/{memberNumber}/{id}")
    public ResponseEntity<ProjectDetailDto> getProjectById(@PathVariable String memberNumber, @PathVariable String id) {
        if (!RequestHeaderParser.verifyUserName(memberNumber)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        try {
            Optional<Project> project = projectService.getProjectById(id);
            return ResponseEntity.status(HttpStatus.OK).body(mapStructMapper.projectToProjectDetailDto(project.get()));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/create")
    @Operation(
            summary = "Create a new project",
            description = "Creates a new project based on the provided project request data.",
            tags = { "Projects" })
    @ApiResponse(responseCode = "200", description = "Project created successfully")
    @ApiResponse(responseCode = "400", description = "Bad request due to validation errors")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequestDto projectRequestDto) {
      try {
        if (projectRequestDto == null) {
          throw new IllegalArgumentException("Le projet ne peut pas être nul.");
        }
        Project project = projectService.createProject(projectRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(project);
      } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", "Erreur de validation", "message", e.getMessage()));
      } catch (DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Conflit de données", "message", e.getMessage()));
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur inattendue", "message", e.getMessage()));
      }
    }

  @PostMapping("/add-sites")
  public ResponseEntity<ProjectResponseDto> addSitesToProject(@RequestBody ProjectRequestDto projectRequestDto) {
    Project updatedProject = projectService.addSitesToProject(projectRequestDto);
    ProjectResponseDto responseDto = projectService.mapProjectToResponseDto(updatedProject);
    return ResponseEntity.ok(responseDto);
  }

}
