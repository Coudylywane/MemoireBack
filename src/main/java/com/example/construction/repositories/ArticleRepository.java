package com.example.construction.repositories;

import com.example.construction.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article , Long> {

    @Query("SELECT a FROM Article a where a.status = 0")
    Page<Article> articlePage(Pageable pageable);

    Optional<Article> findByDesignation(String designation); // Recherche un article par son nom

    List<Article> findAllByIdIn(List<Long> ids);  

    Optional<Article> findByCode(String code);

    boolean existsByCode(String code);



}
