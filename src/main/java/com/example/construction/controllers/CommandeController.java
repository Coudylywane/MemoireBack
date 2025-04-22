package com.example.construction.controllers;

import com.example.construction.models.Commande;
import com.example.construction.models.DetailCommande;
import com.example.construction.repositories.CommandeRepository;
import com.example.construction.services.CommandeService;
import com.example.construction.services.ParametrageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommandeController {
    private final CommandeService commandeService;

    @PostMapping("/commande")
    public ResponseEntity<?> ajouterCommande(@RequestBody Commande commande) {
        try {
            System.out.println("Commande reçue : " + new ObjectMapper().writeValueAsString(commande));
            Commande nouvelleCommande = commandeService.addCommande(commande);
            return ResponseEntity.ok(nouvelleCommande);
        } catch (Exception e) {
            e.printStackTrace(); // pour afficher l'erreur dans les logs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    

  
   
    
    
    

}
