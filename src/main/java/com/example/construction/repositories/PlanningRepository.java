package com.example.construction.repositories;

import com.example.construction.models.Planning;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanningRepository extends JpaRepository<Planning, Long> {
    Planning findByDevisId(Long devisId);
}
