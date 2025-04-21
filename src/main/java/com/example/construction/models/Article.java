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

    private String status = "DISPONIBLE";

    @Column(name = "prixReel", nullable = false)
    private Double prixReel; // Le prix réel de l'article

    @Column(name = "prixDevis", nullable = false)
    private Double prixDevis; // Le prix dans le devis (peut être différent du prix réel)

    // Quantité de l'article pour calculer le prix total
    @Column(nullable = false)
    private Integer quantity;


    // La quantite seuil qui vient verifier  le reste 
    @Column(nullable = true)
    private Integer quantiteSeuil;

    // Prix total pour cet article basé sur la quantité et le prix du devis
    @Transient
    private Double totalPrice;

    // Méthode pour supprimer l'article sans le supprimer de la base de données
    public void softDelete() {
        this.status = "Archive";
    }

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
    
    // Calcul du prix total en fonction du prix du devis et de la quantité
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
