package com.example.construction.models;

import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReglementFournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String montant;
    private String commentaire;
    private String motif;
    private boolean typeReglement;

    private int status = 0;

    // Méthode pour la suppression logique
    public void softDelete() {
        this.status = 1;
    }
//    @ManyToOne
//    @JoinColumn(name = "commande", referencedColumnName = "id")
//    private Commande commande;
}
