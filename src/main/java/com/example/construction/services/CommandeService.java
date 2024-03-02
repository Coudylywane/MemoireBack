package com.example.construction.services;

import com.example.construction.models.Commande;
import com.example.construction.models.DetailCommande;
import com.example.construction.repositories.CommandeRepository;
import com.example.construction.repositories.DetailCommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommandeService {
    private final CommandeRepository commandeRepository ;
    private final DetailCommandeRepository detailCommandeRepository;
    
    @Transactional
    public DetailCommande addDetailCommande(DetailCommande detailCommande){
        try {
            int prix = detailCommande.getNombre()*detailCommande.getArticle().getPrixAchatUnitaire();
            detailCommande.setPrixTotal(Double.parseDouble(String.valueOf(prix)));
            return detailCommandeRepository.save(detailCommande);
        }catch (Exception e){
            throw e;
        }
    }
    
    /// Ajout
    @Transactional
    public Commande addCommande(Commande commande){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            commande.setDate(dateFormat.format(date));
         return commandeRepository.save(commande);
        }catch (Exception e){
            throw e ;
        }
    }
    
    
}
