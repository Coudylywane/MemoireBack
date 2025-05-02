package com.example.construction.repositories;

import com.example.construction.models.TacheArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TacheArticleRepository extends JpaRepository<TacheArticle, Long> {
    @Query("SELECT COALESCE(SUM(ta.quantiteUtilisee), 0) FROM TacheArticle ta WHERE ta.article.id = :articleId")
    int getTotalQuantiteUtiliseeByArticleId(@Param("articleId") Long articleId);
}
