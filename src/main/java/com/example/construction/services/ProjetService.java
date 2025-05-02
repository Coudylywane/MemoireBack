package com.example.construction.services;

import com.example.construction.mapper.MapStructMapper;
import com.example.construction.models.Projet;
import com.example.construction.models.Tache;
import com.example.construction.models.Utilisateur;
import com.example.construction.models.Validation;
import com.example.construction.repositories.*;
import com.example.construction.response.ProjetResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.construction.request.ProjetRequestDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjetService {
    private final ProjetRepository projetRepository;
    private final MapStructMapper mapStructMapper;
    private final UtilisateurRepository utilisateurRepository;


    public Page<Projet> getAllProject(Pageable pageable) {
        return projetRepository.findAll(pageable); // Retourner la liste des projets
    }
    public List<Projet> getAllProjects() {
        return projetRepository.findAll();
    }

//    public Page<Projet> getAllProjectByClient(Pageable pageable){
//        return projetRepository.findByClientId(pageable);
//    }

    public Projet createProject(ProjetRequestDto projetRequestDto) {
        if (projetRequestDto == null || projetRequestDto.getName() == null || projetRequestDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Le nom du projet est obligatoire.");
        }
        if (projetRequestDto.getClientId() == null) {
            throw new IllegalArgumentException("L'identifiant du client est obligatoire.");
        }
        Utilisateur client = utilisateurRepository.findById(projetRequestDto.getClientId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID: " + projetRequestDto.getClientId()));

        Projet projet = mapStructMapper.ProjetDtoToProjet(projetRequestDto);
        projet.setCreatedAt(LocalDateTime.now());
        projet.setClient(client);
        if (projet.getStatus() == null) {
            projet.setStatus("EN_ATTENTE");
        }
        Projet savedProjet = projetRepository.save(projet);
        return savedProjet;
    }


    public Projet createProjecte(ProjetRequestDto projetRequestDto) {
        if (projetRequestDto == null || projetRequestDto.getName() == null || projetRequestDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Le nom du projet est obligatoire.");
        }
        Projet projet = mapStructMapper.ProjetDtoToProjet(projetRequestDto);
        return projetRepository.save(projet);
    }

    public Projet addSitesToProject(ProjetRequestDto projectRequestDto) {
        return null;
    }

    public ProjetResponseDto mapProjectToResponseDto(Projet updatedProject) {
        return null;
    }

   public Projet getById(String projectId) {
        return null;
    }

    public Projet getProjetById(Long id) {
        return projetRepository.findById(id).orElse(null);
    }

//    public boolean toutesTachesTerminees(Long projetId) {
//        List<Tache> taches = tacheRepository.findByProjetId(projetId);
//        return taches.stream().allMatch(tache -> tache.getStatut().equals("Terminée"));
//    }

    public Projet addValidationToProjet(Long id, String status) {
        // Récupérer le projet par son ID
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Projet non trouvé : " + id));

        // Mettre à jour le statut du projet
        projet.setStatus(status); // Assurez-vous que la classe Projet a un champ `status` et un setter correspondant

        // Sauvegarder les modifications dans la base de données
        return projetRepository.save(projet);
    }





}
