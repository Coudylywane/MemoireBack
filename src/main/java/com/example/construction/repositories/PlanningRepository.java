package com.example.construction.repositories;

import com.example.construction.models.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanningRepository extends JpaRepository<Planning, Long> {
    Planning findByDevisId(Long devisId);
        // Recherche par devisId avec fetch des tâches
        @Query("SELECT p FROM Planning p JOIN FETCH p.taches WHERE p.devis.id = :devisId")
            Optional<Planning> findByDevis(@Param("devisId") Long devisId);

        // Garder cette méthode si nécessaire pour d'autres cas
        @Query("SELECT p FROM Planning p JOIN FETCH p.taches WHERE p.projet.id = :projetId")
        Optional<Planning> findByProjetId(@Param("projetId") Long projetId);

}
