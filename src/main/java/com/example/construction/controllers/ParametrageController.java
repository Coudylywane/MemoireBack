package com.example.construction.controllers;

import java.util.List;


import com.example.construction.models.*;
import javax.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.construction.request.TypeArticleRequest;
import com.example.construction.services.ParametrageService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ParametrageController {

    private final ParametrageService parametrageService;

    @PostMapping("/zone")
    public ResponseEntity<ZoneStock> addZone(@RequestBody ZoneStock newZone) {
        try {
            // Enregistrez le nouveau ZoneStock avec le statut défini sur 0 par défaut
            newZone.setStatus(0);
            ZoneStock savedZone = parametrageService.addZone(newZone);

            return ResponseEntity.ok(savedZone);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/zone")
    public ResponseEntity<?> getAllZone() {
        List<ZoneStock> zone = parametrageService.getAllZone();
        return ResponseEntity.ok(zone);
    }

    @PutMapping("/zone/{id}")
    public ResponseEntity<?> updateZone(@PathVariable Long id, @RequestBody ZoneStock updatedZone) {
        try {
            updatedZone.setId(id);
            updatedZone.setStatus(0);

            ZoneStock updated = parametrageService.updateZone(updatedZone);

            return ResponseEntity.ok(updated);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Zone not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/zone/{id}")
    public ResponseEntity<?> softDeleteZone(@PathVariable Long id) {
        try {
            parametrageService.softDeleteZone(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Zone not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    ///////////////////////////////////// UNITE MESURE ///////////////////////////////////////////////////////////
    
    @PostMapping("/unite")
    public ResponseEntity<UniteMesure> addUnite(@RequestBody UniteMesure newUnite) {
        try {
            // Enregistrez le nouveau unite avec le statut défini sur 0 par défaut
            newUnite.setStatus(0);
            UniteMesure savedUnite = parametrageService.addUnite(newUnite);

            return ResponseEntity.ok(savedUnite);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/unite")
    public ResponseEntity<?> getAllUnite() {
        List<UniteMesure> zone = parametrageService.getAllUnite();
        return ResponseEntity.ok(zone);
    }

    @PutMapping("/unite/{id}")
    public ResponseEntity<?> updateUnite(@PathVariable Long id, @RequestBody UniteMesure updatedUnite) {
        try {
            updatedUnite.setId(id);
            updatedUnite.setStatus(0);
            UniteMesure updated = parametrageService.updateUnite(updatedUnite);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unite not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/unite/{id}")
    public ResponseEntity<?> softDeleteUnite(@PathVariable Long id) {
        try {
            parametrageService.softDeleteUnite(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Unite not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    ///////////////////////////////////// FAMILLE ///////////////////////////////////////////////////////////

    @PostMapping("/famille")
    public ResponseEntity<FamilleArticle> addFamille(@RequestBody FamilleArticle newFamille) {
        try {
            // Enregistrez le nouveau ZoneStock avec le statut défini sur 0 par défaut
            newFamille.setStatus(0);
            FamilleArticle savedFamille = parametrageService.addFamille(newFamille);

            return ResponseEntity.ok(savedFamille);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/famille")
    public ResponseEntity<?> getAllFamille() {
        List<FamilleArticle> famille = parametrageService.getAllFamilleWithTypes();
        return ResponseEntity.ok(famille);
    }

    @GetMapping("/familleAndType")
    public ResponseEntity<?> getAllFamilleWithTypes() {
        List<FamilleArticle> famille = parametrageService.getAllFamilleWithTypes();
        return ResponseEntity.ok(famille);
    }

    @PutMapping("/famille/{id}")
    public ResponseEntity<?> updateFamille(@PathVariable Long id, @RequestBody FamilleArticle famille) {
        try {
            famille.setId(id);
            famille.setStatus(0);
            FamilleArticle updated = parametrageService.updateFamille(famille);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Famille not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/famille/{id}")
    public ResponseEntity<?> softDeleteFamille(@PathVariable Long id) {
        try {
            parametrageService.softDeleteFamille(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Famille not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    ///////////////////////////////////// TYPE ARTICLE ///////////////////////////////////////////////////////////

//Ajout
    @PostMapping("/typeArticles")
    public ResponseEntity<?> addTypeArticleToFamily(@RequestBody TypeArticle typeArticle) {
        try {
            FamilleArticle familleArticle = parametrageService.getFamilleById(typeArticle.getFamilleArticle().getId());
            typeArticle.setFamilleArticle(familleArticle);
            TypeArticle savedTypeArticle = parametrageService.addTypeArticleToFamily(typeArticle);
            return ResponseEntity.ok().body(savedTypeArticle);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/typeArticles/{id}")
    public ResponseEntity<?> updateTypeArticle(@PathVariable Long id, @RequestBody TypeArticle typeArticle) {
        try {
            typeArticle.setId(id);
            // Appeler la méthode de mise à jour du service
            TypeArticle updatedTypeArticle = parametrageService.updateTypeArticle(typeArticle);
            return ResponseEntity.ok(updatedTypeArticle);
        } catch (EntityNotFoundException e) {
            // Retourner une réponse NOT_FOUND si le TypeArticle n'est pas trouvé
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TypeArticle not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while updating TypeArticle");
        }
    }

    @DeleteMapping("/typeArticles/{id}")
    public ResponseEntity<?> softDeleteTypeFamille(@PathVariable Long id) {
        try {
            parametrageService.softDeleteTypeArticle(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Famille not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/typeArticles")
    public ResponseEntity<?> getAllTypeArticle() {
        List<TypeArticle> typeArticles = parametrageService.getAllTypeArticle();
        return ResponseEntity.ok(typeArticles);
    }

////////////////////////////////////// CATEGORIE FOURNISSEUR ///////////////////////////////////////////////////////

    @PostMapping("/categorie-fournisseur")
    public ResponseEntity<CategorieFournisseur> addCategorieFournisseur(@RequestBody CategorieFournisseur newCategorieFournisseur) {
        try {
            // Enregistrez le nouveau CATEGORIE FOURNISSEUR avec le statut défini sur 0 par défaut
            newCategorieFournisseur.setStatus(0);
            CategorieFournisseur savedCategorieFournisseur = parametrageService.addCategorieFournisseur(newCategorieFournisseur);

            return ResponseEntity.ok(savedCategorieFournisseur);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping("/categorie-fournisseur")
    public ResponseEntity<?> getAllCategorieFournisseur() {
        List<CategorieFournisseur> categories = parametrageService.getAllCategorieFournisseur();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/categorie-fournisseur/{id}")
    public ResponseEntity<?> updateCategorieFournisseur(@PathVariable Long id, @RequestBody CategorieFournisseur updatedCategorieFournisseur) {
        try {
            updatedCategorieFournisseur.setId(id);
            updatedCategorieFournisseur.setStatus(0);

            CategorieFournisseur updated = parametrageService.updateCategorieFournisseur(updatedCategorieFournisseur);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Zone not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/categorie-fournisseur/{id}")
    public ResponseEntity<?> softDeleteCategorieFournisseur(@PathVariable Long id) {
        try {
            parametrageService.softDeleteCategorieFournisseur(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Zone not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    
    ////////////////////////////////////// TYPE FOURNISSEUR ///////////////////////////////////////////////////////

    @PostMapping("/type-fournisseur")
    public ResponseEntity<TypeFournisseur> addTypeFournisseur(@RequestBody TypeFournisseur newCategorieFournisseur) {
        try {
            // Enregistrez le nouveau TYPE FOURNISSEUR avec le statut défini sur 0 par défaut
            newCategorieFournisseur.setStatus(0);
            TypeFournisseur savedCategorieFournisseur = parametrageService.addTypeFournisseur(newCategorieFournisseur);
            return ResponseEntity.ok(savedCategorieFournisseur);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/type-fournisseur")
    public ResponseEntity<?> getAllTypeFournisseur() {
        List<TypeFournisseur> categories = parametrageService.getAllTypeFournisseur();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/type-fournisseur/{id}")
    public ResponseEntity<?> updateTypeFournisseur(@PathVariable Long id, @RequestBody TypeFournisseur updatedTypeFournisseur) {
        try {
            updatedTypeFournisseur.setId(id);
            updatedTypeFournisseur.setStatus(0);
            TypeFournisseur updated = parametrageService.updateTypeFournisseur(updatedTypeFournisseur);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TypeFournisseur not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/type-fournisseur/{id}")
    public ResponseEntity<?> softDeleteTypeFournisseur(@PathVariable Long id) {
        try {
            parametrageService.softDeleteTypeFournisseur(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TypeFournisseur not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    ////////////////////////////////////// CONTACT FOURNISSEUR ///////////////////////////////////////////////////////

    @PostMapping("/contact-fournisseur")
    public ResponseEntity<ContactFournisseur> addContactFournisseur(@RequestBody ContactFournisseur newContactFournisseur) {
        try {
            // Enregistrez le nouveau CONTACT FOURNISSEUR avec le statut défini sur 0 par défaut
            newContactFournisseur.setStatus(0);
            ContactFournisseur savedCategorieFournisseur = parametrageService.createContactFournisseur(newContactFournisseur);
            return ResponseEntity.ok(savedCategorieFournisseur);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/contact-fournisseur")
    public ResponseEntity<?> getAllContactFournisseur() {
        List<ContactFournisseur> contacts = parametrageService.getAllContactFournisseur();
        return ResponseEntity.ok(contacts);
    }

    @PutMapping("/contact-fournisseur/{id}")
    public ResponseEntity<?> updateContactFournisseur(@PathVariable Long id, @RequestBody ContactFournisseur updatedContactFournisseur) {
        try {
            updatedContactFournisseur.setId(id);
            updatedContactFournisseur.setStatus(0);
            ContactFournisseur updated = parametrageService.updateContactFournisseur(updatedContactFournisseur);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ContactFournisseur not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/contact-fournisseur/{id}")
    public ResponseEntity<?> softDeleteContactFournisseur(@PathVariable Long id) {
        try {
            parametrageService.softDeleteContactFournisseur(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ContactFournisseur not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    ///////////////////////////////////// FOURNISSEURS ///////////////////////////////////////////////////////////

    @PostMapping("/fournisseur")
    public ResponseEntity<Fournisseur> addFournisseur(@RequestBody Fournisseur newFournisseur) {
        try {
            // Enregistrez le nouveau ZoneStock avec le statut défini sur 0 par défaut
            newFournisseur.setStatus(0);
            Fournisseur savedFournisseur = parametrageService.addFournisseur(newFournisseur);

            return ResponseEntity.ok(savedFournisseur);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/fournisseur")
    public ResponseEntity<?> getAllFournisseur() {
        List<Fournisseur> fournisseur = parametrageService.getAllFournisseurWithTypes();
        return ResponseEntity.ok(fournisseur);
    }

    @GetMapping("/fournisseurAndType")
    public ResponseEntity<?> getAllFournisseurWithTypes() {
        List<Fournisseur> fournisseur = parametrageService.getAllFournisseurWithTypes();
        return ResponseEntity.ok(fournisseur);
    }



    ///////////////////////////////////// FOURNISSEURS ///////////////////////////////////////////////////////////

    @PutMapping("/fournisseur/{id}")
    public ResponseEntity<?> updateFournisseur(@PathVariable Long id, @RequestBody Fournisseur fournisseur) {
        try {
            fournisseur.setId(id);
            fournisseur.setStatus(0);
            Fournisseur updated = parametrageService.updateFournisseur(fournisseur);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Famille not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/fournisseur/{id}")
    public ResponseEntity<?> softDeleteFournisseur(@PathVariable Long id) {
        try {
            parametrageService.softDeleteFamille(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Famille not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //////////////////////////////////// TYPE PRESTATAIRE /////////////////////////

    @PostMapping("/typePre")
    public ResponseEntity<TypePrestataire> addTypePrestataire(@RequestBody TypePrestataire newTypePrestataire) {
        try {
            // Enregistrez le nouveau ZoneStock avec le statut défini sur 0 par défaut
            newTypePrestataire.setStatus(0);
            TypePrestataire savedTypePrestataire = parametrageService.addTypePrestataire(newTypePrestataire);

            return ResponseEntity.ok(savedTypePrestataire);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }


    @GetMapping("/typePre")
    public ResponseEntity<?> getAllTypePrestataire() {
        List<TypePrestataire> zone = parametrageService.getAllTypePrestataire();
        return ResponseEntity.ok(zone);
    }

    @PutMapping("/typePre/{id}")
    public ResponseEntity<?> updateTypePrestataire(@PathVariable Long id, @RequestBody TypePrestataire updatedTypePrestataire) {
        try {
            updatedTypePrestataire.setId(id);
            updatedTypePrestataire.setStatus(0);

            TypePrestataire updated = parametrageService.updateTypePrestataire(updatedTypePrestataire);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("TypePrestataire not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/typePre/{id}")
    public ResponseEntity<?> softDeleteTypePrestataire(@PathVariable Long id) {
        try {
            parametrageService.softDeleteTypePrestataire(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Type Prestataire not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //////////////////////////////////// CONTACT PRESTATAIRE /////////////////////////

    @PostMapping("/contact-prestataire")
    public ResponseEntity<ContactPrestataire> addContactPrestataire(@RequestBody ContactPrestataire newContactPrestataire) {
        try {
            // Enregistrez le nouveau ZoneStock avec le statut défini sur 0 par défaut
            newContactPrestataire.setStatus(0);
            ContactPrestataire savedContactPrestataire = parametrageService.addContactPrestataire(newContactPrestataire);

            return ResponseEntity.ok(savedContactPrestataire);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/contact-prestataire")
    public ResponseEntity<?> getAllContactPrestataire() {
        List<ContactPrestataire> contact = parametrageService.getAllContactPrestataire();
        return ResponseEntity.ok(contact);
    }

    @PutMapping("/contact-prestataire/{id}")
    public ResponseEntity<?> updateContactPrestataire(@PathVariable Long id, @RequestBody ContactPrestataire updatedContactPrestataire) {
        try {
            updatedContactPrestataire.setId(id);
            updatedContactPrestataire.setStatus(0);

            ContactPrestataire updated = parametrageService.updateContactPrestataire(updatedContactPrestataire);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact Prestataire not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/contact-prestataire/{id}")
    public ResponseEntity<?> softDeleteContactPrestataire(@PathVariable Long id) {
        try {
            parametrageService.softDeleteContactPrestataire(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact Prestataire not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //////////////////////////////////// DEPARTEMENT PRESTATAIRE /////////////////////////

    @PostMapping("/departement-prestataire")
    public ResponseEntity<Departement> addDepartementPrestataire(@RequestBody Departement newDepartement) {
        try {
            // Enregistrez le nouveau ZoneStock avec le statut défini sur 0 par défaut
            newDepartement.setStatus(0);
            Departement savedDepartement = parametrageService.addDepartement(newDepartement);

            return ResponseEntity.ok(savedDepartement);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/departement-prestataire")
    public ResponseEntity<?> getAllDepartement() {
        List<Departement> departement = parametrageService.getAllDepartement();
        return ResponseEntity.ok(departement);
    }

    @PutMapping("/departement-prestataire/{id}")
    public ResponseEntity<?> updateDepartement(@PathVariable Long id, @RequestBody Departement updatedDepartement) {
        try {
            updatedDepartement.setId(id);
            updatedDepartement.setStatus(0);

            Departement updated = parametrageService.updateDepartement(updatedDepartement);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Departement Prestataire not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/departement-prestataire/{id}")
    public ResponseEntity<?> softDeleteDepartement(@PathVariable Long id) {
        try {
            parametrageService.softDeleteDepartement(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Departement Prestataire not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //////////////////////////////////// FONCTIONS PRESTATAIRE /////////////////////////

    @PostMapping("/fonction-prestataire")
    public ResponseEntity<Fonctions> addFonctionsPrestataire(@RequestBody Fonctions newFonctions) {
        try {
            // Enregistrez le nouveau ZoneStock avec le statut défini sur 0 par défaut
            newFonctions.setStatus(0);
            Fonctions savedFonctions = parametrageService.addFonctions(newFonctions);

            return ResponseEntity.ok(savedFonctions);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/fonction-prestataire")
    public ResponseEntity<?> getAllFonctions() {
        List<Fonctions> fonctions = parametrageService.getAllFonctions();
        return ResponseEntity.ok(fonctions);
    }

    @PutMapping("/fonction-prestataire/{id}")
    public ResponseEntity<?> updateFonctions(@PathVariable Long id, @RequestBody Fonctions updatedFonctions) {
        try {
            updatedFonctions.setId(id);
            updatedFonctions.setStatus(0);

            Fonctions updated = parametrageService.updateFonctions(updatedFonctions);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fonctions Prestataire not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/fonction-prestataire/{id}")
    public ResponseEntity<?> softDeleteFonctions(@PathVariable Long id) {
        try {
            parametrageService.softDeleteFonctions(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fonctions Prestataire not found with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
//>>>>>>> 7186956e54d4c2ef8f4161a73d4bb189ffa5ac28
//
//    @PostMapping("/fournisseur")
//    public ResponseEntity<Fournisseur> addFournisseur(@RequestBody Fournisseur newFournisseur) {
//        try {
//
//            // Enregistrez le nouveau ZoneStock avec le statut défini sur 0 par défaut
//            newFournisseur.setStatus(0);
//
//            Fournisseur savedFournisseur = parametrageService.addFournisseur(newFournisseur);
//
//            return ResponseEntity.ok(savedFournisseur);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).build();
//        }
//    }
//
//    @GetMapping("/fournisseur")
//    public ResponseEntity<?> getAllFournisseur() {
//        List<Fournisseur> fournisseur = parametrageService.getAllFournisseurWithTypes();
//        return ResponseEntity.ok(fournisseur);
//    }
//
//    @GetMapping("/fournisseurAndType")
//    public ResponseEntity<?> getAllFournisseurWithTypes() {
//        List<Fournisseur> fournisseur = parametrageService.getAllFournisseurWithTypes();
//        return ResponseEntity.ok(fournisseur);
//    }
//
//
//    @PutMapping("/fournisseur/{id}")
//    public ResponseEntity<?> updateFournisseur(@PathVariable Long id, @RequestBody Fournisseur fournisseur) {
//        try {
//            fournisseur.setId(id);
//            fournisseur.setStatus(0);
//            Fournisseur updated = parametrageService.updateFournisseur(fournisseur);
//            return ResponseEntity.ok(updated);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Famille not found with id: " + id);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/fournisseur/{id}")
//    public ResponseEntity<?> softDeleteFournisseur(@PathVariable Long id) {
//        try {
//            parametrageService.softDeleteFamille(id);
//            return ResponseEntity.ok().build();
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Famille not found with id: " + id);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
}
