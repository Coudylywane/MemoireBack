package com.example.construction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.construction.models.TypeArticle;

@Repository
public interface TypeArticleRepository extends JpaRepository<TypeArticle , Long>{

}
