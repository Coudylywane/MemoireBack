package com.example.construction.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private int dureeEstimee; // Durée estimée en jours
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut; // Par exemple : "À faire", "En cours", "Terminée"
    private int pourcentageExecution; // Pourcentage d'exécution (0 à 100)

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;
}
