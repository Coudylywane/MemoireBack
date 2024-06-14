package com.example.construction.services;

import com.example.construction.models.*;
import com.example.construction.repositories.CategorieFournisseurRepository;
import com.example.construction.repositories.FournisseurRepository;
import com.example.construction.repositories.TypeFournisseurRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FournisseurService {
    private final FournisseurRepository fournisseurRepository;
    private final TypeFournisseurRepository typeFournisseurRepository;
    private final CategorieFournisseurRepository categorieFournisseurRepository;



    public ResponseEntity<Object> addFournisseur(Fournisseur fournisseur) {
        try {

            TypeFournisseur typeFournisseur = typeFournisseurRepository.findById(fournisseur.getTypeFournisseur().getId())
                    .orElseThrow(() -> new javax.persistence.EntityNotFoundException("Type de fournisseur not found with id: " + fournisseur.getTypeFournisseur().getId()));
            CategorieFournisseur categorieFournisseur = categorieFournisseurRepository.findById(fournisseur.getCategorieFournisseur().getId())
                    .orElseThrow(() -> new javax.persistence.EntityNotFoundException("Categorie de fournisseur not found with id: " + fournisseur.getCategorieFournisseur().getId()));


            fournisseur.setAdresse(fournisseur.getAdresse());
            fournisseur.setEmail(fournisseur.getEmail());
            fournisseur.setNom(fournisseur.getNom());
            fournisseur.setSolde(fournisseur.getSolde());
            fournisseur.setTelephone(fournisseur.getTelephone());
            fournisseur.setTotalVersement(fournisseur.getTotalVersement());
            fournisseur.setTypeFournisseur(typeFournisseur);
            fournisseur.setCategorieFournisseur(categorieFournisseur);
            Fournisseur savedFournisseur = fournisseurRepository.save(fournisseur);
            // Renvoyez une réponse contenant l'objet Article en cas de succès
            return ResponseEntity.ok().body(savedFournisseur);
        } catch (Exception e) {
            // Gérez les autres exceptions ici
            throw e;
        }
    }
    ///MODIFICATION
    @Transactional
    public Fournisseur updateFournisseur(Fournisseur fournisseur) {
        try {
            // Vérifier si la famille que vous souhaitez mettre à jour existe dans la base de données
            Fournisseur existingFournisseur = fournisseurRepository.findById(fournisseur.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Famille not found with id: " + fournisseur.getId()));

            // Mettre à jour les propriétés de la famille existante avec les nouvelles valeurs
            existingFournisseur.setNom(fournisseur.getNom());
            existingFournisseur.setAdresse(fournisseur.getAdresse());
            existingFournisseur.setTelephone(fournisseur.getTelephone());
            existingFournisseur.setNom(fournisseur.getAdresse());
            existingFournisseur.setEmail(fournisseur.getEmail());
            existingFournisseur.setTotalVersement(fournisseur.getTotalVersement());
            existingFournisseur.setSolde(fournisseur.getSolde());

            // Enregistrer la mise à jour dans la base de données
            fournisseurRepository.save(existingFournisseur);
            // Retourner la famille mise à jour
            return existingFournisseur;
        } catch (Exception e) {
            // Gérer les exceptions, vous pouvez choisir de les logger ou de les relancer
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }

    //Suppression
    public Fournisseur softDeleteFournisseur(Long fournisseurId) {
        Fournisseur existingFournisseur = fournisseurRepository.findById(fournisseurId)
                .orElseThrow(() -> new javax.persistence.EntityNotFoundException("Fournisseur not found with id: " + fournisseurId));
        existingFournisseur.setStatus(1);
        return fournisseurRepository.save(existingFournisseur);
    }

    //LISTE
    public List<Fournisseur> getAllFournisseur() {
        return fournisseurRepository.findAll();
    }
    // Liste avec pagination
    public Page<Fournisseur> getFournisseurPage(Pageable pageable){
        return fournisseurRepository.fournisseurPage(pageable);
    }
    // Fournisseur by id
    public Fournisseur getFournisseurById(Long id) {
        return fournisseurRepository.findById(id).orElse(null);
    }




    //LISTE + TYPE
 //   @Transactional()
//    public List<Fournisseur> getAllFournisseurWithTypes() {
//        List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
//        return fournisseurs.stream()
//                .map(fournisseur -> new Fournisseur(
//                        fournisseur.getId(),
//                        fournisseur.getNom(),
//                        fournisseur.getAdresse(),
//                        fournisseur.getTelephone(),
//                        fournisseur.getEmail(),
//                        fournisseur.getTotalVersement(),
//                        fournisseur.getSolde(),
//                        fournisseur.getStatus(),
//                        fournisseur.getCategorieFournisseur(),
//                        fournisseur.getTypeFournisseur()
//                ))
//    }
}
