package com.example.construction.models;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private String totalVersement;
    private String solde;

    private int status = 0;

    // Méthode pour la suppression logique
    public void softDelete() {
        this.status = 1;
    }

    @ManyToOne
    @JoinColumn(name = "categorie")
    private CategorieFournisseur categorieFournisseur;


    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id")
    private TypeFournisseur typeFournisseur;


    @ManyToOne
    @JoinColumn(name = "contact", referencedColumnName = "id")
    private ContactFournisseur contactFournisseur;
}
