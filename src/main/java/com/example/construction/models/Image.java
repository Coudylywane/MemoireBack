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
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "article", referencedColumnName = "id")
    private Article article;
}
