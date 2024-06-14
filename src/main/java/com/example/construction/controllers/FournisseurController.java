package com.example.construction.controllers;

import com.example.construction.exceptions.BadRequestException;
import com.example.construction.models.*;
import com.example.construction.repositories.CategorieFournisseurRepository;
import com.example.construction.repositories.FournisseurRepository;
import com.example.construction.repositories.TypeFournisseurRepository;
import com.example.construction.services.FournisseurService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log
@RequestMapping("/api")
public class FournisseurController {
    private final FournisseurService fournisseurService;
    private final TypeFournisseurRepository typeFournisseurRepository;
    private final CategorieFournisseurRepository categorieFournisseurRepository;

    @PostMapping("/fournisseur")
    public ResponseEntity<?> addFournisseur(@RequestBody Fournisseur newFournisseur) {
        try {
            newFournisseur.setStatus(0);
            ResponseEntity<Object> savedFournisseur = fournisseurService.addFournisseur(newFournisseur);
            return ResponseEntity.ok(savedFournisseur);
        } catch (Exception e) {
            String errorMessage = "Erreur lors de l'ajout de l'article : " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }



    @GetMapping("/fournisseurs")
    public ResponseEntity<?> getFournisseurPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "2") int size
    ){
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Fournisseur> fournisseurPage= fournisseurService.getFournisseurPage(paging);
            Map<String, Object> response = new HashMap<>();
            response.put("fournisseur", fournisseurPage.getContent());
            response.put("currentPage", fournisseurPage.getNumber());
            response.put("totalItems", fournisseurPage.getTotalElements());
            response.put("totalPages", fournisseurPage.getTotalPages());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw e;
        }
    }

    @PutMapping("/fournisseur/{id}")
    public ResponseEntity<?> updateFournisseur(@PathVariable Long id, @RequestBody Fournisseur fournisseur) {
        try {
            fournisseur.setId(id);
            fournisseur.setStatus(0);
            Fournisseur updated = fournisseurService.updateFournisseur(fournisseur);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Famille not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/fournisseur/{id}")
    public ResponseEntity<?> softDeleteFournisseur(@PathVariable Long id) {
        try {
            fournisseurService. softDeleteFournisseur(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Famille not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/fournisseur/{id}")
    public ResponseEntity<?> getFournisseurById(@PathVariable Long id) {
        try {
            if (id == null) throw new BadRequestException("id required");
            Fournisseur fournisseur = fournisseurService.getFournisseurById(id);
            if (fournisseur == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(fournisseur);
            }
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


//    @GetMapping("/fournisseur")
//    public ResponseEntity<?> getAllFournisseur() {
//        List<Fournisseur> fournisseur = fournisseurService.getAllFournisseurWithTypes();
//        return ResponseEntity.ok(fournisseur);
//    }
//
//    @GetMapping("/fournisseurAndType")
//    public ResponseEntity<?> getAllFournisseurWithTypes() {
//        List<Fournisseur> fournisseur = fournisseurService.getAllFournisseurWithTypes();
//        return ResponseEntity.ok(fournisseur);
//    }


}
