package com.example.construction.repositories;

import com.example.construction.models.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long> {
    List<Validation> findByProjectId(Long projectId); // 🔍 Trouver toutes les validations d'un projet
}

