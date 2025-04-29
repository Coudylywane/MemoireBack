package com.example.construction.services;

import com.example.construction.models.Tache;
import com.example.construction.models.enumeration.TaskStatus;
import com.example.construction.repositories.TacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TacheService {

    private final TacheRepository tacheRepository;

    public Tache ajouterTache(Tache tache) {
        return tacheRepository.save(tache);
    }

    public List<Tache> listerTaches() {
        return tacheRepository.findAll();
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