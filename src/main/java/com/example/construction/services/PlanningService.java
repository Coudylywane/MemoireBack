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

    public void genererPlanning(Projet projet) {
        // Exemple de tâches prédéfinies
        List<Tache> taches = new ArrayList<>();

        Tache tache1 = new Tache();
        tache1.setNom("Analyse des besoins");
        tache1.setDescription("Étudier les besoins du client");
        tache1.setDureeEstimee(5);
        tache1.setDateDebut(LocalDate.now());
        tache1.setDateFin(LocalDate.now().plusDays(5));
        tache1.setStatut("À faire");
        //tache1.setProjet(projet);

        Tache tache2 = new Tache();
        tache2.setNom("Conception");
        tache2.setDescription("Concevoir l'architecture du projet");
        tache2.setDureeEstimee(10);
        tache2.setDateDebut(LocalDate.now().plusDays(6));
        tache2.setDateFin(LocalDate.now().plusDays(15));
        tache2.setStatut("À faire");
        //tache2.setProjet(projet);

        taches.add(tache1);
        taches.add(tache2);

        // Sauvegarder les tâches
        tacheRepository.saveAll(taches);
    }

    public void regenererPlanning(Projet projet) {
        // Récupérer toutes les tâches du projet
        List<Tache> taches = tacheRepository.findByProjetId(projet.getId());

        // Trier les tâches par date de début (si nécessaire)
        taches.sort(Comparator.comparing(Tache::getDateDebut));

        // Recalculer les dates de début et de fin
        LocalDate dateDebutProjet = LocalDate.now(); // Ou utiliser la date de début du projet
        for (Tache tache : taches) {
            tache.setDateDebut(dateDebutProjet);
            tache.setDateFin(dateDebutProjet.plusDays(tache.getDureeEstimee()));

            // Mettre à jour la date de début pour la prochaine tâche
            dateDebutProjet = tache.getDateFin().plusDays(1); // Ajouter un jour de délai entre les tâches
        }

        // Sauvegarder les tâches mises à jour
        tacheRepository.saveAll(taches);
    }

}
