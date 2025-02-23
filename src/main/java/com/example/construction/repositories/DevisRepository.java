package com.example.construction.repositories;

import com.example.construction.models.Article;
import com.example.construction.models.Devis;
import com.example.construction.models.Projet;
import com.example.construction.models.enumeration.DevisStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DevisRepository extends JpaRepository<Devis, Long> {
    boolean existsByProjetAndStatut(Projet projet, DevisStatus statut);
}
