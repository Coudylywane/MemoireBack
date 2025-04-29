package com.example.construction.models;

import javax.persistence.*;

import com.example.construction.models.enumeration.StatusCommande;

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
    @Enumerated(EnumType.STRING)
    private StatusCommande status = StatusCommande.EN_COURS;

    // Ajoutez les annotations JPA appropriées pour mapper cette liste
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailCommande> detailsCommande;
    



}
