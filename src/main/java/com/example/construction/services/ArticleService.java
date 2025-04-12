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
    private final MapStructMapper mapStructMapper;
    private final ZoneStockRepository zoneStockRepository;
    private final UniteMesureRepository uniteMesureRepository;
    //private final ValidationUtils validationUtils;
    private final TypeArticleRepository typeArticleRepository;
    private final ValidationUtils validationUtils ;
    public List<Article> getArticlesByIds(List<Long> ids) {
        return articleRepository.findAllByIdIn(ids); // Récupère les articles correspondant aux ids
    }


//    @Transactional
//    public Article addArticle(Article article) {
//        try {
//            // Validation
//            String errorMessage = validationUtils.valideDesignation(article.getDesignation(), "Designation");
//            if (errorMessage != null) {
//                // La validation a échoué, renvoyez un ResponseEntity contenant le message d'erreur
//                return ResponseEntity.badRequest().body(errorMessage);
//            }
//            ZoneStock zoneStock = zoneStockRepository.findById(article.getZoneStock().getId())
//                    .orElseThrow(() -> new EntityNotFoundException("ZoneStock not found with id: " + article.getZoneStock().getId()));
//
//            UniteMesure uniteMesure = uniteMesureRepository.findById(article.getUniteMesure().getId())
//                    .orElseThrow(() -> new EntityNotFoundException("UniteMesure not found with id: " + article.getUniteMesure().getId()));
//
//            FamilleArticle familleArticle = familleArticleRepository.findById(article.getFamilleArticle().getId())
//                    .orElseThrow(() -> new EntityNotFoundException("FamilleArticle not found with id: " + article.getFamilleArticle().getId()));
//
//            String generatedCode = generateUniqueCode();
//            article.setCode(generatedCode);
//            article.setDescription(article.getDescription());
//            article.setDesignation(article.getDesignation());
//            article.setPoids(article.getPoids());
//            article.setPrixAchatUnitaire(article.getPrixAchatUnitaire());
//            article.setPrixVenteUnitaire(article.getPrixVenteUnitaire());
//            article.setPrixReviensUnitaire(article.getPrixReviensUnitaire());
//            article.setZoneStock(zoneStock);
//            article.setUniteMesure(uniteMesure);
//            article.setFamilleArticle(familleArticle);
//
//            articleRepository.save(article);
//            return article;
//        } catch (Exception e) {
//            throw e;
//        }
//    }

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
    
    public ResponseEntity<Object> addArticle(Article article) {
        try {
            String errorMessage = validationUtils.valideDesignation(article.getDesignation(), "Designation");
            if (errorMessage != null) {
                return ResponseEntity.badRequest().body(errorMessage);
            }
    
            // Vérification et récupération des relations uniquement si elles ne sont pas null
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
    
            // Création de l'article
            String generatedCode = generateUniqueCode();
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

    //LISTE
    public List<Article> getAllArticle() {
        return articleRepository.findAll();
    }

    ///Delete
    public void softDeleteArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));

        article.softDelete(); // Utilisez la méthode de suppression logique définie dans l'entité
        articleRepository.save(article);
    }


    @Transactional
    public Article updateArticle(Long articleId, Article updatedArticle) {
        try {

            // Assurez-vous que l'article existe dans la base de données
            Article existingArticle = articleRepository.findById(articleId)
                    .orElseThrow(() -> new EntityNotFoundException("Article not found with id: " + articleId));

            // Assurez-vous que la zoneStock, uniteMesure et familleArticle existent dans la base de données
            ZoneStock zoneStock = zoneStockRepository.findById(updatedArticle.getZoneStock().getId())
                    .orElseThrow(() -> new EntityNotFoundException("ZoneStock not found with id: " + updatedArticle.getZoneStock().getId()));

            UniteMesure uniteMesure = uniteMesureRepository.findById(updatedArticle.getUniteMesure().getId())
                    .orElseThrow(() -> new EntityNotFoundException("UniteMesure not found with id: " + updatedArticle.getUniteMesure().getId()));

            TypeArticle familleArticle = typeArticleRepository.findById(updatedArticle.getTypeArticle().getId())
                    .orElseThrow(() -> new EntityNotFoundException("FamilleArticle not found with id: " + updatedArticle.getTypeArticle().getId()));

            // Mettez à jour les propriétés de l'article existant avec les nouvelles valeurs
            existingArticle.setCode(generateUniqueCode());
            existingArticle.setDescription(updatedArticle.getDescription());
            existingArticle.setDesignation(updatedArticle.getDesignation());
            existingArticle.setPoids(updatedArticle.getPoids());
            existingArticle.setPrixAchatUnitaire(updatedArticle.getPrixAchatUnitaire());
            existingArticle.setPrixVenteUnitaire(updatedArticle.getPrixVenteUnitaire());
            existingArticle.setPrixReviensUnitaire(updatedArticle.getPrixReviensUnitaire());
            existingArticle.setZoneStock(zoneStock);
            existingArticle.setUniteMesure(uniteMesure);
            existingArticle.setTypeArticle(familleArticle);

            // Enregistrez la mise à jour dans la base de données
            return articleRepository.save(existingArticle);

        } catch (EntityNotFoundException e) {
            // Gérez spécifiquement l'exception d'entité non trouvée
            throw new RuntimeException("Error updating article: " + e.getMessage());
        } catch (Exception e) {
            // Gérez d'autres exceptions de manière générale
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
