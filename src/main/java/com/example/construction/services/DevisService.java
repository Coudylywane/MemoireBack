package com.example.construction.services;

import com.example.construction.jwtutils.ValidationUtils;
import com.example.construction.models.*;
import com.example.construction.models.enumeration.DevisStatus;
import com.example.construction.repositories.*;

import javax.persistence.*;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DevisService {
    private final DevisRepository devisRepository;
    private final ArticleRepository articleRepository;
    private final ProjectRepository projectRepository;

    //@Transactional
//    public Devis creerDevis(Devis devis) {
//        for (LigneDevis ligne : devis.getLignesDevis()) {
//            Article article = ligne.getArticle();
//
//            // Vérifier si l'article existe déjà dans la base de données
//            Optional<Article> existingArticle = articleRepository.findByDesignation(article.getDesignation());
//
//            if (existingArticle.isPresent()) {
//                // Utiliser l'article existant
//                ligne.setArticle(existingArticle.get());
//            } else {
//                // Lever une exception si l'article n'existe pas
//                throw new RuntimeException("L'article '" + article.getDesignation() + "' n'existe pas dans la base de données.");
//            }
//        }
//
//        // Sauvegarder le devis
//        return devisRepository.save(devis);
//    }

    @Transactional
    public Devis creerDevis(Devis devis, Long projetId) {
        // Récupérer le projet associé
        Project project = projectRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        // Associer le devis au projet
        devis.setProject(project);

        // Vérifier et associer les articles existants
        for (LigneDevis ligne : devis.getLignesDevis()) {
            Article article = ligne.getArticle();
            Optional<Article> existingArticle = articleRepository.findByDesignation(article.getDesignation());

            if (existingArticle.isPresent()) {
                ligne.setArticle(existingArticle.get());
            } else {
                throw new RuntimeException("L'article '" + article.getDesignation() + "' n'existe pas dans la base de données.");
            }
        }

        // Sauvegarder le devis
        return devisRepository.save(devis);
    }
    public List<Devis> obtenirTousLesDevis() {
        return devisRepository.findAll();
    }

    public Optional<Devis> obtenirDevisParId(Long id) {
        return devisRepository.findById(id);
    }

    @Transactional
    public Devis mettreAJourStatut(Long devisId, DevisStatus nouveauStatut) {
        // Récupérer le devis par son ID
        Devis devis = devisRepository.findById(devisId)
                .orElseThrow(() -> new RuntimeException("Devis non trouvé"));

        // Vérifier les règles métier (optionnel)
        if (devis.getStatut() == DevisStatus.VALIDER && nouveauStatut == DevisStatus.REFUSER) {
            throw new RuntimeException("Un devis validé ne peut pas être refusé.");
        }

        // Mettre à jour le statut
        devis.setStatut(nouveauStatut);

        // Sauvegarder le devis mis à jour
        return devisRepository.save(devis);
    }

    @Transactional
    public Devis validerDevis(Long devisId) {
        Devis devis = devisRepository.findById(devisId)
                .orElseThrow(() -> new RuntimeException("Devis non trouvé"));

        // Vérifier si le projet a déjà un devis validé
        boolean projetADejaUnDevisValide = devisRepository.existsByProjetAndStatut(devis.getProject(), DevisStatus.VALIDER);
        if (projetADejaUnDevisValide) {
            throw new RuntimeException("Le projet a déjà un devis validé.");
        }

        // Valider le devis
        devis.setStatut(DevisStatus.VALIDER);
        return devisRepository.save(devis);
    }
}
