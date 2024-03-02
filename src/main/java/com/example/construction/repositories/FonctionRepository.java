package com.example.construction.repositories;

import com.example.construction.models.Fonctions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FonctionRepository  extends JpaRepository<Fonctions, Long> {
}
