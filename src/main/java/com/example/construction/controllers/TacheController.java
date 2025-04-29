package com.example.construction.controllers;

import com.example.construction.models.Tache;
import com.example.construction.models.enumeration.TaskStatus;
import com.example.construction.services.PdfService;
import com.example.construction.services.TacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/taches")
@Slf4j
@RequiredArgsConstructor
public class TacheController {
    private final TacheService tacheService;
    private final PdfService pdfService;

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

    @PutMapping("/{id}")
    public ResponseEntity<Tache> modifierTache(
            @PathVariable Long id,
            @RequestBody Tache tacheDetails) {
        log.info("Mise à jour de la tâche ID : {}", id);
        Tache tacheMiseAJour = tacheService.modifierTache(id, tacheDetails);
        return new ResponseEntity<>(tacheMiseAJour, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerTache(@PathVariable Long id) {
        log.info("Suppression de la tâche ID : {}", id);
        tacheService.supprimerTache(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/planning/{planningId}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> downloadPlanningPdf(@PathVariable Long planningId) {
        log.info("Génération du PDF pour le planning ID : {}", planningId);
        List<Tache> taches = tacheService.listerTachesParPlanning(planningId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            pdfService.generatePlanningPdf(taches, baos);
            byte[] pdfBytes = baos.toByteArray();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "planning-" + planningId + ".pdf");
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Erreur lors de la génération du PDF : {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}