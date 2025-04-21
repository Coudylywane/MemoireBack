package com.example.construction.controllers;

import com.example.construction.models.Devis;
import com.example.construction.models.Planning;
import com.example.construction.models.Projet;
import com.example.construction.models.Tache;
import com.example.construction.repositories.DevisRepository;
import com.example.construction.services.PlanningService;
import com.example.construction.services.TacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {
    @Autowired
    private PlanningService planningService;

    @Autowired
    private TacheService tacheService;

//    @GetMapping("/taches/default")
//    public ResponseEntity<List<Tache>> getDefaultTaches() {
//        return ResponseEntity.ok(tacheService.getDefaultTaches());
//    }

    @PostMapping("/generate/{devisId}")
    public ResponseEntity<Planning> generatePlanning(@PathVariable Long devisId, @RequestBody List<Tache> taches) {
        Planning planning = planningService.createPlanning(devisId, taches);
        return ResponseEntity.ok(planning);
    }

    @GetMapping("/devis/{devisId}")
    public ResponseEntity<Planning> getPlanningByDevisId(@PathVariable Long devisId) {
        Planning planning = planningService.getPlanning(devisId);
        if (planning == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(planning);
    }


}