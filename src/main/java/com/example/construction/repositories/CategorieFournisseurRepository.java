package com.example.construction.repositories;

import com.example.construction.models.CategorieFournisseur;
import com.example.construction.models.TypeFournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieFournisseurRepository extends JpaRepository<CategorieFournisseur, Long > {
    @Query("SELECT cf FROM CategorieFournisseur cf where cf.status = 0")
    Page<CategorieFournisseur> categorieFournisseurPage(Pageable pageable);
}
