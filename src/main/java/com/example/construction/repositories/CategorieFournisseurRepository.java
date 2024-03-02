package com.example.construction.repositories;

import com.example.construction.models.CategorieFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieFournisseurRepository extends JpaRepository<CategorieFournisseur, Long > {
}
