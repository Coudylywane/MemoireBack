package com.example.construction.repositories;

import com.example.construction.models.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartementRepository  extends JpaRepository<Departement, Long> {
}
