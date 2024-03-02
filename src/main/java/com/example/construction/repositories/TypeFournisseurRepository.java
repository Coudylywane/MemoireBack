package com.example.construction.repositories;

import com.example.construction.models.TypeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeFournisseurRepository extends JpaRepository<TypeFournisseur, Long> {
}
