package com.example.construction.repositories;

import com.example.construction.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article , Long> {

    @Query("SELECT a FROM Article a where a.status = 0")
    Page<Article> articlePage(Pageable pageable);
}
