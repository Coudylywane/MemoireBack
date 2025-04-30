package com.example.construction.controllers;

import com.example.construction.exceptions.BadRequestException;
import com.example.construction.models.Commande;
import com.example.construction.models.DetailCommande;
import com.example.construction.models.Fournisseur;
import com.example.construction.repositories.CommandeRepository;
import com.example.construction.services.CommandeService;
import com.example.construction.services.ParametrageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log
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
    
    @GetMapping("/commandes")
    public ResponseEntity<?> getCommandePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "2") int size
    ){
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Commande> commandePage= commandeService.getCommandePage(paging);
            Map<String, Object> response = new HashMap<>();
            response.put("commande", commandePage.getContent());
            response.put("currentPage", commandePage.getNumber());
            response.put("totalItems", commandePage.getTotalElements());
            response.put("totalPages", commandePage.getTotalPages());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw e;
        }
    }

    @PutMapping("/commande/{id}")
    public ResponseEntity<?> updateCommande(@PathVariable Long id, @RequestBody Commande commande) {
        try {
            commande.setId(id);
            commande.setStatus(null);
            Commande updated = commandeService.updateCommande(commande);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Commande not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    
    @GetMapping("/commande/{id}")
    public ResponseEntity<?> getCommandeById(@PathVariable Long id) {
        try {
            if (id == null) throw new BadRequestException("id required");
            Commande commande = commandeService.getCommandeById(id);
            if (commande == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(commande);
            }
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /** RECUPERER LES COMMANDES DU DASH **/
    @GetMapping("/commande-list")
    public List<Commande> getCommandes() {
        return commandeService.getAllCommandes(); 
    }

    /*** GENERE BON COMMANDE ****/
    //@PostMapping("/{id}/generer-bon")
    //public ResponseEntity<String> generateBonDeCommande(@PathVariable Long id) {
        // Logique pour générer le bon de commande
        //boolean isGenerated = commandeService.genererBonDeCommande(id);
        
        //if (isGenerated) {
           // return ResponseEntity.ok("Bon de commande généré avec succès.");
        //} else {
            //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la génération du bon de commande.");
       // }
   // }


    

  
   
    
    
    

}
