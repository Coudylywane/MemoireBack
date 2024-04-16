package com.example.construction.controllers;

import com.example.construction.models.Commande;
import com.example.construction.models.DetailCommande;
import com.example.construction.services.CommandeService;
import com.example.construction.services.ParametrageService;
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
    public ResponseEntity<?> ajouterCommande(@RequestBody List<DetailCommande> detailsCommande) {
        try {
            Commande nouvelleCommande = commandeService.addCommande(detailsCommande);
            return ResponseEntity.ok(nouvelleCommande);
        } catch (Exception e) {
            // En cas d'erreur, renvoyer un code d'état 500 avec le message de l'exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
