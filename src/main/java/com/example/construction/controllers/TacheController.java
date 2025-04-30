package com.example.construction.controllers;

import com.example.construction.models.Article;
import com.example.construction.models.Tache;
import com.example.construction.models.TacheArticle;
import com.example.construction.models.enumeration.TaskStatus;
import com.example.construction.repositories.TacheArticleRepository;
import com.example.construction.repositories.TacheRepository;
import com.example.construction.request.TacheArticleDto;
import com.example.construction.request.TacheDto;
import com.example.construction.services.PdfService;
import com.example.construction.services.TacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/taches")
@Slf4j
@RequiredArgsConstructor
public class TacheController {
    private final TacheService tacheService;
    private final PdfService pdfService;
    private final TacheRepository tacheRepository;
    private final TacheArticleRepository tacheArticleRepository;

    @PostMapping("/add")
    public ResponseEntity<Tache> ajouterTache(@RequestBody Tache tache) {
        log.info("Ajout d'une nouvelle tâche : {}", tache.getNom());
        Tache nouvelleTache = tacheService.ajouterTache(tache);
        return new ResponseEntity<>(nouvelleTache, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Tache>> listerTaches() {
        log.info("Récupération de toutes les tâches");
        List<Tache> taches = tacheService.listerTaches();
        return new ResponseEntity<>(taches, HttpStatus.OK);
    }

    @GetMapping("/planning/{planningId}")
    public ResponseEntity<List<Tache>> listerTachesParPlanning(@PathVariable Long planningId) {
        log.info("Récupération des tâches pour le planning ID : {}", planningId);
        List<Tache> taches = tacheService.listerTachesParPlanning(planningId);
        return new ResponseEntity<>(taches, HttpStatus.OK);
    }

    @GetMapping("/planning/{planningId}/status/{status}")
    public ResponseEntity<List<Tache>> listerTachesParStatut(
            @PathVariable Long planningId,
            @PathVariable TaskStatus status) {
        log.info("Récupération des tâches pour le planning ID : {} avec statut : {}", planningId, status);
        List<Tache> taches = tacheService.listerTachesParStatut(planningId, status);
        return new ResponseEntity<>(taches, HttpStatus.OK);
    }

    @PutMapping("/taches/update/{id}")
    public TacheDto updateTacheWithArticle(@PathVariable Long id, @RequestBody TacheDto tacheDTO) {
        Tache tache = tacheRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tâche non trouvée : " + id));
        tache.setNom(tacheDTO.getNom());
        tache.setDescription(tacheDTO.getDescription());
        tache.setDureeEstimee(tacheDTO.getDureeEstimee());
        tache.setDateDebut(tacheDTO.getDateDebut());
        tache.setDateFin(tacheDTO.getDateFin());
        tache.setStatus(tacheDTO.getStatus());
        tache.setPourcentageExecution(tacheDTO.getPourcentageExecution());
        Tache updatedTache = tacheRepository.save(tache);

        TacheDto result = new TacheDto();
        result.setId(updatedTache.getId());
        result.setNom(updatedTache.getNom());
        result.setDescription(updatedTache.getDescription());
        result.setDureeEstimee(updatedTache.getDureeEstimee());
        result.setDateDebut(updatedTache.getDateDebut());
        result.setDateFin(updatedTache.getDateFin());
        result.setStatus(updatedTache.getStatus());
        result.setPourcentageExecution(updatedTache.getPourcentageExecution());
        return result;
    }

    @PutMapping("/update/{id}")
    public TacheDto updateTache(@PathVariable Long id, @RequestBody TacheDto tacheDTO) {
        Tache tache = tacheRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tâche non trouvée : " + id));

        // Vérifier que la tâche est en cours
        if (tache.getStatus() != TaskStatus.IN_PROGRESS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seules les tâches en cours peuvent être modifiées.");
        }

        // Mettre à jour les champs de la tâche
        tache.setNom(tacheDTO.getNom());
        tache.setDescription(tacheDTO.getDescription());
        tache.setDureeEstimee(tacheDTO.getDureeEstimee());
        tache.setDateDebut(tacheDTO.getDateDebut());
        tache.setDateFin(tacheDTO.getDateFin());
        tache.setStatus(tacheDTO.getStatus());
        tache.setPourcentageExecution(tacheDTO.getPourcentageExecution());

        // Mettre à jour les articles associés
        // Supprimer les anciennes associations
        tache.getArticles().clear();
        tacheArticleRepository.deleteAll(tache.getArticles());

        // Ajouter les nouvelles associations
        List<TacheArticle> newArticles = tacheDTO.getArticles().stream().map(dto -> {
            TacheArticle tacheArticle = new TacheArticle();
            tacheArticle.setTache(tache);
            tacheArticle.setArticle(new Article());
            tacheArticle.getArticle().setId(dto.getArticleId());
            tacheArticle.setQuantiteUtilisee(dto.getQuantiteUtilisee());
            return tacheArticle;
        }).collect(Collectors.toList());

        tache.getArticles().addAll(newArticles);
        Tache updatedTache = tacheRepository.save(tache);

        // Créer le DTO de réponse
        TacheDto result = new TacheDto();
        result.setId(updatedTache.getId());
        result.setNom(updatedTache.getNom());
        result.setDescription(updatedTache.getDescription());
        result.setDureeEstimee(updatedTache.getDureeEstimee());
        result.setDateDebut(updatedTache.getDateDebut());
        result.setDateFin(updatedTache.getDateFin());
        result.setStatus(updatedTache.getStatus());
        result.setPourcentageExecution(updatedTache.getPourcentageExecution());
        result.setArticles(updatedTache.getArticles().stream().map(ta -> {
            TacheArticleDto taDto = new TacheArticleDto();
            taDto.setArticleId(ta.getArticle().getId());
            taDto.setQuantiteUtilisee(ta.getQuantiteUtilisee());
            return taDto;
        }).collect(Collectors.toList()));

        return result;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerTache(@PathVariable Long id) {
        log.info("Suppression de la tâche ID : {}", id);
        tacheService.supprimerTache(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping(value = "/planning/{planningId}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
//    public ResponseEntity<byte[]> downloadPlanningPdf(@PathVariable Long planningId) {
//        log.info("Génération du PDF pour le planning ID : {}", planningId);
//        List<TacheDto> taches = tacheService.listerTachesParPlanning(planningId);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        try {
//            pdfService.generatePlanningPdf(taches, baos);
//            byte[] pdfBytes = baos.toByteArray();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentDispositionFormData("attachment", "planning-" + planningId + ".pdf");
//            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("Erreur lors de la génération du PDF : {}", e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}