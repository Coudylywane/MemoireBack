package com.example.construction.models;
import javax.persistence.*;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TypeArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String designation;
    private String description;
    private int status = 0;


    //    // Méthode pour la suppression logique
  public void softDelete() {
       this.status = 1;
    }
}
