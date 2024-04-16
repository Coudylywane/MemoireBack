package com.example.construction.services;

import com.example.construction.models.Article;
import com.example.construction.models.Commande;
import com.example.construction.models.DetailCommande;
import com.example.construction.models.Fournisseur;
import com.example.construction.repositories.CommandeRepository;
import com.example.construction.repositories.DetailCommandeRepository;
import com.example.construction.repositories.FournisseurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CommandeService {
    private final CommandeRepository commandeRepository;
    private final DetailCommandeRepository detailCommandeRepository;
    private final FournisseurRepository fournisseurRepository;

    @Transactional
    public Commande addCommande(List<DetailCommande> detailsCommande) {
        // Créer une nouvelle commande avec les détails fournis
        Commande nouvelleCommande = new Commande();
        // Générer un numéro de commande unique en utilisant l'heure actuelle et un identifiant unique
        String numeroCommande = commandeRepository.generateUniqueCommandeNumber();
        nouvelleCommande.setNumero(numeroCommande);
        // Obtenir la date et l'heure actuelles au moment de la création de la commande
        LocalDateTime dateCreation = LocalDateTime.now();
        // Formatter la date et l'heure dans le format souhaité
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateCreationFormattee = dateCreation.format(formatter);
        // Définir la date de création de la commande
        nouvelleCommande.setDate(dateCreationFormattee);
        // Calculer le prix total de la commande et créer les détails de commande pour chaque article commandé
        double prixTotalCommande = 0.0;
        for (DetailCommande detailCommande : detailsCommande) {
            // Créer un nouveau détail de commande
            DetailCommande nouveauDetail = new DetailCommande();
            // Définir la commande pour le nouveau détail de commande
            nouveauDetail.setCommande(nouvelleCommande);
            // Copier les autres attributs du détail de commande actuel
            nouveauDetail.setNombre(detailCommande.getNombre());
            // Calculer le prix total du détail de commande
           // System.out.println(detailCommande.getArticle().getCode());
            // Get the unit purchase price of the article
            Integer prixAchatUnitaire = detailCommande.getArticle().getPrixAchatUnitaire();
            // Check if the unit purchase price is null
            if (prixAchatUnitaire == null) {
                // Handle the null case, e.g., log a warning, throw an exception, or use a default value
                System.out.println("Warning: Unit purchase price is null for article code " + detailCommande.getArticle().getCode());
                prixAchatUnitaire = 0; // Default value if null is acceptable
            }
            // Calculate the total price of the order detail
            int prixtotalDetailCommande = detailCommande.getNombre() * prixAchatUnitaire;
            nouveauDetail.setPrixTotal((double) prixtotalDetailCommande);

            // Ajouter le prix total du détail de commande au prix total de la commande
            prixTotalCommande += nouveauDetail.getPrixTotal();
        }
        // Définir le prix total de la commande
        nouvelleCommande.setPrixTotal(prixTotalCommande);
        // Enregistrer la nouvelle commande dans la base de données
        Commande commandeEnregistre = commandeRepository.save(nouvelleCommande);
        return commandeEnregistre;
    }



}
    
//    @Transactional
//    public DetailCommande addDetailCommande(DetailCommande detailCommande){
//        try {
//            int prix = detailCommande.getNombre()*detailCommande.getArticle().getPrixAchatUnitaire();
//            detailCommande.setPrixTotal(Double.parseDouble(String.valueOf(prix)));
//            return detailCommandeRepository.save(detailCommande);
//        }catch (Exception e){
//            throw e;
//        }
//    }
//
//    /// Ajout
//    @Transactional
//    public Commande addCommande(Commande commande){
//        try {
//            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//            Date date = new Date();
//            commande.setDate(dateFormat.format(date));
//         return commandeRepository.save(commande);
//        }catch (Exception e){
//            throw e ;
//        }
//    }

    

