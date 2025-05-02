package com.example.construction.services;

import com.example.construction.models.Article;
import com.example.construction.models.TacheArticle;
import com.example.construction.repositories.ArticleRepository;
import com.example.construction.repositories.TacheArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TacheArticleService {
    @Autowired
    private TacheArticleRepository tacheArticleRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Transactional
    public TacheArticle createTacheArticle(TacheArticle tacheArticle) {
        Article article = articleRepository.findById(tacheArticle.getArticle().getId())
                .orElseThrow(() -> new IllegalArgumentException("Article non trouvé"));
        TacheArticle savedTacheArticle = tacheArticleRepository.save(tacheArticle);
        updateArticleQuantity(article.getId());
        return savedTacheArticle;
    }

    @Transactional
    public TacheArticle updateTacheArticle(Long id, TacheArticle updatedTacheArticle) {
        TacheArticle existing = tacheArticleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TacheArticle non trouvé"));
        existing.setQuantiteUtilisee(updatedTacheArticle.getQuantiteUtilisee());
        existing.setTache(updatedTacheArticle.getTache());
        existing.setArticle(updatedTacheArticle.getArticle());
        TacheArticle savedTacheArticle = tacheArticleRepository.save(existing);
        updateArticleQuantity(savedTacheArticle.getArticle().getId());
        return savedTacheArticle;
    }

    @Transactional
    public void deleteTacheArticle(Long id) {
        TacheArticle tacheArticle = tacheArticleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("TacheArticle non trouvé"));
        Long articleId = tacheArticle.getArticle().getId();
        tacheArticleRepository.deleteById(id);
        updateArticleQuantity(articleId);
    }

    @Transactional
    public void updateArticleQuantity(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("Article non trouvé"));
        int totalQuantiteUtilisee = tacheArticleRepository.getTotalQuantiteUtiliseeByArticleId(articleId);
        // Supposons que quantity représente la quantité disponible
        // Si vous avez initialQuantity, utilisez : article.setQuantity(article.getInitialQuantity() - totalQuantiteUtilisee);
        article.setQuantity(article.getQuantity() - totalQuantiteUtilisee);
//        if (article.getQuantity() < 0) {
//            article.setQuantity(0);
//        }
        articleRepository.save(article);
    }
}
