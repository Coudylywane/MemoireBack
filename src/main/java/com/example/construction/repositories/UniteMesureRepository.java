package com.example.construction.repositories;

import com.example.construction.models.ZoneStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.construction.models.UniteMesure;

@Repository
public interface UniteMesureRepository extends JpaRepository<UniteMesure, Long>{
    @Query("SELECT z FROM UniteMesure z where z.status = 0")
    Page<UniteMesure> uniteMesurePage(Pageable pageable);
    
}
