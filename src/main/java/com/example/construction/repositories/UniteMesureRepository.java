package com.example.construction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.construction.models.UniteMesure;

@Repository
public interface UniteMesureRepository extends JpaRepository<UniteMesure, Long>{

    
}
