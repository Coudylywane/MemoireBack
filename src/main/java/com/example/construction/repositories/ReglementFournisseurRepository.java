package com.example.construction.repositories;

import com.example.construction.models.ReglementFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReglementFournisseurRepository extends JpaRepository<ReglementFournisseur, Long> {
}
