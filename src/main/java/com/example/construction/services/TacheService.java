package com.example.construction.services;

import com.example.construction.models.Tache;
import com.example.construction.models.enumeration.TaskStatus;
import com.example.construction.repositories.TacheRepository;
import com.example.construction.request.TacheArticleDto;
import com.example.construction.request.TacheDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TacheService {

    private final TacheRepository tacheRepository;

    public Tache ajouterTache(Tache tache) {
        return tacheRepository.save(tache);
    }

    public List<TacheDto> listerTaches() {
        List<Tache> taches = tacheRepository.findAll();
        return taches.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TacheDto convertToDto(Tache tache) {
        TacheDto dto = new TacheDto();
        dto.setId(tache.getId());
        dto.setNom(tache.getNom());
        dto.setDateDebut(tache.getDateDebut());
        dto.setDateFin(tache.getDateFin());
        dto.setPourcentageExecution(tache.getPourcentageExecution());
        dto.setStatus(tache.getStatus());
        return dto;
    }

    public List<Tache> listerTachesParPlanning(Long planningId) {
        return tacheRepository.findByPlanningId(planningId);
    }

    public List<Tache> listerTachesParStatut(Long planningId, TaskStatus status) {
        return tacheRepository.findByPlanningIdAndStatus(planningId, status);
    }

    public Tache modifierTache(Long id, Tache tacheDetails) {
        Tache tache = tacheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID : " + id));
        tache.setNom(tacheDetails.getNom());
        tache.setDateDebut(tacheDetails.getDateDebut());
        tache.setDateFin(tacheDetails.getDateFin());
        tache.setPourcentageExecution(tacheDetails.getPourcentageExecution());
        tache.setStatus(tacheDetails.getStatus());
        tache.setPlanning(tacheDetails.getPlanning());
        return tacheRepository.save(tache);
    }

    public void supprimerTache(Long id) {
        Tache tache = tacheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID : " + id));
        tacheRepository.delete(tache);
    }
}