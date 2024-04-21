package com.example.construction.repositories;

import com.example.construction.models.UniteMesure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.construction.models.TypeArticle;

@Repository
public interface TypeArticleRepository extends JpaRepository<TypeArticle , Long>{
    @Query("SELECT t FROM TypeArticle t where t.status = 0")
    Page<TypeArticle> typeArticlePage(Pageable pageable);

}
