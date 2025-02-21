package com.example.construction.services;

import com.example.construction.models.Projet;
import com.example.construction.models.Validation;
import com.example.construction.repositories.ProjectRepository;
import com.example.construction.repositories.ValidationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ValidationService {

    private final ValidationRepository validationRepository;
    private final ProjectRepository projectRepository;

    public ValidationService(ValidationRepository validationRepository, ProjectRepository projectRepository) {
        this.validationRepository = validationRepository;
        this.projectRepository = projectRepository;
    }

    // ✅ Récupérer toutes les validations d'un projet
    public List<Validation> getValidationsByProject(Long projetId) {
        return validationRepository.findByProjetId(projetId);
    }

    //  Ajouter une validation à un projet
    public Validation addValidationToProject(Long projetId, String status) {
        Projet projet = (Projet) projectRepository.findById(projetId)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé : " + projetId));

        Validation validation = new Validation();
        validation.setStatus(status);
        validation.setProjet(projet);

        return validationRepository.save(validation);
    }

    // ✅ Mettre à jour une validation
    @Transactional
    public Validation updateValidation(Long validationId, String newStatus) {
        Validation validation = validationRepository.findById(validationId)
                .orElseThrow(() -> new RuntimeException("Validation non trouvée"));

        validation.setStatus(newStatus);
        return validationRepository.save(validation);
    }

    //  Supprimer une validation
    @Transactional
    public void deleteValidation(Long validationId) {
        if (!validationRepository.existsById(validationId)) {
            throw new RuntimeException("Validation non trouvée");
        }
        validationRepository.deleteById(validationId);
    }
}

