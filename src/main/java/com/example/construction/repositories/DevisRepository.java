package com.example.construction.repositories;

import com.example.construction.models.Article;
import com.example.construction.models.Devis;
import com.example.construction.models.Projet;
import com.example.construction.models.Tache;
import com.example.construction.models.enumeration.DevisStatus;
import com.example.construction.models.enumeration.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DevisRepository extends JpaRepository<Devis, Long> {
    boolean existsByProjetAndStatut(Projet projet, DevisStatus statut);
    List<Devis> findByProjetId(Long projetId);

        @Query("SELECT t FROM Tache t LEFT JOIN t.planning p WHERE p.devis.id = :devisId AND t.status = :status")
        List<Tache> findTachesByDevisId(@Param("devisId") Long devisId, @Param("status") TaskStatus status);

        @Query("SELECT d FROM Devis d JOIN d.projet p WHERE p.client.id = :clientId")
        List<Devis> findByClientId(@Param("clientId") Long clientId);
}


