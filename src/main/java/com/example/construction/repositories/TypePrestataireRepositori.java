package com.example.construction.repositories;

import com.example.construction.models.TypePrestataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypePrestataireRepositori extends JpaRepository<TypePrestataire, Long> {
}