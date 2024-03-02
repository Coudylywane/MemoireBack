package com.example.construction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.construction.models.ZoneStock;


@Repository
public interface ZoneStockRepository extends JpaRepository<ZoneStock , Long>{

}