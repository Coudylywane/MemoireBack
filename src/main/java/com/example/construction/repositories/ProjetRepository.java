package com.example.construction.repositories;

import com.example.construction.models.Article;
import com.example.construction.models.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long>{
}
