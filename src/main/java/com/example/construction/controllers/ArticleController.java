package com.example.construction.controllers;


import com.example.construction.models.Article;
import com.example.construction.services.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleController {
    private final ArticleService articleService;

//    @PostMapping("/article")
//    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
//        try {
//        Article addedArticle = articleService.addArticle(article);
//        return ResponseEntity.ok(addedArticle);
//        } catch (Exception e) {
//        return ResponseEntity.status(500).build();
//        }
//    }

    @PostMapping("/article")
    public ResponseEntity<?> addArticle(@RequestBody Article article) {
        try {
            ResponseEntity<Object> addedArticle = articleService.addArticle(article);
            return ResponseEntity.ok(addedArticle);
        } catch (Exception e) {
            String errorMessage = "Erreur lors de l'ajout de l'article : " + e.getMessage();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping("/article")
    public ResponseEntity<?> getAllArticles() {
        List<Article> articles = articleService.getAllArticle();
        return ResponseEntity.ok(articles);
    }

    @PutMapping("/article/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle) {
        try {
            Article result = articleService.updateArticle(id, updatedArticle);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating article: " + e.getMessage());
        }
    }


    @DeleteMapping("/article/{id}")
    public ResponseEntity<?> softDeleteArticle(@PathVariable Long id) {
        try {
            articleService.softDeleteArticle(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/articles")
    public ResponseEntity<?> getArticlePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "2") int size
    ){
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Article> articlesPage = articleService.getArticlePage(paging);
            Map<String, Object> response = new HashMap<>();
            response.put("article", articlesPage.getContent());
            response.put("currentPage", articlesPage.getNumber());
            response.put("totalItems", articlesPage.getTotalElements());
            response.put("totalPages", articlesPage.getTotalPages());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw e;
        }
    }
}


