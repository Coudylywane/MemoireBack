package com.example.construction.controllers;

import com.example.construction.models.Article;
import com.example.construction.models.Tache;
import com.example.construction.models.TacheArticle;
import com.example.construction.models.enumeration.TaskStatus;
import com.example.construction.repositories.ArticleRepository;
import com.example.construction.repositories.TacheArticleRepository;
import com.example.construction.repositories.TacheRepository;
import com.example.construction.request.TacheArticleDto;
import com.example.construction.request.TacheDto;
import com.example.construction.services.PdfService;
import com.example.construction.services.TacheArticleService;
import com.example.construction.services.TacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private final ArticleRepository articleRepository;
    private final TacheArticleService tacheArticleService;

    @PostMapping("/add")
    public ResponseEntity<Tache> ajouterTache(@RequestBody Tache tache) {
        log.info("Ajout d'une nouvelle tâche : {}", tache.getNom());
        Tache nouvelleTache = tacheService.ajouterTache(tache);
        return new ResponseEntity<>(nouvelleTache, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<TacheDto>> listerTaches() {
        log.info("Récupération de toutes les tâches");
        List<TacheDto> taches = tacheService.listerTaches();
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
    @Transactional
    public TacheDto updateTache(@PathVariable Long id, @RequestBody TacheDto tacheDTO) {
        System.out.println("Reçu requête PUT pour tâche ID: " + id);
        System.out.println("TacheDTO reçu: " + tacheDTO);
        System.out.println("Articles dans DTO: " + tacheDTO.getArticles());

        // Récupérer la tâche existante
        Tache tache = tacheRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tâche non trouvée : " + id));

        // Mettre à jour les champs de la tâche
        tache.setNom(tacheDTO.getNom());
        tache.setDescription(tacheDTO.getDescription());
        tache.setDureeEstimee(tacheDTO.getDureeEstimee());
        tache.setDateDebut(tacheDTO.getDateDebut());
        tache.setDateFin(tacheDTO.getDateFin());
        tache.setStatus(tacheDTO.getStatus());
        tache.setPourcentageExecution(tacheDTO.getPourcentageExecution());

        // Gérer les TacheArticle
        Set<Long> newArticleIds = tacheDTO.getArticles().stream()
                .map(TacheArticleDto::getArticleId)
                .collect(Collectors.toSet());

        // Identifier les TacheArticle existants à supprimer
        Set<Long> articlesToRemove = tache.getArticles().stream()
                .map(ta -> ta.getArticle().getId())
                .filter(articleId -> !newArticleIds.contains(articleId))
                .collect(Collectors.toSet());

        // Supprimer les TacheArticle qui ne sont plus dans le DTO
        tache.getArticles().removeIf(ta -> articlesToRemove.contains(ta.getArticle().getId()));

        // Créer ou mettre à jour les TacheArticle
        Set<Long> affectedArticleIds = new HashSet<>(articlesToRemove);
        List<TacheArticle> updatedArticles = tacheDTO.getArticles().stream().map(dto -> {
            Article article = articleRepository.findById(dto.getArticleId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Article non trouvé : " + dto.getArticleId()));
            affectedArticleIds.add(article.getId());

            // Vérifier si un TacheArticle existe déjà pour cet article
            TacheArticle existingTacheArticle = tache.getArticles().stream()
                    .filter(ta -> ta.getArticle().getId().equals(dto.getArticleId()))
                    .findFirst()
                    .orElse(null);

            if (existingTacheArticle != null) {
                // Mettre à jour la quantité utilisée
                existingTacheArticle.setQuantiteUtilisee(dto.getQuantiteUtilisee());
                return existingTacheArticle;
            } else {
                // Créer un nouveau TacheArticle
                TacheArticle tacheArticle = new TacheArticle();
                tacheArticle.setTache(tache);
                tacheArticle.setArticle(article);
                tacheArticle.setQuantiteUtilisee(dto.getQuantiteUtilisee());
                tache.getArticles().add(tacheArticle);
                return tacheArticle;
            }
        }).collect(Collectors.toList());

        System.out.println("Articles avant sauvegarde: " + tache.getArticles());

        // Sauvegarder la tâche
        Tache updatedTache = tacheRepository.save(tache);
        System.out.println("Tâche sauvegardée: " + updatedTache);
        System.out.println("Articles après sauvegarde: " + updatedTache.getArticles());

        // Mettre à jour la quantité des articles affectés
        affectedArticleIds.forEach(tacheArticleService::updateArticleQuantity);

        // Créer le DTO de retour
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

        System.out.println("TacheDto renvoyé: " + result);
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