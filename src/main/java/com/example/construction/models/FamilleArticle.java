package com.example.construction.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@jsonidentityinfo(generator = objectidgenerators.propertygenerator.class property = id )
@Cacheable(false)
public class FamilleArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String designation;
    private String description;
    private int status = 0;

    @JsonIgnore
    @OneToMany(mappedBy = "familleArticle", cascade = CascadeType.ALL)
    private List<TypeArticle> typeArticles = new ArrayList<>();


    // Ajoutez cette méthode pour retirer un TypeArticle de la liste
    public void removeArticleType(TypeArticle articleType) {
        this.typeArticles.remove(articleType);
        articleType.setFamilleArticle(null); // Assurez-vous de maintenir la cohérence des deux côtés de la relation
    }

    // Méthode pour la suppression logique
    public void softDelete() {
        this.status = 1;
    }

    // Méthode pour ajouter un TypeArticle à la liste
    public void addTypeArticle(TypeArticle typeArticle) {
        typeArticles.add(typeArticle);
        typeArticle.setFamilleArticle(this); // Maintenir la relation bidirectionnelle
    }

}
