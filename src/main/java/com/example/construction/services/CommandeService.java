package com.example.construction.services;

import com.example.construction.models.Article;
import com.example.construction.models.Commande;
import com.example.construction.models.DetailCommande;
import com.example.construction.models.Fournisseur;
import com.example.construction.repositories.CommandeRepository;
import com.example.construction.repositories.DetailCommandeRepository;
import com.example.construction.repositories.FournisseurRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CommandeService {
    //private final CommandeRepository commandeRepository;
    private final DetailCommandeRepository detailCommandeRepository;
    private final FournisseurRepository fournisseurRepository;
    @Autowired
    private CommandeRepository commandeRepository;

    @Transactional
    public Commande addCommande(Commande commande) {
        System.out.println(">>> C   OMMANDE REÇUE");
        System.out.println("Numero: " + commande.getNumero());
        System.out.println("Détails: " + commande.getDetailsCommande());
    
        if (commande.getDetailsCommande() != null) {
            for (DetailCommande detail : commande.getDetailsCommande()) {
                detail.setCommande(commande);
            }
        }
    
        Commande saved = commandeRepository.save(commande);
        System.out.println(">>> COMMANDE SAUVEGARDÉE : " + saved.getId());
        return saved;
    }
    




/*public Commande addCommande(Commande commande) {
    // Associer chaque détail à la commande
    if (commande.getDetailsCommande() != null) {
        for (DetailCommande detail : commande.getDetailsCommande()) {
            detail.setCommande(commande);
        }
    }

    // Calculer le prix total
    double prixTotal = 0.0;
    for (DetailCommande detail : commande.getDetailsCommande()) {
        if (detail.getArticle().getPrixAchatUnitaire() == null) {
            throw new IllegalArgumentException("Prix unitaire manquant pour l'article : " + detail.getArticle().getCode());
        }
        double prixDetail = detail.getNombre() * detail.getArticle().getPrixAchatUnitaire();
        detail.setPrixTotal(prixDetail);
        prixTotal += prixDetail;
    }

    commande.setPrixTotal(prixTotal);

    // Générer la date et le numéro
    commande.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    commande.setNumero(commandeRepository.generateUniqueCommandeNumber());

    for (DetailCommande d : commande.getDetailsCommande()) {
        d.setCommande(commande);
    }
    return commandeRepository.save(commande);
}*/



}

    

