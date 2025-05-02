package com.example.construction.repositories;

import com.example.construction.models.TacheArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacheArticleRepository extends JpaRepository<TacheArticle, Long> {
}
