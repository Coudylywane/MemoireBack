package com.example.construction.controllers;

import com.example.construction.models.Devis;
import com.example.construction.models.LigneDevis;
import com.example.construction.models.enumeration.DevisStatus;
import com.example.construction.repositories.DevisRepository;
import com.example.construction.services.DevisService;
import com.example.construction.services.PdfService;
import com.example.construction.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/devis")
public class DevisController {

    private final DevisService devisService;
    private final PdfService pdfService;
    private final DevisRepository devisRepository ;
    private final ProjectService projetService ;

    @PostMapping
    public ResponseEntity<Devis> creerDevis(@RequestBody Devis devis, @RequestParam Long projetId) {
        Devis nouveauDevis = devisService.creerDevis(devis, projetId);
        return ResponseEntity.ok(nouveauDevis);
    }

    @GetMapping
    public List<Devis> obtenirTousLesDevis() {
        return devisService.obtenirTousLesDevis();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Devis> obtenirDevisParId(@PathVariable Long id) {
        return devisService.obtenirDevisParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/pdf")
    public void downloadDevisPdf(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Devis devis = devisService.obtenirDevisParId(id)
                .orElseThrow(() -> new RuntimeException("Devis non trouvé"));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=devis_" + id + ".pdf");

        pdfService.generateDevisPdf(devis, response.getOutputStream());
    }

    // Valider un devis
    @PutMapping("/{id}/valider")
    public ResponseEntity<Devis> validerDevis(@PathVariable Long id) {
        Devis devis = devisService.mettreAJourStatut(id, DevisStatus.VALIDER);
        return ResponseEntity.ok(devis);
    }

    // Refuser un devis
    @PutMapping("/{id}/refuser")
    public ResponseEntity<Devis> refuserDevis(@PathVariable Long id) {
        Devis devis = devisService.mettreAJourStatut(id, DevisStatus.REFUSER);
        return ResponseEntity.ok(devis);
    }

    // Marquer un devis comme "À négocier"
    @PutMapping("/{id}/a-negocier")
    public ResponseEntity<Devis> marquerANegocier(@PathVariable Long id) {
        Devis devis = devisService.mettreAJourStatut(id, DevisStatus.À_NÉGOCIER);
        return ResponseEntity.ok(devis);
    }

    @PutMapping("/{id}/valider2")
    public ResponseEntity<Devis> validerDevis2(@PathVariable Long id) {
        Devis devis = devisService.validerDevis(id);
        return ResponseEntity.ok(devis);
    }

    @PutMapping("/{devisId}/prix-reels")
    public ResponseEntity<Devis> mettreAJourPrixReels(
            @PathVariable Long devisId,
            @RequestBody Map<Long, Double> prixReels) { // Map<articleId, nouveauPrixReel>
        Devis devis = devisRepository.findById(devisId)
                .orElseThrow(() -> new RuntimeException("Devis non trouvé"));

        // Vérifier si toutes les tâches du projet sont terminées
        if (!projetService.toutesTachesTerminees(devis.getProjet().getId())) {
            throw new RuntimeException("Toutes les tâches du projet doivent être terminées pour modifier les prix réels.");
        }

        // Mettre à jour les prix réels des articles
        for (LigneDevis ligne : devis.getLignesDevis()) {
            if (prixReels.containsKey(ligne.getArticle().getId())) {
                ligne.getArticle().setPrixReel(prixReels.get(ligne.getArticle().getId()));
            }
        }

        Devis devisMisAJour = devisRepository.save(devis);
        return ResponseEntity.ok(devisMisAJour);
    }

    @GetMapping("/{devisId}/differences-prix")
    public ResponseEntity<Map<Long, Double>> getDifferencesPrix(@PathVariable Long devisId) {
        Devis devis = devisRepository.findById(devisId)
                .orElseThrow(() -> new RuntimeException("Devis non trouvé"));

        Map<Long, Double> differences = devis.calculerDifferencesPrix();
        return ResponseEntity.ok(differences);
    }
}
