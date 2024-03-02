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
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String telephoneString;
    private String login;
    private String password;

    @Column(nullable=true)
    private String adresse;

    @Column(nullable=true)
    private String dateEmbauche;

    @Column(nullable=true)
    private int solde;
    

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;

    
    
}
