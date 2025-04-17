package com.example.construction.services;

import com.example.construction.models.Devis;
import com.example.construction.models.Planning;
import com.example.construction.models.Projet;
import com.example.construction.models.Tache;
import com.example.construction.repositories.DevisRepository;
import com.example.construction.repositories.PlanningRepository;
import com.example.construction.repositories.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanningService {
    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private DevisRepository devisRepository;

    @Autowired
    private ProjetRepository projetRepository;

    public Planning createPlanning(Long devisId, List<Tache> taches) {
        Devis devis = devisRepository.findById(devisId)
                .orElseThrow(() -> new RuntimeException("Devis not found"));
        Projet projet = devis.getProjet();
        if (projet == null) {
            throw new RuntimeException("Projet not found for Devis");
        }

        Planning planning = new Planning();
        planning.setDevis(devis);
        planning.setProjet(projet);

        // Filter selected tasks and associate with planning
        taches.stream()
                .filter(tache -> tache.getDateDebut() != null && tache.getDateFin() != null)
                .forEach(tache -> {
                    tache.setPlanning(planning);
                    planning.getTaches().add(tache);
                });

        return planningRepository.save(planning);
    }

    public Planning getPlanningByDevisId(Long devisId) {
        return planningRepository.findByDevisId(devisId);
    }
}