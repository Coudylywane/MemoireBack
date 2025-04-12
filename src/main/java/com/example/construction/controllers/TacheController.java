package com.example.construction.controllers;

import com.example.construction.models.Tache;
import com.example.construction.services.TacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taches")
@Slf4j
@RequiredArgsConstructor
public class TacheController {
    private final TacheService tacheService;

    @PostMapping("/add")
    public ResponseEntity<Tache> ajouterTache(@RequestBody Tache tache) {
        Tache nouvelleTache = tacheService.ajouterTache(tache);
        return new ResponseEntity<>(nouvelleTache, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Tache>> listerTaches() {
        List<Tache> taches = tacheService.listerTaches();
        return new ResponseEntity<>(taches, HttpStatus.OK);
    }

//    @GetMapping("/projet/{projetId}")
//    public ResponseEntity<List<Tache>> listerTachesParProjet(@PathVariable Long projetId) {
//        List<Tache> taches = tacheService.listerTachesParProjet(projetId);
//        return new ResponseEntity<>(taches, HttpStatus.OK);
//    }
}
