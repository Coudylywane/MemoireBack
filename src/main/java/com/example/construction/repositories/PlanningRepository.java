package com.example.construction.repositories;

import com.example.construction.models.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlanningRepository extends JpaRepository<Planning, Long> {
        Planning findByDevisId(Long devisId);
        @Query("SELECT p FROM Planning p WHERE p.devis.id = :devisId")
        List<Planning> findByDevis(@Param("devisId") Long devisId);
}
