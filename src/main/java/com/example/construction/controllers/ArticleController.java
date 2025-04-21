package com.example.construction.controllers;


import com.example.construction.exceptions.BadRequestException;
import com.example.construction.models.Article;
import com.example.construction.models.Fournisseur;
import com.example.construction.models.Projet;
import com.example.construction.request.ArticleRequestDto;
import com.example.construction.request.ProjetRequestDto;
import com.example.construction.services.ArticleService;

import io.jsonwebtoken.io.IOException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import org.springframework.dao.DataIntegrityViolationException;
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
@Log
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

    //@PostMapping("/article-add")
    //public ResponseEntity<?> addArticle(@RequestBody Article article)throws IOException {
    //    System.out.println(article);
    //    try {
    //        ResponseEntity<Object> addedArticle = articleService.addArticle(article);
    //        return ResponseEntity.ok(addedArticle);
    //    } catch (Exception e) {
    //        String errorMessage = "Erreur lors de l'ajout de l'article : " + e.getMessage();
    //        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    //    }
    //}

    @PostMapping("/article-add")
public ResponseEntity<?> addArticle(@RequestBody Article article) throws IOException {
    try {
        System.out.println("Données reçues: " + article);
        ResponseEntity<Object> addedArticle = articleService.addArticle(article);
        return ResponseEntity.ok(addedArticle);
    } catch (Exception e) {
        String errorMessage = "Erreur lors de l'ajout de l'article : " + e.getMessage();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}

///////////////    
    @PostMapping("/createArticle")
    @Operation(
            summary = "Create a new article",
            description = "Creates a new article based on the provided article request data.",
            tags = { "Articles" })
    @ApiResponse(responseCode = "200", description = "Article created successfully")
    @ApiResponse(responseCode = "400", description = "Bad request due to validation errors")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<?> createArticle(@RequestBody ArticleRequestDto ArticleRequestDto) {
      try {
        if (ArticleRequestDto == null) {
          throw new IllegalArgumentException("Le projet ne peut pas être nul.");
        }
        Article Article = articleService.createArticle(ArticleRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(Article);
      } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("error", "Erreur de validation", "message", e.getMessage()));
      } catch (DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Conflit de données", "message", e.getMessage()));
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Erreur inattendue", "message", e.getMessage()));
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
            updatedArticle.setId(id);
            updatedArticle.setStatus(null);
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
    
    @GetMapping("/article/{id}")
    public ResponseEntity<?>getArticleById(@PathVariable Long id) {
        try {
            if (id == null) throw new BadRequestException("id required");
            Article article = articleService.getArticleById(id);
            if (article == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(article);
            }
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}


