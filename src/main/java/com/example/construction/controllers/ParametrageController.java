package com.example.construction.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.example.construction.exceptions.BadRequestException;
import com.example.construction.exceptions.InternalServerErrorException;
import com.example.construction.models.*;
import javax.persistence.EntityNotFoundException;

import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.construction.request.TypeArticleRequest;
import com.example.construction.services.ParametrageService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@Log
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

    @GetMapping("/zones")
    public ResponseEntity<?> getZonePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "2") int size
    ){
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<ZoneStock> zoneStockPage = parametrageService.getZoneStockPage(paging);
            Map<String, Object> response = new HashMap<>();
            response.put("zone", zoneStockPage.getContent());
            response.put("currentPage", zoneStockPage.getNumber());
            response.put("totalItems", zoneStockPage.getTotalElements());
            response.put("totalPages", zoneStockPage.getTotalPages());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw e;
        }
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

    @GetMapping("/zone/{id}")
    public ResponseEntity<?> getZoneById(@PathVariable Long id) {
        try {
            if (id == null) throw new BadRequestException("id required");
            ZoneStock zoneStock = parametrageService.getZoneById(id);
            if (zoneStock == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(zoneStock);
            }
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
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

    @GetMapping("/uniteMesures")
    public ResponseEntity<?> getUniteMesurePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "2") int size
    ){
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<UniteMesure> uniteMesurePage= parametrageService.getUniteMesurePage(paging);
            Map<String, Object> response = new HashMap<>();
            response.put("uniteMesure", uniteMesurePage.getContent());
            response.put("currentPage", uniteMesurePage.getNumber());
            response.put("totalItems", uniteMesurePage.getTotalElements());
            response.put("totalPages", uniteMesurePage.getTotalPages());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw e;
        }
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

    @GetMapping("/unite/{id}")
    public ResponseEntity<?> getUniteById(@PathVariable Long id) {
        try {
            if (id == null) throw new BadRequestException("id required");
            UniteMesure uniteMesure = parametrageService.getUniteById(id);
            if (uniteMesure == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(uniteMesure);
            }
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

//    ///////////////////////////////////// FAMILLE ///////////////////////////////////////////////////////////
//
//    @PostMapping("/famille")
//    public ResponseEntity<FamilleArticle> addFamille(@RequestBody FamilleArticle newFamille) {
//        try {
//            // Enregistrez le nouveau ZoneStock avec le statut défini sur 0 par défaut
//            newFamille.setStatus(0);
//            FamilleArticle savedFamille = parametrageService.addFamille(newFamille);
//
//            return ResponseEntity.ok(savedFamille);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).build();
//        }
//    }
//
//    @GetMapping("/famille")
//    public ResponseEntity<?> getAllFamille() {
//        List<FamilleArticle> famille = parametrageService.getAllFamilleWithTypes();
//        return ResponseEntity.ok(famille);
//    }
//
//    @GetMapping("/familleAndType")
//    public ResponseEntity<?> getAllFamilleWithTypes() {
//        List<FamilleArticle> famille = parametrageService.getAllFamilleWithTypes();
//        return ResponseEntity.ok(famille);
//    }
//
//    @PutMapping("/famille/{id}")
//    public ResponseEntity<?> updateFamille(@PathVariable Long id, @RequestBody FamilleArticle famille) {
//        try {
//            famille.setId(id);
//            famille.setStatus(0);
//            FamilleArticle updated = parametrageService.updateFamille(famille);
//            return ResponseEntity.ok(updated);
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Famille not found with id: " + id);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/famille/{id}")
//    public ResponseEntity<?> softDeleteFamille(@PathVariable Long id) {
//        try {
//            parametrageService.softDeleteFamille(id);
//            return ResponseEntity.ok().build();
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Famille not found with id: " + id);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

    ///////////////////////////////////// TYPE ARTICLE ///////////////////////////////////////////////////////////


    @PostMapping("/typeArticles")
    public ResponseEntity<?> addTypeArticleToFamily(@RequestBody TypeArticle typeArticle) {
        try {
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

    @GetMapping("/types")
    public ResponseEntity<?> getTypeArticlePage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "2") int size
    ){
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<TypeArticle> typeArticlePage= parametrageService.getTypeArticlePage(paging);
            Map<String, Object> response = new HashMap<>();
            response.put("typeArticle", typeArticlePage.getContent());
            response.put("currentPage", typeArticlePage.getNumber());
            response.put("totalItems", typeArticlePage.getTotalElements());
            response.put("totalPages", typeArticlePage.getTotalPages());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping("/type/{id}")
    public ResponseEntity<?> getTypeById(@PathVariable Long id) {
        try {
            if (id == null) throw new BadRequestException("id required");
            TypeArticle typeArticle = parametrageService.getTypeById(id);
            if (typeArticle == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(typeArticle);
            }
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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


    @GetMapping("/categorieFournisseur")
    public ResponseEntity<?> getcategorieFournisseurPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "2") int size
    ){
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<CategorieFournisseur> categorieFournisseurPage= parametrageService.getCategorieFournisseurPage(paging);
            Map<String, Object> response = new HashMap<>();
            response.put("categorieFournisseur", categorieFournisseurPage.getContent());
            response.put("currentPage", categorieFournisseurPage.getNumber());
            response.put("totalItems", categorieFournisseurPage.getTotalElements());
            response.put("totalPages", categorieFournisseurPage.getTotalPages());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw e;
        }
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

    @GetMapping("/categorieFournisseur/{id}")
    public ResponseEntity<?> getCategorieFournisseurById(@PathVariable Long id) {
        try {
            if (id == null) throw new BadRequestException("id required");
            CategorieFournisseur categorieFournisseur = parametrageService.getCategorieFournisseurById(id);
            if (categorieFournisseur == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(categorieFournisseur);
            }
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
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

    @GetMapping("/typeFournisseur")
    public ResponseEntity<?> getTypeFournisseurPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "2") int size
    ){
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<TypeFournisseur> typeFournisseurPage= parametrageService.getTypeFournisseurPage(paging);
            Map<String, Object> response = new HashMap<>();
            response.put("typeFournisseur", typeFournisseurPage.getContent());
            response.put("currentPage", typeFournisseurPage.getNumber());
            response.put("totalItems", typeFournisseurPage.getTotalElements());
            response.put("totalPages", typeFournisseurPage.getTotalPages());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            throw e;
        }
    }

    @GetMapping("/typeFournisseur/{id}")
    public ResponseEntity<?> getTypeFournisseurById(@PathVariable Long id) {
        try {
            if (id == null) throw new BadRequestException("id required");
            TypeFournisseur typeFournisseur = parametrageService.getTypeFournisseurById(id);
            if (typeFournisseur == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            } else {
                return ResponseEntity.ok(typeFournisseur);
            }
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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
