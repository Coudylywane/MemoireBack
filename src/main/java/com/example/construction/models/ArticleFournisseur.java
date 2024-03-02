package com.example.construction.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFournisseur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    id;

    @ManyToOne
    @JoinColumn(name = "fournisseur", referencedColumnName = "id")
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "article", referencedColumnName = "id")
    private Article article;
}
