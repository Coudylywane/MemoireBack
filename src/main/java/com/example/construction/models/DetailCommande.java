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
public class DetailCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;
    @Column(nullable = false)
    private Integer nombre ;
    private Double prixTotal ;

    @ManyToOne
    @JoinColumn(name = "fournisseur", referencedColumnName = "id")
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "article", referencedColumnName = "id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "commande", referencedColumnName = "id")
    private Commande commande;
}
