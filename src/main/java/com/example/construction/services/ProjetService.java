package com.example.construction.services;

import com.example.construction.mapper.MapStructMapper;
import com.example.construction.models.Projet;
import com.example.construction.models.Tache;
import com.example.construction.models.Validation;
import com.example.construction.repositories.ArticleRepository;
import com.example.construction.repositories.ProjetRepository;
import com.example.construction.repositories.DevisRepository;
import com.example.construction.repositories.TacheRepository;
import com.example.construction.response.ProjetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.construction.request.ProjetRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjetService {
    private final ProjetRepository projetRepository;
    private final MapStructMapper mapStructMapper;
    private final ArticleRepository articleRepository ;
    private final DevisRepository quoteRepository ;
    private final TacheRepository tacheRepository ;


    public Page<Projet> getAllProject(Pageable pageable) {
        return projetRepository.findAll(pageable); // Retourner la liste des projets
    }
    public List<Projet> getAllProjects() {
        return projetRepository.findAll();
    }




    public Projet createProject(ProjetRequestDto projetRequestDto) {
        if (projetRequestDto == null || projetRequestDto.getName() == null || projetRequestDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Le nom du projet est obligatoire.");
        }
        // Convertir DTO en entité
        Projet projet = mapStructMapper.ProjetDtoToProjet(projetRequestDto);
        // Enregistrer en base de données
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
