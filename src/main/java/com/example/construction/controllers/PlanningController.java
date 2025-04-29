package com.example.construction.controllers;

import com.example.construction.models.Planning;
import com.example.construction.models.Tache;
import com.example.construction.models.enumeration.TaskStatus;
import com.example.construction.services.PdfService;
import com.example.construction.services.PlanningService;
import com.example.construction.services.TacheService;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/planning")
public class PlanningController {
    private final PlanningService planningService;
    private final PdfService pdfService;

    @PostMapping("/generate/{devisId}")
    public ResponseEntity<Planning> generatePlanning(@PathVariable Long devisId, @RequestBody List<Tache> taches) {
        Planning planning = planningService.createPlanning(devisId, taches);
        return ResponseEntity.ok(planning);
    }

    @GetMapping("/devis/{devisId}/{status}")
    public List<Tache> getTachesByDevisId(@PathVariable Long devisId , TaskStatus status) {
        return planningService.getTachesByDevisId(devisId, status);
    }

    @GetMapping("/devis/{devisId}/pdf")
    public ResponseEntity<InputStreamResource> downloadTachesPdf(@PathVariable Long devisId,TaskStatus status) {
        List<Tache> taches = planningService.getTachesByDevisId(devisId, status);

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            pdfService.generatePlanningPdf(taches, out); // Appeler PdfService

            ByteArrayInputStream bis = new ByteArrayInputStream(out.toByteArray());
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=planning_devis_" + devisId + ".pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(new InputStreamResource(bis));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }



}