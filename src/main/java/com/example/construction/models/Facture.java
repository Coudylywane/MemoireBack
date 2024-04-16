package com.example.construction.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;
    private String numero ;
    private String prixTotal ;
    private String date;

    @ManyToOne
    @JoinColumn(name = "commande", referencedColumnName = "id")
    private Commande commande ;

    @ManyToOne
    @JoinColumn(name = "fournisseur", referencedColumnName = "id")
    private Fournisseur fournisseur ;


}
