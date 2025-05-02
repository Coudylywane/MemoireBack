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
public class ZoneStock extends Generique{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String designation;
    private String description;
    private int status = 0;
    private String adresse;

//    // Méthode pour la suppression logique
//    public void softDelete() {
//        this.status = 1;
//    }
    
}
