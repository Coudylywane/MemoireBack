package com.example.construction.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.construction.models.ZoneStock;


@Repository
public interface ZoneStockRepository extends JpaRepository<ZoneStock , Long>{

    @Query("SELECT z FROM ZoneStock z where z.status = 0")
    Page<ZoneStock> zoneStockPage(Pageable pageable);
}