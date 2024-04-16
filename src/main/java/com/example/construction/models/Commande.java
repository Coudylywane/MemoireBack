package com.example.construction.models;

import javax.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Commande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String date;
    private Double prixTotal;

    // Ajoutez les annotations JPA appropriées pour mapper cette liste
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<DetailCommande> detailsCommande;




}
