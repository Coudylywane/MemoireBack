package com.example.construction.models;

import com.example.construction.models.enumeration.StatusArticle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Column(nullable = false, unique = true)
    private String designation;

    @Column(nullable = false, unique = true)
    private String description;

    @Column(nullable = false)
    private Integer poids;

    @Column(nullable = false)
    private Integer prixAchatUnitaire;

    @Column(nullable = false)
    private Integer prixVenteUnitaire;

    private Integer prixReviensUnitaire;

    @Enumerated(EnumType.STRING)
    private StatusArticle status = StatusArticle.DISPONIBLE;

    @Column(name = "prixReel", nullable = false)
    private Double prixReel;

    @Column(name = "prixDevis", nullable = false)
    private Double prixDevis;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = true)
    private Integer quantiteSeuil;

    @Transient
    private Double totalPrice;

    @ManyToOne
    @JoinColumn(name = "zoneStock", referencedColumnName = "id")
    private ZoneStock zoneStock;

    @ManyToOne
    @JoinColumn(name = "uniteMesure", referencedColumnName = "id")
    private UniteMesure uniteMesure;

    @ManyToOne
    @JoinColumn(name = "typeArticle", referencedColumnName = "id")
    private TypeArticle typeArticle;

    @ManyToOne
    @JoinColumn(name = "fournisseur", referencedColumnName = "id")
    private Fournisseur fournisseur;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TacheArticle> tacheArticles;

    @PrePersist
    @PreUpdate
    public void calculateTotalPrice() {
        if (this.prixDevis != null && this.quantity != null) {
            this.totalPrice = this.prixDevis * this.quantity;
        } else {
            this.totalPrice = 0.0;
        }
    }
}