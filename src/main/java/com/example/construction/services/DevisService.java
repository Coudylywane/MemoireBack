package com.example.construction.services;

import com.example.construction.dto.DevisDto;
import com.example.construction.dto.DtoProjet;
import com.example.construction.models.*;
import com.example.construction.models.enumeration.DevisStatus;
import com.example.construction.repositories.*;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DevisService {
    private final DevisRepository devisRepository;
    private final ArticleRepository articleRepository;
    private final ProjetRepository projectRepository;
    private final ModelMapper modelMapper;
    private final PlanningRepository planningRepository ;
    // Injection correcte de ModelMapper

    @Transactional
    public Devis creerDevis(Devis devis, Long projetId) {
        // Récupérer le projet associé
        Projet projet = projectRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé"));

        // Associer le devis au projet
        devis.setProjet(projet);
        devis.setDateCreation(LocalDate.now());

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

    public List<DevisDto> obtenirTousLesDevis() {
        return devisRepository.findAll()
                .stream()
                .map(devis -> modelMapper.map(devis, DevisDto.class))
                .collect(Collectors.toList());
    }

    public List<DevisDto> getAllDevis() {
        List<Devis> devisList = devisRepository.findAll();
        return devisList.stream().map(devis -> {
            DevisDto dto = new DevisDto();
            dto.setId(devis.getId());
            dto.setMontant(BigDecimal.valueOf(devis.getTotal()));
            dto.setDateCreation(devis.getDateCreation());
            dto.setStatut(devis.getStatut());
            // Check if planning exists
            Planning planning = planningRepository.findByDevisId(devis.getId());
            dto.setPlanningId(planning != null ? planning.getId() : null);
            // Mapper Projet vers ProjetDto
            DtoProjet projetDto = new DtoProjet();
            Projet projet = devis.getProjet();
            projetDto.setId(projet.getId());
            projetDto.setClientId(projet.getClient() != null ? projet.getClient().getId() : null);
            dto.setProjet(projetDto);
            return dto;
        }).collect(Collectors.toList());
    }

//    public List<Devis> obtenirTousLesDevis() {
//        return devisRepository.findAll();
//    }

    public Optional<Devis> obtenirDevisParId(Long id) {
        return devisRepository.findById(id);
    }

    public List<Devis> obtenirDevisParProjetId(Long projetId) {
        return devisRepository.findByProjetId(projetId);
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
        boolean projetADejaUnDevisValide = devisRepository.existsByProjetAndStatut(devis.getProjet(), DevisStatus.VALIDER);
        if (projetADejaUnDevisValide) {
            throw new RuntimeException("Le projet a déjà un devis validé.");
        }

        // Valider le devis
        devis.setStatut(DevisStatus.VALIDER);
        return devisRepository.save(devis);
    }

    public List<DevisDto> getDevisByClientId(Long clientId) {
        List<Devis> devisList = devisRepository.findByClientId(clientId);
        return devisList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private DevisDto convertToDto(Devis devis) {
        DevisDto dto = new DevisDto();
        dto.setId(devis.getId());
        dto.setMontant(BigDecimal.valueOf(devis.getTotal()));
        dto.setDateCreation(devis.getDateCreation());
        dto.setStatut(devis.getStatut());
        Planning planning = planningRepository.findByDevisId(devis.getId());
        dto.setPlanningId(planning != null ? planning.getId() : null);
        DtoProjet projetDto = new DtoProjet();
        Projet projet = devis.getProjet();
        projetDto.setId(projet.getId());
        projetDto.setClientId(projet.getClient() != null ? projet.getClient().getId() : null);
        dto.setProjet(projetDto);
        return dto;
    }


}
