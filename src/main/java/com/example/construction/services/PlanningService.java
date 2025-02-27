package com.example.construction.services;

import com.example.construction.models.Projet;
import com.example.construction.models.Tache;
import com.example.construction.repositories.TacheRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class PlanningService {
    private final TacheRepository tacheRepository;

//    @Transactional
//    public void genererPlanning(List<Tache> tachesDto) {
//        for (Tache tacheDto : tachesDto) {
//            Tache tache = tacheRepository.findById(tacheDto.getId())
//                    .orElseThrow(() -> new RuntimeException("Tâche introuvable avec ID : " + tacheDto.getId()));
//
//            // Mettre à jour uniquement les dates de début et de fin
//            tache.setDateDebut(LocalDate.parse(tacheDto.getDateDebut()));
//            tache.setDateFin(LocalDate.parse(tacheDto.getDateFin()));
//            tache.setStatut("PLANIFIÉ"); // Changer le statut à "PLANIFIÉ" (ou autre selon le besoin)
//
//            tacheRepository.save(tache);
//        }
//    }

//    public void regenererPlanning(Projet projet) {
//        // Récupérer toutes les tâches du projet
//        List<Tache> taches = tacheRepository.findByProjetId(projet.getId());
//
//        // Trier les tâches par date de début (si nécessaire)
//        taches.sort(Comparator.comparing(Tache::getDateDebut));
//
//        // Recalculer les dates de début et de fin
//        LocalDate dateDebutProjet = LocalDate.now(); // Ou utiliser la date de début du projet
//        for (Tache tache : taches) {
//            tache.setDateDebut(dateDebutProjet);
//            tache.setDateFin(dateDebutProjet.plusDays(tache.getDureeEstimee()));
//
//            // Mettre à jour la date de début pour la prochaine tâche
//            dateDebutProjet = tache.getDateFin().plusDays(1); // Ajouter un jour de délai entre les tâches
//        }
//
//        // Sauvegarder les tâches mises à jour
//        tacheRepository.saveAll(taches);
//    }

}
