package com.example.construction.controllers;
import com.example.construction.dto.ProjetDto;
import com.example.construction.models.Tache;
import com.example.construction.models.Validation;
import com.example.construction.repositories.ProjetRepository;
import com.example.construction.repositories.TacheRepository;
import com.example.construction.services.PlanningService;
import org.modelmapper.ModelMapper;
import com.example.construction.models.Projet;
import com.example.construction.request.ProjetRequestDto;
import com.example.construction.response.ProjetResponseDto;
import com.example.construction.services.ProjetService;
import com.example.construction.utils.Util;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projets")
@Slf4j
@RequiredArgsConstructor
public class ProjetController {
    private final ModelMapper modelMapper; // Injection correcte de ModelMapper
    private final ProjetService projetService;
    private final TacheRepository tacheRepository ;
    private final ProjetRepository projectRepository;
    private final PlanningService planningService;


    @GetMapping()
    public ResponseEntity<Page<ProjetDto>> getAllProjects(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "25") int perPage,
            @RequestParam(value = "orderBy", required = false, defaultValue = "") String orderBy,
            @RequestParam(value = "direction", required = false, defaultValue = "") String direction) {
        log.debug("Récupération des projets avec page={}, perPage={}, orderBy={}, direction={}", page, perPage, orderBy, direction);
        try {
            Pageable pageable = Util.getPageable(page, perPage, orderBy, direction);
            Page<Projet> result = projetService.getAllProject(pageable);
            // Vérification du résultat
            if (result.isEmpty()) {
                log.warn("Aucun projet trouvé.");
            } else {
                log.debug("{} projets trouvés.", result.getTotalElements());
            }
            Page<ProjetDto> dtoPage = result.map(project -> modelMapper.map(project, ProjetDto.class));
            return ResponseEntity.ok(dtoPage);
        } catch (RuntimeException e) {
            log.error("Erreur lors de la récupération des projets: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Page.empty());
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
    public ResponseEntity<?> createProject(@RequestBody ProjetRequestDto projectRequestDto) {
      try {
        if (projectRequestDto == null) {
          throw new IllegalArgumentException("Le projet ne peut pas être nul.");
        }
        Projet project = projetService.createProject(projectRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(project);
      } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", "Erreur de validation", "message", e.getMessage()));
      } catch (DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Conflit de données", "message", e.getMessage()));
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur inattendue", "message", e.getMessage()));
      }
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "Project details retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Project not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<?> getProjectDetails(@PathVariable Long id) {
        log.debug("Récupération des détails du projet avec ID: {}", id);
        try {
            Projet projet = projetService.getProjetById(id);
            if (projet == null) {
                log.warn("Aucun projet trouvé avec l'ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Projet non trouvé", "message", "Aucun projet trouvé avec l'ID: " + id));
            }

            // Mapper le projet et ses devis vers le DTO
            ProjetDto projetDto = modelMapper.map(projet, ProjetDto.class);

            return ResponseEntity.ok(projetDto);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des détails du projet: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erreur interne du serveur", "message", e.getMessage()));
        }
    }



    @PostMapping("/add-sites")
  public ResponseEntity<ProjetResponseDto> addSitesToProject(@RequestBody ProjetRequestDto projectRequestDto) {
    Projet updatedProject = projetService.addSitesToProject(projectRequestDto);
    ProjetResponseDto responseDto = projetService.mapProjectToResponseDto(updatedProject);
    return ResponseEntity.ok(responseDto);
  }

//    @GetMapping("/{projetId}/taches")
//    public ResponseEntity<List<Tache>> getTachesByProjet(@PathVariable Long projetId) {
//        List<Tache> taches = tacheRepository.findByProjetId(projetId);
//        return ResponseEntity.ok(taches);
//    }


//    @PostMapping("/{projetId}/taches")
//    public ResponseEntity<List<Tache>> ajouterTaches(@PathVariable Long projetId, @RequestBody List<Tache> nouvellesTaches) {
//        Projet projet = projectRepository.findById(projetId)
//                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));
//        // Associer les nouvelles tâches au projet
//        for (Tache tache : nouvellesTaches) {
//            tache.setProjet(projet);
//        }
//        // Sauvegarder les nouvelles tâches
//        List<Tache> tachesSauvegardees = tacheRepository.saveAll(nouvellesTaches);
//        // Régénérer le planning
//        planningService.regenererPlanning(projet);
//        return ResponseEntity.ok(tachesSauvegardees);
//    }

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

    @PostMapping("/project/{id}")
    public ResponseEntity<Projet> addValidationToProjet(
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(projetService.addValidationToProjet(id, status));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjetDto>> getAllProjects() {
        List<Projet> projets = projetService.getAllProjects();
        List<ProjetDto> dtoList = projets.stream()
                .map(projet -> modelMapper.map(projet, ProjetDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }


}
