package com.example.construction.controllers;
import com.example.construction.models.Tache;
import com.example.construction.repositories.ProjectRepository;
import com.example.construction.repositories.TacheRepository;
import com.example.construction.services.PlanningService;
import org.modelmapper.ModelMapper;
import com.example.construction.dto.ProjectDetailDto;
import com.example.construction.dto.ProjectDto;
import com.example.construction.mapper.MapStructMapper;
import com.example.construction.mapper.ModelMapperConfig;
import com.example.construction.models.Projet;
import com.example.construction.request.ProjectRequestDto;
import com.example.construction.response.ProjectResponseDto;
import com.example.construction.services.ProjectService;
import com.example.construction.utils.RequestHeaderParser;
import com.example.construction.utils.Util;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@Slf4j
@RequiredArgsConstructor
public class ProjectController {
    private final ModelMapper modelMapper; // Injection correcte de ModelMapper
    private final ProjectService projectService;
    private final TacheRepository tacheRepository ;
    private final ProjectRepository projectRepository;
    private final PlanningService planningService;


    @GetMapping()
    public ResponseEntity<Page<ProjectDto>> getAllProjects(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "25") int perPage,
            @RequestParam(value = "orderBy", required = false, defaultValue = "") String orderBy,
            @RequestParam(value = "direction", required = false, defaultValue = "") String direction) {
        log.debug("Récupération des projets avec page={}, perPage={}, orderBy={}, direction={}", page, perPage, orderBy, direction);
        try {
            Pageable pageable = Util.getPageable(page, perPage, orderBy, direction);
            Page<Projet> result = projectService.getAllProject(pageable);
            // Vérification du résultat
            if (result.isEmpty()) {
                log.warn("Aucun projet trouvé.");
            } else {
                log.debug("{} projets trouvés.", result.getTotalElements());
            }
            Page<ProjectDto> dtoPage = result.map(project -> modelMapper.map(project, ProjectDto.class));
            return ResponseEntity.ok(dtoPage);
        } catch (RuntimeException e) {
            log.error("Erreur lors de la récupération des projets: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Page.empty());
        }
    }


//    @GetMapping("/{memberNumber}/{id}")
//    public ResponseEntity<ProjectDetailDto> getProjectById(@PathVariable String memberNumber, @PathVariable String id) {
//        if (!RequestHeaderParser.verifyUserName(memberNumber)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
//        }
//        try {
//            Optional<Project> project = projectService.getProjectById(id);
//            return ResponseEntity.status(HttpStatus.OK).body(mapStructMapper.projectToProjectDetailDto(project.get()));
//        } catch (IllegalArgumentException ex) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }

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
        Projet project = projectService.createProject(projectRequestDto);
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
    Projet updatedProject = projectService.addSitesToProject(projectRequestDto);
    ProjectResponseDto responseDto = projectService.mapProjectToResponseDto(updatedProject);
    return ResponseEntity.ok(responseDto);
  }

    @GetMapping("/{projetId}/taches")
    public ResponseEntity<List<Tache>> getTachesByProjet(@PathVariable Long projetId) {
        List<Tache> taches = tacheRepository.findByProjetId(projetId);
        return ResponseEntity.ok(taches);
    }

//    @PostMapping("/{projetId}/taches")
//    public ResponseEntity<List<Tache>> ajouterTaches(@PathVariable Long projetId, @RequestBody List<Tache> nouvellesTaches) {
//        Project projet = projectRepository.findById(projetId)
//                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));
//
//        // Associer les nouvelles tâches au projet
//        for (Tache tache : nouvellesTaches) {
//            tache.setProjet(projet);
//        }
//
//        // Sauvegarder les nouvelles tâches
//        List<Tache> tachesSauvegardees = tacheRepository.saveAll(nouvellesTaches);
//
//        // Régénérer le planning
//        planningService.regenererPlanning();
//
//        return ResponseEntity.ok(tachesSauvegardees);
//    }

    @PostMapping("/{projetId}/taches")
    public ResponseEntity<List<Tache>> ajouterTaches(@PathVariable Long projetId, @RequestBody List<Tache> nouvellesTaches) {
        Projet projet = projectRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        // Associer les nouvelles tâches au projet
        for (Tache tache : nouvellesTaches) {
            //tache.setProjet(projet);
        }

        // Sauvegarder les nouvelles tâches
        List<Tache> tachesSauvegardees = tacheRepository.saveAll(nouvellesTaches);

        // Régénérer le planning
        planningService.regenererPlanning(projet);

        return ResponseEntity.ok(tachesSauvegardees);
    }

    @PutMapping("/taches/{tacheId}/pourcentage")
    public ResponseEntity<Tache> mettreAJourPourcentage(
            @PathVariable Long tacheId,
            @RequestParam int pourcentage) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));

        // Valider le pourcentage (doit être entre 0 et 100)
        if (pourcentage < 0 || pourcentage > 100) {
            throw new RuntimeException("Le pourcentage doit être compris entre 0 et 100.");
        }

        tache.setPourcentageExecution(pourcentage);

        // Mettre à jour le statut en fonction du pourcentage
        if (pourcentage == 100) {
            tache.setStatut("Terminée");
        } else if (pourcentage > 0) {
            tache.setStatut("En cours");
        } else {
            tache.setStatut("En attente");
        }

        Tache tacheMiseAJour = tacheRepository.save(tache);
        return ResponseEntity.ok(tacheMiseAJour);
    }

    @PutMapping("/taches/{tacheId}/statut")
    public ResponseEntity<Tache> mettreAJourStatut(
            @PathVariable Long tacheId,
            @RequestParam String statut) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée"));

        // Valider le statut
        if (!statut.equals("En attente") && !statut.equals("En cours") && !statut.equals("Terminée")) {
            throw new RuntimeException("Statut invalide. Les valeurs autorisées sont : En attente, En cours, Terminée.");
        }

        tache.setStatut(statut);

        // Mettre à jour le pourcentage d'exécution en fonction du statut
        if (statut.equals("Terminée")) {
            tache.setPourcentageExecution(100);
        } else if (statut.equals("En cours")) {
            tache.setPourcentageExecution(50); // Ou une autre valeur par défaut
        } else {
            tache.setPourcentageExecution(0);
        }

        Tache tacheMiseAJour = tacheRepository.save(tache);
        return ResponseEntity.ok(tacheMiseAJour);
    }


}
