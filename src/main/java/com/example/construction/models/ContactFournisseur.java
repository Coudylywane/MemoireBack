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
public class ContactFournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false , unique = true)
    private String nom;
    @Column(nullable = false , unique = true)
    private String telephone;
    @Column(nullable = false , unique = true)
    private String email;
    private int status = 0;

    // Méthode pour la suppression logique
    public void softDelete() {
        this.status = 1;
    }


    @OneToMany(mappedBy = "contactFournisseur", cascade = CascadeType.ALL)
    private List<Fournisseur> fournisseurs;

}
