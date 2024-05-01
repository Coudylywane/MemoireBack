package com.example.construction.repositories;

import com.example.construction.models.TypeArticle;
import com.example.construction.models.TypeFournisseur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeFournisseurRepository extends JpaRepository<TypeFournisseur, Long> {
    @Query("SELECT tf FROM TypeFournisseur tf where tf.status = 0")
    Page<TypeFournisseur> typeFournisseurPage(Pageable pageable);
}
