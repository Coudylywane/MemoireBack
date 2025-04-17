package com.example.construction.dto;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ArticleDto {
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

    // Prix total pour cet article basé sur la quantité et le prix du devis
    @Transient
    private Double totalPrice;
    
}
