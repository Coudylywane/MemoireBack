package com.example.construction.services;

import com.example.construction.models.Article;
import com.example.construction.models.Commande;
import com.example.construction.models.DetailCommande;
import com.example.construction.models.Fournisseur;
import com.example.construction.models.enumeration.StatusCommande;
import com.example.construction.repositories.CommandeRepository;
import com.example.construction.repositories.DetailCommandeRepository;
import com.example.construction.repositories.FournisseurRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import org.springframework.util.StringUtils;



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
        System.out.println(">>> COMMANDE REÇUE");
        System.out.println("Numero: " + commande.getNumero());
        System.out.println("Détails: " + commande.getDetailsCommande());
    
        // Générer automatiquement un numéro de commande s’il est vide
        if (commande.getNumero() == null || commande.getNumero().isEmpty()) {
            commande.setNumero(generateUniqueCommandeNumber());
        }
    
        // Initialiser le statut par défaut
        if (commande.getStatus() == null) {
            commande.setStatus(StatusCommande.EN_COURS);
        }
    
        // Lier chaque détail à la commande
        if (commande.getDetailsCommande() != null) {
            for (DetailCommande detail : commande.getDetailsCommande()) {
                detail.setCommande(commande);
            }
        }
    
        Commande saved = commandeRepository.save(commande);
        System.out.println(">>> COMMANDE SAUVEGARDÉE : " + saved.getId());
        return saved;
    }
    

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }
    /*********** Liste avec pagination ************/
    public Page<Commande> getCommandePage(Pageable pageable){
        return commandeRepository.findAll(pageable);
    }
    

    /********** commande by id *************/
    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    /********** GENERER UN CODE  *************/
    private String generateNumeroCommande() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return "CMD-" + now.format(formatter);
    }
    private String generateUniqueCommandeNumber() {
        LocalDateTime now = LocalDateTime.now();
        String uniqueId = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "CMD-" + now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "-" + uniqueId;
    }
    

    /*************** MODIFICATION *********/
    @Transactional
    public Commande updateCommande(Commande commande) {
        try {

            Commande existingCommande = commandeRepository.findById(commande.getId())
                .orElseThrow(() -> new EntityNotFoundException("Commande not found with id: " + commande.getId()));

            String generatedCode = generateNumeroCommande();
            existingCommande.setNumero(commande.getNumero());
            existingCommande.setStatus(commande.getStatus());
            existingCommande.setPrixTotal(commande.getPrixTotal());

            // Enregistrer la mise à jour dans la base de données
            commandeRepository.save(existingCommande);
            // Retourner la famille mise à jour
            return existingCommande;
        } catch (Exception e) {
            // Gérer les exceptions, vous pouvez choisir de les logger ou de les relancer
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }

    /***** COMMANDE GENERED ****/

    @Transactional
    public void generateBonDeCommande(Long idCommande) {
        // Récupérer la commande par son ID
        Commande commande = commandeRepository.findById(idCommande)
            .orElseThrow(() -> new IllegalArgumentException("Commande non trouvée"));

        // Logique de génération du bon de commande
        // Cela pourrait être l'ajout d'un document, l'envoi d'un email, ou simplement
        // le changement du statut de la commande.
        // Exemple de modification du statut de la commande :
        //commande.setStatus("BON_GENERER");
        commandeRepository.save(commande);

        // Optionnellement, vous pouvez générer un fichier PDF, envoyer un email, etc.
        // Cette partie dépend des besoins de votre application.
        // generatePDF(commande); // Méthode hypothétique pour générer un PDF du bon
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

    

