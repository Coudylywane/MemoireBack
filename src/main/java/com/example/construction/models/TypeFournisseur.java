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
public class TypeFournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String designation;
    private String description;

    private int status = 0;

    // Méthode pour la suppression logique
    public void softDelete() {
        this.status = 1;
    }

    @OneToMany(mappedBy = "typeFournisseur", cascade = CascadeType.ALL)
    private List<Fournisseur> fournisseurs;
}
