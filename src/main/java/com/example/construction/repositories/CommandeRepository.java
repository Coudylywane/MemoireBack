package com.example.construction.repositories;

import com.example.construction.models.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    public default String generateUniqueCommandeNumber() {
        // Utiliser l'heure actuelle et un identifiant unique pour générer un numéro de commande
        LocalDateTime now = LocalDateTime.now();
        String uniqueId = UUID.randomUUID().toString();
        String numero = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "-" + uniqueId.substring(0, 6);
        return numero;
    }
}
