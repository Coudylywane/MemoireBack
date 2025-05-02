package com.example.construction.services;

import com.example.construction.jwtutils.ValidationUtils;
import com.example.construction.mapper.MapStructMapper;
import com.example.construction.models.*;
import com.example.construction.repositories.*;
import com.example.construction.request.ArticleRequestDto;
import com.example.construction.request.ProjetRequestDto;

import javax.persistence.*;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository ;
    private final ZoneStockRepository zoneStockRepository;
    private final UniteMesureRepository uniteMesureRepository;
    private final MapStructMapper mapStructMapper;

    //private final ValidationUtils validationUtils;
    private final TypeArticleRepository typeArticleRepository;
    private final ValidationUtils validationUtils ;
    public List<Article> getArticlesByIds(List<Long> ids) {
        return articleRepository.findAllByIdIn(ids); // Récupère les articles correspondant aux ids
    }


    public Article getArticleByCode(String code) {
        return articleRepository.findByCode(code).orElse(null);
    }
    
    
   
  
    public ResponseEntity<Object> addArticle(Article article) {
        try {
            String errorMessage = validationUtils.valideDesignation(article.getDesignation(), "Designation");
            if (errorMessage != null) {
                return ResponseEntity.badRequest().body(errorMessage);
            }
    
            // Vérification des relations obligatoires
            if (article.getZoneStock() == null || article.getZoneStock().getId() == null) {
                return ResponseEntity.badRequest().body("La zone de stockage est obligatoire.");
            }
            ZoneStock zoneStock = zoneStockRepository.findById(article.getZoneStock().getId()).orElse(null);
            if (zoneStock == null) {
                return ResponseEntity.badRequest().body("ZoneStock avec l'ID " + article.getZoneStock().getId() + " introuvable.");
            }
    
            if (article.getUniteMesure() == null || article.getUniteMesure().getId() == null) {
                return ResponseEntity.badRequest().body("L'unité de mesure est obligatoire.");
            }
            UniteMesure uniteMesure = uniteMesureRepository.findById(article.getUniteMesure().getId()).orElse(null);
            if (uniteMesure == null) {
                return ResponseEntity.badRequest().body("Unité de mesure avec l'ID " + article.getUniteMesure().getId() + " introuvable.");
            }
    
            if (article.getTypeArticle() == null || article.getTypeArticle().getId() == null) {
                return ResponseEntity.badRequest().body("Le type d'article est obligatoire.");
            }
            TypeArticle typeArticle = typeArticleRepository.findById(article.getTypeArticle().getId()).orElse(null);
            if (typeArticle == null) {
                return ResponseEntity.badRequest().body("Type d'article avec l'ID " + article.getTypeArticle().getId() + " introuvable.");
            }
    
            // Génération du code et vérification d'unicité
            String generatedCode;
            do {
                generatedCode = generateUniqueCode();
            } while (articleRepository.existsByCode(generatedCode)); // boucle tant que le code existe
    
            article.setCode(generatedCode);
            article.setZoneStock(zoneStock);
            article.setUniteMesure(uniteMesure);
            article.setTypeArticle(typeArticle);
    
            Article savedArticle = articleRepository.save(article);
            System.out.println("Article enregistré avec succès: " + savedArticle);
            return ResponseEntity.ok().body(savedArticle);
    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'ajout de l'article : " + e.getMessage());
        }
    }
    
    

    public Article createArticle(ArticleRequestDto articleRequestDto) {
        if (articleRequestDto == null || articleRequestDto.getCode() == null || articleRequestDto.getCode().isEmpty()) {
            throw new IllegalArgumentException("Le code est obligatoire.");
        }
        // Convertir DTO en entité
        Article article = mapStructMapper.ArticleDtoToArticle(articleRequestDto);
        // Enregistrer en base de données
        return articleRepository.save(article);
    }

    /**** LISTE ****/
    public List<Article> getAllArticle() {
        return articleRepository.findAll();
    }


    @Transactional
public Article updateArticle(Long articleId, Article updatedArticle) {
    try {
        // Vérifie que l'article existe
        Article existingArticle = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));

        // Vérifie les relations
        ZoneStock zoneStock = zoneStockRepository.findById(updatedArticle.getZoneStock().getId())
                .orElseThrow(() -> new EntityNotFoundException("ZoneStock not found with id: " + updatedArticle.getZoneStock().getId()));

        UniteMesure uniteMesure = uniteMesureRepository.findById(updatedArticle.getUniteMesure().getId())
                .orElseThrow(() -> new EntityNotFoundException("UniteMesure not found with id: " + updatedArticle.getUniteMesure().getId()));

        TypeArticle familleArticle = typeArticleRepository.findById(updatedArticle.getTypeArticle().getId())
                .orElseThrow(() -> new EntityNotFoundException("FamilleArticle not found with id: " + updatedArticle.getTypeArticle().getId()));

        // Mise à jour des champs (sans modifier le code)
        existingArticle.setDescription(updatedArticle.getDescription());
        existingArticle.setDesignation(updatedArticle.getDesignation());
        existingArticle.setPoids(updatedArticle.getPoids());
        existingArticle.setPrixAchatUnitaire(updatedArticle.getPrixAchatUnitaire());
        existingArticle.setPrixVenteUnitaire(updatedArticle.getPrixVenteUnitaire());
        existingArticle.setPrixReviensUnitaire(updatedArticle.getPrixReviensUnitaire());
        existingArticle.setZoneStock(zoneStock);
        existingArticle.setUniteMesure(uniteMesure);
        existingArticle.setTypeArticle(familleArticle);

        return articleRepository.save(existingArticle);

    } catch (EntityNotFoundException e) {
        throw new RuntimeException("Error updating article: " + e.getMessage());
    } catch (Exception e) {
        throw new RuntimeException("Error updating article.", e);
    }
}



    private String generateUniqueCode() {
        // Générez un code unique basé sur l'horodatage, par exemple
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return "ART" + now.format(formatter);
    }

    public Page<Article> getArticlePage(Pageable pageable){
        return articleRepository.articlePage(pageable);
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

}
// @Transactional
   // public ResponseEntity<Object> addArticle(Article article) {
        //try {
            // Validation
           // String errorMessage = validationUtils.valideDesignation(article.getDesignation(), "Designation");
           // if (errorMessage != null) {
                // La validation a échoué, renvoyez un ResponseEntity contenant le message d'erreur
           //     return ResponseEntity.badRequest().body(errorMessage);
           // }

            // La validation a réussi, continuez avec le reste du code...
            //ZoneStock zoneStock = zoneStockRepository.findById(article.getZoneStock().getId())
                   // .orElseThrow(() -> new EntityNotFoundException("ZoneStock not found with id: " + article.getZoneStock().getId()));

            //UniteMesure uniteMesure = uniteMesureRepository.findById(article.getUniteMesure().getId())
                    //.orElseThrow(() -> new EntityNotFoundException("UniteMesure not found with id: " + article.getUniteMesure().getId()));

            //TypeArticle typeArticle = typeArticleRepository.findById(article.getTypeArticle().getId())
                    //.orElseThrow(() -> new EntityNotFoundException("FamilleArticle not found with id: " + article.getTypeArticle().getId()));

          //  String generatedCode = generateUniqueCode();
          //  article.setCode(generatedCode);
          //  article.setDescription(article.getDescription());
          //  article.setDesignation(article.getDesignation());
          //  article.setPoids(article.getPoids());
          //  article.setPrixAchatUnitaire(article.getPrixAchatUnitaire());
          //  article.setPrixVenteUnitaire(article.getPrixVenteUnitaire());
          //  article.setPrixReviensUnitaire(article.getPrixReviensUnitaire());
            //article.setZoneStock(zoneStock);
            //article.setUniteMesure(uniteMesure);
            //article.setTypeArticle(typeArticle);

        //    Article savedArticle = articleRepository.save(article);

            // Renvoyez une réponse contenant l'objet Article en cas de succès
       //     return ResponseEntity.ok().body(savedArticle);
      //  } catch (Exception e) {
            // Gérez les autres exceptions ici
            //throw e;
    //    }
    //}