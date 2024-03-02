package com.example.construction.repositories;

import com.example.construction.models.ContactFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactFournisseurRepository extends JpaRepository<ContactFournisseur, Long> {
}
