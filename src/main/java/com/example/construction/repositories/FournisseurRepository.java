package com.example.construction.repositories;

import com.example.construction.models.Fournisseur;
import com.example.construction.models.ZoneStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    @Query("SELECT f FROM Fournisseur f where f.status = 0")
    Page<Fournisseur> fournisseurPage(Pageable pageable);
}
