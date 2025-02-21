package com.example.construction.services;

import com.example.construction.mapper.MapStructMapper;
import com.example.construction.models.Projet;
import com.example.construction.models.Tache;
import com.example.construction.repositories.ArticleRepository;
import com.example.construction.repositories.ProjectRepository;
import com.example.construction.repositories.DevisRepository;
import com.example.construction.repositories.TacheRepository;
import com.example.construction.response.ProjectResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.construction.request.ProjectRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final MapStructMapper mapStructMapper;
    private final ArticleRepository articleRepository ;
    private final DevisRepository quoteRepository ;
    private final TacheRepository tacheRepository ;


    public Page<Projet> getAllProject(Pageable pageable) {
        return projectRepository.findAll(pageable); // Retourner la liste des projets
    }

    public Optional<Projet> getProjectById(String projectId) {
        return null;
    }

    public Projet createProject(ProjectRequestDto projectRequestDto) {
        if (projectRequestDto == null || projectRequestDto.getName() == null || projectRequestDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Le nom du projet est obligatoire.");
        }

        // Convertir DTO en entité
        Projet project = mapStructMapper.ProjectDtoToProject(projectRequestDto);

        // Enregistrer en base de données
        return projectRepository.save(project);
    }

    public Projet addSitesToProject(ProjectRequestDto projectRequestDto) {
        return null;
    }

    public ProjectResponseDto mapProjectToResponseDto(Projet updatedProject) {
        return null;
    }

   public Projet getById(String projectId) {
        return null;
    }

    public boolean toutesTachesTerminees(Long projetId) {
        List<Tache> taches = tacheRepository.findByProjetId(projetId);
        return taches.stream().allMatch(tache -> tache.getStatut().equals("Terminée"));
    }





}
