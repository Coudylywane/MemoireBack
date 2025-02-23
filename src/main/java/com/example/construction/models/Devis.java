package com.example.construction.models;

import com.example.construction.models.enumeration.DevisStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Devis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String client;
    private LocalDate dateCreation;
    private DevisStatus statut = DevisStatus.EN_ATTENTE; // Statut par défaut

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "devis_id")
    private List<LigneDevis> lignesDevis = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false) // Un devis doit être lié à un projet
    private Projet projet;

    // Méthode pour calculer le total du devis
    public double getTotal() {
        return lignesDevis.stream()
                .mapToDouble(ligne -> ligne.getQuantite() * ligne.getArticle().getPrixDevis())
                .sum();
    }

    public Map<Long, Double> calculerDifferencesPrix() {
        Map<Long, Double> differences = new HashMap<>();

        for (LigneDevis ligne : this.getLignesDevis()) {
            double difference = ligne.getArticle().getPrixReel() - ligne.getArticle().getPrixDevis();
            differences.put(ligne.getArticle().getId(), difference);
        }

        return differences;
    }
}
