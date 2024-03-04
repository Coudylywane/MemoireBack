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
public class Article{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;
    private String  code;
    @Column(nullable = false , unique = true)
    private String  designation;
    @Column(nullable = false , unique = true)
    private String  description;
    @Column(nullable = false , unique = true)
    private Integer poids;
    @Column(nullable = false , unique = true)
    private Integer prixAchatUnitaire;
    @Column(nullable = false , unique = true)
    private Integer prixVenteUnitaire;
    private Integer prixReviensUnitaire;
    private int status = 0;
    public void softDelete() {
        this.status = 1;                                                                                      
    }

    @ManyToOne
    @JoinColumn(name = "zoneStock", referencedColumnName = "id")
    private ZoneStock zoneStock;

    @ManyToOne
    @JoinColumn(name = "uniteMesure", referencedColumnName = "id")
    private UniteMesure uniteMesure;
//teste
    @ManyToOne
    @JoinColumn(name = "famille", referencedColumnName = "id")
    private FamilleArticle familleArticle;
}
