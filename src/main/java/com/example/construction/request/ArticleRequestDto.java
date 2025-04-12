package com.example.construction.request;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ArticleRequestDto {
    private Long id;

    private String code;
    private String designation;
    private String description;
    private Integer poids;
    private Integer prixAchatUnitaire;
    private Integer prixVenteUnitaire;
    private Integer prixReviensUnitaire;
    private String status = "DISPONIBLE";
    private Double prixReel; 
    private Double prixDevis; // Le prix dans le devis (peut être différent du prix réel)
    // Quantité de l'article pour calculer le prix total
    private Integer quantity;

    // Prix total pour cet article basé sur la quantité et le prix du devis
    //@Transient
    //private Double totalPrice;

    // Méthode pour supprimer l'article sans le supprimer de la base de données
    //public void softDelete() {
        //this.status = "Archive";
    //}
}
