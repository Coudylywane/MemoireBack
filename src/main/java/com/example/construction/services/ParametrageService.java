package com.example.construction.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.construction.repositories.ContactPrestataireRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.construction.models.*;
import com.example.construction.repositories.*;
import com.example.construction.request.TypeArticleRequest;
import javax.persistence.*;

import javax.transaction.Transactional;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ParametrageService {


    private final ZoneStockRepository zoneStockRepository;
    private final UniteMesureRepository uniteMesureRepository;
    private final CategorieFournisseurRepository categorieFournisseurRepository;
    private final ContactFournisseurRepository contactFournistRepository;
    private final TypeFournisseurRepository typeFournisseurRepository;
    private final TypeArticleRepository typeArticleRepository;
    private final TypePrestataireRepositori typePrestataireRepositori;
    private final DepartementRepository departmentRepository;
    private final ContactPrestataireRepository contactPrestataireRepository;
    private final FonctionRepository  fonctionRepository;


    /// / //////////////////// ZONE //////////////////////////////////////////////////////////
    //AJOUT
    public ZoneStock addZone(ZoneStock zone) {
        try {
            zoneStockRepository.save(zone);
            return zone;
        } catch (Exception e) {
            throw new RuntimeException("Erreur de l'ajout de zone", e);
        }
    }

    //MODIFICATION
    public ZoneStock updateZone(ZoneStock updatedZone) {
        try {
            // Vérifier si la zone que vous souhaitez mettre à jour existe dans la base de données
            ZoneStock existingZone = zoneStockRepository.findById(updatedZone.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Zone not found with id: " + updatedZone.getId()));

            // Mettre à jour les propriétés de la zone existante avec les nouvelles valeurs
            existingZone.setDesignation(updatedZone.getDesignation());
            existingZone.setDescription(updatedZone.getDescription());

            // Enregistrer la mise à jour dans la base de données
            zoneStockRepository.save(existingZone);

            // Retourner la zone mise à jour
            return existingZone;
        } catch (Exception e) {
            // Gérer les exceptions, vous pouvez choisir de les logger ou de les relancer
            throw new RuntimeException("Erreur de la modification de la  zone", e);
        }
    }

    //Suppression
    public ZoneStock softDeleteZone(Long zoneId) {
        ZoneStock existingZone = zoneStockRepository.findById(zoneId)
                .orElseThrow(() -> new EntityNotFoundException("Zone not found with id: " + zoneId));
        existingZone.setStatus(1);
        return zoneStockRepository.save(existingZone);
    }

    //LISTE
    public List<ZoneStock> getAllZone() {
        return zoneStockRepository.findAll();
    }

    public Page<ZoneStock> getZoneStockPage(Pageable pageable){
        return zoneStockRepository.zoneStockPage(pageable);
    }

    // Recuperation zone par l'id

    public ZoneStock getZoneById(Long id) {
        return zoneStockRepository.findById(id).orElse(null);
    }


    /// / //////////////////// UNITE MESURE //////////////////////////////////////////////////////////

    //AJOUT
    public UniteMesure addUnite(UniteMesure uniteMesure) {
        try {
            uniteMesureRepository.save(uniteMesure);
            return uniteMesure;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }

    //MODIFICATION
    public UniteMesure updateUnite(UniteMesure uniteMesure) {
        try {
            // Vérifier si la zone que vous souhaitez mettre à jour existe dans la base de données
            UniteMesure existingUnite = uniteMesureRepository.findById(uniteMesure.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Unite not found with id: " + uniteMesure.getId()));

            // Mettre à jour les propriétés de la zone existante avec les nouvelles valeurs
            existingUnite.setNom(uniteMesure.getNom());
            existingUnite.setAbreviation(uniteMesure.getAbreviation());

            // Enregistrer la mise à jour dans la base de données
            uniteMesureRepository.save(existingUnite);

            // Retourner la zone mise à jour
            return existingUnite;
        } catch (Exception e) {
            // Gérer les exceptions, vous pouvez choisir de les logger ou de les relancer
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }


//    public void softDeleteUnite(Long uniteId) {
//        UniteMesure existingUnite = uniteMesureRepository.findById(uniteId)
//                .orElseThrow(() -> new EntityNotFoundException("Unite not found with id: " + uniteId));
//
//        existingUnite.softDelete(); // Utilisez la méthode de suppression logique définie dans l'entité
//
//        // Si vous voulez une exception plus spécifique, vous pouvez créer une UniteNotFoundException
//        try {
//            uniteMesureRepository.save(existingUnite);
//        } catch (Exception e) {
//            // Log the exception or perform any necessary actions
//            throw new RuntimeException("Error while soft deleting unite", e);
//        }
//    }

    public UniteMesure softDeleteUnite(Long uniteId) {
        UniteMesure existingUnite = uniteMesureRepository.findById(uniteId)
                .orElseThrow(() -> new EntityNotFoundException("Unite de mesure not found with id: " + uniteId));
        existingUnite.setStatus(1);
        return uniteMesureRepository.save(existingUnite);
    }

    public UniteMesure getUniteById(Long id) {
        return uniteMesureRepository.findById(id).orElse(null);
    }



    //LISTE
    public List<UniteMesure> getAllUnite() {
        return uniteMesureRepository.findAll();
    }

    public Page<UniteMesure> getUniteMesurePage(Pageable pageable){
        return uniteMesureRepository.uniteMesurePage(pageable);
    }

    /// / //////////////////// FAMILLE  //////////////////////////////////////////////////////////

//    //AJOUT
//    public FamilleArticle addFamille(FamilleArticle famille) {
//        try {
//            familleArticleRepository.save(famille);
//            return famille;
//        } catch (Exception e) {
//            throw new RuntimeException("Erreur lors de l'ajout", e);
//        }
//    }
//
//    @Transactional
//    public FamilleArticle updateFamille(FamilleArticle famille) {
//        // Utilisez le mécanisme de @Transactional de Spring plutôt que le bloc try-catch
//        FamilleArticle existingFamille = familleArticleRepository.findById(famille.getId())
//                .orElseThrow(() -> new EntityNotFoundException("Famille not found with id: " + famille.getId()));
//        // Mettre à jour les propriétés de la famille existante avec les nouvelles valeurs non nulles
//        if (famille.getDesignation() != null) {
//            existingFamille.setDesignation(famille.getDesignation());
//        }
//        if (famille.getDescription() != null) {
//            existingFamille.setDescription(famille.getDescription());
//        }
//        // Utilisez Collection.removeAll pour supprimer les types obsolètes
//        existingFamille.getTypeArticles().removeAll(
//                existingFamille.getTypeArticles()
//                        .stream()
//                        .filter(existingType -> !famille.getTypeArticles().contains(existingType))
//                        .collect(Collectors.toList())
//        );
//        // Ajoutez les nouveaux types
//        famille.getTypeArticles().forEach(existingFamille::addTypeArticle);
//        // Enregistrer la mise à jour dans la base de données
//        return familleArticleRepository.save(existingFamille);
//    }
//
//    public void softDeleteFamille(Long familleId) {
//        FamilleArticle existingFamille = familleArticleRepository.findById(familleId)
//                .orElseThrow(() -> new EntityNotFoundException("Famille not found with id: " + familleId));
//
//        existingFamille.softDelete(); // Utilisez la méthode de suppression logique définie dans l'entité
//
//        // Si vous voulez une exception plus spécifique, vous pouvez créer une UniteNotFoundException
//        try {
//            familleArticleRepository.save(existingFamille);
//        } catch (Exception e) {
//            // Log the exception or perform any necessary actions
//            throw new RuntimeException("Error while soft deleting unite", e);
//        }
//    }
//
//    //LISTE
//    public List<FamilleArticle> getAllFamille() {
//        return familleArticleRepository.findAll();
//    }
//    public FamilleArticle getFamilleById(Long id) {
//        return familleArticleRepository.findById(id).orElse(null);
//    }
//
//    //LISTE + TYPE
//    @Transactional()
//    public List<FamilleArticle> getAllFamilleWithTypes() {
//        List<FamilleArticle> familles = familleArticleRepository.findAll();
//
//        return familles.stream()
//                .map(famille -> new FamilleArticle(
//                        famille.getId(),
//                        famille.getDesignation(),
//                        famille.getDescription(),
//                        famille.getStatus(),
//                        famille.getTypeArticles()))
//                .collect(Collectors.toList());
//    }

    /// / //////////////////// TYPE FAMILLE  //////////////////////////////////////////////////////////

    public TypeArticle addTypeArticleToFamily(TypeArticle typeArticle) {
        try{
            return typeArticleRepository.save(typeArticle);
        }catch(Exception e){
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }

    @Transactional
    public TypeArticle updateTypeArticle(TypeArticle typeArticle) {
        TypeArticle existingTypeArticle = typeArticleRepository.findById(typeArticle.getId())
                .orElseThrow(() -> new EntityNotFoundException("TypeArticle not found with id: " + typeArticle.getId()));

        // Mettre à jour les propriétés du TypeArticle existant avec les nouvelles valeurs
        existingTypeArticle.setDesignation(typeArticle.getDesignation());
        existingTypeArticle.setDescription(typeArticle.getDescription());
        // Enregistrer la mise à jour dans la base de données
        return typeArticleRepository.save(existingTypeArticle);
    }

//    public void softDeleteTypeArticle(Long typeArticleId) {
//        TypeArticle existingTypeArticle = typeArticleRepository.findById(typeArticleId)
//                .orElseThrow(() -> new EntityNotFoundException("Famille not found with id: " + typeArticleId));
//        existingTypeArticle.softDelete(); // Utilisez la méthode de suppression logique définie dans l'entité
//        // Si vous voulez une exception plus spécifique, vous pouvez créer une UniteNotFoundException
//        try {
//            typeArticleRepository.save(existingTypeArticle);
//        } catch (Exception e) {
//            // Log the exception or perform any necessary actions
//            throw new RuntimeException("Error while soft deleting type article", e);
//        }
//    }
    public TypeArticle softDeleteTypeArticle(Long typeArticleId) {
        TypeArticle existingType = typeArticleRepository.findById(typeArticleId)
                .orElseThrow(() -> new EntityNotFoundException("Unite de mesure not found with id: " + typeArticleId));
        existingType.setStatus(1);
        return typeArticleRepository.save(existingType);
    }

    //LISTE
    public List<TypeArticle> getAllTypeArticle() {
        return typeArticleRepository.findAll();
    }

    public Page<TypeArticle> getTypeArticlePage(Pageable pageable){
        return typeArticleRepository.typeArticlePage(pageable);
    }

    public TypeArticle getTypeById(Long id) {
        return typeArticleRepository.findById(id).orElse(null);
    }

    /// / //////////////////// CATEGORIE FOURNISSEUR //////////////////////////////////////////////////////////
    //AJOUT
    public CategorieFournisseur addCategorieFournisseur(CategorieFournisseur categorieFournisseur) {
        try {
            categorieFournisseurRepository.save(categorieFournisseur);
            return categorieFournisseur;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }

    //MODIFICATION
    public CategorieFournisseur updateCategorieFournisseur(CategorieFournisseur updatedCategorieFournisseur) {
        try {
            // Vérifier si la zone que vous souhaitez mettre à jour existe dans la base de données
            CategorieFournisseur existingCategorieFournisseur = categorieFournisseurRepository.findById(updatedCategorieFournisseur.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Zone not found with id: " + updatedCategorieFournisseur.getId()));

            // Mettre à jour les propriétés de la zone existante avec les nouvelles valeurs
            existingCategorieFournisseur.setDesignation(updatedCategorieFournisseur.getDesignation());
            existingCategorieFournisseur.setDescription(updatedCategorieFournisseur.getDescription());

            // Enregistrer la mise à jour dans la base de données
            categorieFournisseurRepository.save(existingCategorieFournisseur);

            // Retourner la zone mise à jour
            return existingCategorieFournisseur;
        } catch (Exception e) {
            // Gérer les exceptions, vous pouvez choisir de les logger ou de les relancer
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }

    //Suppression
    public CategorieFournisseur softDeleteCategorieFournisseur(Long categorieFournisseurId) {
        CategorieFournisseur existingCategorieFournisseur = categorieFournisseurRepository.findById(categorieFournisseurId)
                .orElseThrow(() -> new EntityNotFoundException("Unite de mesure not found with id: " + categorieFournisseurId));
        existingCategorieFournisseur.setStatus(1);
        return categorieFournisseurRepository.save(existingCategorieFournisseur);
    }

    public Page<CategorieFournisseur> getCategorieFournisseurPage(Pageable pageable){
        return categorieFournisseurRepository.categorieFournisseurPage(pageable);
    }

    public CategorieFournisseur getCategorieFournisseurById(Long id) {
        return categorieFournisseurRepository.findById(id).orElse(null);
    }

    //LISTE
    public List<CategorieFournisseur> getAllCategorieFournisseur() {
        return categorieFournisseurRepository.findAll();
    }

    /// / //////////////////// TYPE FOURNISSEUR //////////////////////////////////////////////////////////
    //AJOUT
    public TypeFournisseur addTypeFournisseur(TypeFournisseur typeFournisseur) {
        try {
            typeFournisseurRepository.save(typeFournisseur);
            return typeFournisseur;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }

    public Page<TypeFournisseur> getTypeFournisseurPage(Pageable pageable){
        return typeFournisseurRepository.typeFournisseurPage(pageable);
    }

    public TypeFournisseur getTypeFournisseurById(Long id) {
        return typeFournisseurRepository.findById(id).orElse(null);
    }

    //MODIFICATION
    public TypeFournisseur updateTypeFournisseur(TypeFournisseur updatedTypeFournisseur) {
        try {
            // Vérifier si la zone que vous souhaitez mettre à jour existe dans la base de données
            TypeFournisseur existingTypeFournisseur = typeFournisseurRepository.findById(updatedTypeFournisseur.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Zone not found with id: " + updatedTypeFournisseur.getId()));

            // Mettre à jour les propriétés de la zone existante avec les nouvelles valeurs
            existingTypeFournisseur.setDesignation(updatedTypeFournisseur.getDesignation());
            existingTypeFournisseur.setDescription(updatedTypeFournisseur.getDescription());

            // Enregistrer la mise à jour dans la base de données
            typeFournisseurRepository.save(existingTypeFournisseur);

            // Retourner la zone mise à jour
            return existingTypeFournisseur;
        } catch (Exception e) {
            // Gérer les exceptions, vous pouvez choisir de les logger ou de les relancer
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }


    public TypeFournisseur softDeleteTypeFournisseur(Long typeFournisseurId) {
        TypeFournisseur existingTypeFournisseur = typeFournisseurRepository.findById(typeFournisseurId)
                .orElseThrow(() -> new EntityNotFoundException("Unite de mesure not found with id: " + typeFournisseurId));
        existingTypeFournisseur.setStatus(1);
        return typeFournisseurRepository.save(existingTypeFournisseur);
    }

    //LISTE
    public List<TypeFournisseur> getAllTypeFournisseur() {
        return typeFournisseurRepository.findAll();
    }

    //////////////////////////////////////////// CONTACT FOURNISSEUR ///////////////////////////////////////////

    //CREATION
    public ContactFournisseur createContactFournisseur(ContactFournisseur contactFournisseur) {
        try {
            contactFournistRepository.save(contactFournisseur);
            return contactFournisseur;
        }catch (Exception e){
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }

    //MODIFICATION
    public ContactFournisseur updateContactFournisseur(ContactFournisseur updatedContactFournisseur) {
        try {
            // Vérifier si la zone que vous souhaitez mettre à jour existe dans la base de données
            ContactFournisseur existingContactFournisseur = contactFournistRepository.findById(updatedContactFournisseur.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Contact not found with id: " + updatedContactFournisseur.getId()));
            // Mettre à jour les propriétés de la zone existante avec les nouvelles valeurs
            existingContactFournisseur.setNom(updatedContactFournisseur.getNom());
            existingContactFournisseur.setTelephone(updatedContactFournisseur.getTelephone());
            existingContactFournisseur.setEmail(updatedContactFournisseur.getEmail());

            // Enregistrer la mise à jour dans la base de données
            contactFournistRepository.save(existingContactFournisseur);

            // Retourner la zone mise à jour
            return existingContactFournisseur;
        } catch (Exception e) {
            // Gérer les exceptions, vous pouvez choisir de les logger ou de les relancer
            throw new RuntimeException("Erreur lors de l'ajout", e);
        }
    }
    // Suppression logique
    public void softDeleteContactFournisseur(Long contactFournisseurId) {
        ContactFournisseur existingContactFournisseur = contactFournistRepository.findById(contactFournisseurId)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found with id: " + contactFournisseurId));

        existingContactFournisseur.softDelete(); // Utilisez la méthode de suppression logique définie dans l'entité
        contactFournistRepository.save(existingContactFournisseur);
    }

    //LISTE
    public List<ContactFournisseur> getAllContactFournisseur() {
        return contactFournistRepository.findAll();
    }


    ///////////////////////////////////////// TYPE DE PRESTATAIRE ///////////////////////////////////////
    //AJOUT
    public TypePrestataire addTypePrestataire(TypePrestataire typePrestataire) {
        try {
            typePrestataireRepositori.save(typePrestataire);
            return typePrestataire;
        } catch (Exception e) {
            throw e;
        }
    }

    //MODIFICATION
    public TypePrestataire updateTypePrestataire(TypePrestataire updatedTypePrestataire) {
        try {
            // Vérifier si la zone que vous souhaitez mettre à jour existe dans la base de données
            TypePrestataire existingTypePrestataire = typePrestataireRepositori.findById(updatedTypePrestataire.getId())
                    .orElseThrow(() -> new EntityNotFoundException("TypePrestataire not found with id: " + updatedTypePrestataire.getId()));

            // Mettre à jour les propriétés de la zone existante avec les nouvelles valeurs
            existingTypePrestataire.setDesignation(updatedTypePrestataire.getDesignation());
            existingTypePrestataire.setDescription(updatedTypePrestataire.getDescription());

            // Enregistrer la mise à jour dans la base de données
            typePrestataireRepositori.save(existingTypePrestataire);

            // Retourner la zone mise à jour
            return existingTypePrestataire;
        } catch (Exception e) {
            // Gérer les exceptions, vous pouvez choisir de les logger ou de les relancer
            throw e;
        }
    }

    //Suppression
    public void softDeleteTypePrestataire(Long typePrestId) {
        TypePrestataire existingTypePrestataire = typePrestataireRepositori.findById(typePrestId)
                .orElseThrow(() -> new EntityNotFoundException("Zone not found with id: " + typePrestId));

        existingTypePrestataire.softDelete(); // Utilisez la méthode de suppression logique définie dans l'entité
        typePrestataireRepositori.save(existingTypePrestataire);
    }

    //LISTE
    public List<TypePrestataire> getAllTypePrestataire() {
        return typePrestataireRepositori.findAll();
    }

    /////////////////////////////// CONTACT PRESTATAIRE /////////////////////////////

    //AJOUT
    public ContactPrestataire addContactPrestataire(ContactPrestataire contactPrestataire) {
        try {
            contactPrestataireRepository.save(contactPrestataire);
            return contactPrestataire;
        } catch (Exception e) {
            throw e;
        }
    }

    //MODIFICATION
    public ContactPrestataire updateContactPrestataire(ContactPrestataire updatedContactPrestataire) {
        try {
            // Vérifier si la zone que vous souhaitez mettre à jour existe dans la base de données
            ContactPrestataire existingContactPrestataire = contactPrestataireRepository.findById(updatedContactPrestataire.getId())
                    .orElseThrow(() -> new EntityNotFoundException("ContactPrestataire not found with id: " + updatedContactPrestataire.getId()));

            // Mettre à jour les propriétés de la zone existante avec les nouvelles valeurs
            existingContactPrestataire.setNom(updatedContactPrestataire.getNom());
            existingContactPrestataire.setEmail(updatedContactPrestataire.getEmail());
            existingContactPrestataire.setTelephone(updatedContactPrestataire.getTelephone());

            // Enregistrer la mise à jour dans la base de données
            contactPrestataireRepository.save(existingContactPrestataire);

            // Retourner la zone mise à jour
            return existingContactPrestataire;
        } catch (Exception e) {
            // Gérer les exceptions, vous pouvez choisir de les logger ou de les relancer
            throw e;
        }
    }

    //Suppression
    public void softDeleteContactPrestataire(Long contactId) {
        ContactPrestataire existingContactPrestataire = contactPrestataireRepository.findById(contactId)
                .orElseThrow(() -> new EntityNotFoundException("contactId not found with id: " + contactId));

        existingContactPrestataire.softDelete(); // Utilisez la méthode de suppression logique définie dans l'entité
        contactPrestataireRepository.save(existingContactPrestataire);
    }

    //LISTE
    public List<ContactPrestataire> getAllContactPrestataire() {
        return contactPrestataireRepository.findAll();
    }

    /////////////////////////////// DEPARTEMENT PRESTATAIRE /////////////////////////////

    //AJOUT
    public Departement addDepartement(Departement departement) {
        try {
            departmentRepository.save(departement);
            return departement;
        } catch (Exception e) {
            throw e;
        }
    }

    //MODIFICATION
    public Departement updateDepartement(Departement updatedDepartement) {
        try {
            // Vérifier si la zone que vous souhaitez mettre à jour existe dans la base de données
            Departement existingDepartement = departmentRepository.findById(updatedDepartement.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Departement not found with id: " + updatedDepartement.getId()));

            // Mettre à jour les propriétés de la zone existante avec les nouvelles valeurs
            existingDepartement.setChefDepartement(updatedDepartement.getChefDepartement());
            existingDepartement.setDesignation(updatedDepartement.getDesignation());
           // existingDepartement.set(updatedDepartement.getTelephone());

            // Enregistrer la mise à jour dans la base de données
            departmentRepository.save(existingDepartement);

            // Retourner la zone mise à jour
            return existingDepartement;
        } catch (Exception e) {
            // Gérer les exceptions, vous pouvez choisir de les logger ou de les relancer
            throw e;
        }
    }

    //Suppression
    public void softDeleteDepartement(Long departementtId) {
        Departement existingDepartement = departmentRepository.findById(departementtId)
                .orElseThrow(() -> new EntityNotFoundException("contactId not found with id: " + departementtId));

        existingDepartement.softDelete(); // Utilisez la méthode de suppression logique définie dans l'entité
        departmentRepository.save(existingDepartement);
    }

    //LISTE
    public List<Departement> getAllDepartement() {
        return departmentRepository.findAll();
    }

    /////////////////////////////// FONCTION PRESTATAIRE /////////////////////////////

    //AJOUT
    public Fonctions addFonctions(Fonctions fonctions) {
        try {
            fonctionRepository.save(fonctions);
            return fonctions;
        } catch (Exception e) {
            throw e;
        }
    }

    //MODIFICATION
    public Fonctions updateFonctions(Fonctions updatedFonctions) {
        try {
            // Vérifier si la zone que vous souhaitez mettre à jour existe dans la base de données
            Fonctions existingFonctions = fonctionRepository.findById(updatedFonctions.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Departement not found with id: " + updatedFonctions.getId()));

            // Mettre à jour les propriétés de la zone existante avec les nouvelles valeurs
            existingFonctions.setDescription(updatedFonctions.getDescription());
            existingFonctions.setNom(updatedFonctions.getNom());
            // existingDepartement.set(updatedDepartement.getTelephone());

            // Enregistrer la mise à jour dans la base de données
            fonctionRepository.save(existingFonctions);

            // Retourner la zone mise à jour
            return existingFonctions;
        } catch (Exception e) {
            // Gérer les exceptions, vous pouvez choisir de les logger ou de les relancer
            throw e;
        }
    }

    //Suppression
    public void softDeleteFonctions(Long fonctionsId) {
        Fonctions existingFonctions = fonctionRepository.findById(fonctionsId)
                .orElseThrow(() -> new EntityNotFoundException("Fonctions not found with id: " + fonctionsId));

        existingFonctions.softDelete(); // Utilisez la méthode de suppression logique définie dans l'entité
        fonctionRepository.save(existingFonctions);
    }

    //LISTE
    public List<Fonctions> getAllFonctions() {
        return fonctionRepository.findAll();
    }
}

