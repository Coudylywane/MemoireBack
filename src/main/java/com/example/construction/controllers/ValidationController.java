package com.example.construction.controllers;

import com.example.construction.models.Validation;
import com.example.construction.services.ValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/validations")
public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    //  1. Récupérer les validations d'un projet
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Validation>> getValidationsByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(validationService.getValidationsByProject(projectId));
    }

    //  2. Ajouter une validation à un projet
    @PostMapping("/project/{projectId}")
    public ResponseEntity<Validation> addValidationToProject(
            @PathVariable Long projectId,
            @RequestParam String status) {
        return ResponseEntity.ok(validationService.addValidationToProject(projectId, status));
    }

    //  3. Mettre à jour une validation
    @PutMapping("/{validationId}")
    public ResponseEntity<Validation> updateValidation(
            @PathVariable Long validationId,
            @RequestParam String newStatus) {
        return ResponseEntity.ok(validationService.updateValidation(validationId, newStatus));
    }

    //  4. Supprimer une validation
    @DeleteMapping("/{validationId}")
    public ResponseEntity<Void> deleteValidation(@PathVariable Long validationId) {
        validationService.deleteValidation(validationId);
        return ResponseEntity.noContent().build();
    }
}

