package com.example.construction.repositories;

import com.example.construction.models.ContactPrestataire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactPrestataireRepository extends JpaRepository<ContactPrestataire, Long> {
}
