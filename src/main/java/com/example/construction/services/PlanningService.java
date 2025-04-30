package com.example.construction.services;

import com.example.construction.models.Devis;
import com.example.construction.models.Planning;
import com.example.construction.models.Projet;
import com.example.construction.models.Tache;
import com.example.construction.models.enumeration.TaskStatus;
import com.example.construction.repositories.DevisRepository;
import com.example.construction.repositories.PlanningRepository;
import com.example.construction.request.TacheDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanningService {
    @Autowired
    private PlanningRepository planningRepository;

    @Autowired
    private DevisRepository devisRepository;


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

//    public List<Tache> getTachesByDevisId(Long devisId , TaskStatus status) {
//        return devisRepository.findTachesByDevisId(devisId , status);
//    }

    public List<TacheDto> getTachesByDevisId(Long devisId, TaskStatus status) {
        System.out.println("Appel de findTachesByDevisId avec devisId=" + devisId + ", status=" + status);
        List<Tache> taches = devisRepository.findTachesByDevisId(devisId, status);
        return taches.stream()
                .map(tache -> {
                    TacheDto dto = new TacheDto();
                    dto.setId(tache.getId());
                    dto.setNom(tache.getNom());
                    dto.setDescription(tache.getDescription());
                    dto.setDureeEstimee(tache.getDureeEstimee());
                    dto.setDateDebut(tache.getDateDebut());
                    dto.setDateFin(tache.getDateFin());
                    dto.setStatus(tache.getStatus());
                    dto.setPourcentageExecution(tache.getPourcentageExecution());
                    return dto;
                })
                .collect(Collectors.toList());
    }


}