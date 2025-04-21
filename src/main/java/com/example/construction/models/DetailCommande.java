package com.example.construction.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailCommande {
   
         @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = true)
        private int nombre;

        private double prixTotal;
    
        @ManyToOne
        @JoinColumn(name = "article_id")
        private Article article;
    
        @ManyToOne
        @JoinColumn(name = "fournisseur_id")
        private Fournisseur fournisseur;
    
    
        @ManyToOne
        @JoinColumn(name = "commande_id")
        @JsonBackReference
        private Commande commande;
    
    
}
