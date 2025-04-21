package com.example.construction.models;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilisateur implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String telephoneString;
    private String login;
    private String password;
    @Column(columnDefinition = "boolean default false")
    private boolean archive;
    @Column(columnDefinition = "boolean default false")
    private boolean status;

//    @Column(nullable=true)
//    private String adresse;
//
//    @Column(nullable=true)
//    private String dateEmbauche;
//
//    @Column(nullable=true)
//    private int solde;
    

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
