package com.example.construction.services;

import com.example.construction.models.Devis;
import com.example.construction.models.LigneDevis;
import com.example.construction.models.Tache;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;


import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
public class PdfService {
    public void generateDevisPdf(Devis devis, OutputStream outputStream) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

            // 🔶 En-tête avec Logo
//            String logoPath = ""; // Chemin de ton logo
//            PDImageXObject logo = PDImageXObject.createFromFile(logoPath, document);
//            contentStream.drawImage(logo, 50, 700, 100, 50);

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.setNonStrokingColor(Color.ORANGE);
            contentStream.beginText();
            contentStream.newLineAtOffset(200, 750);
            contentStream.showText("DEVIS N° " + devis.getId());
            contentStream.endText();

            // 🏢 Informations de l'entreprise
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 680);
            contentStream.showText("Entreprise De construction");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Dakar plateau");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Tel: 33 876 59 89  - Email: contact@construction.fr");
            contentStream.endText();

            // 👤 Informations du client
            contentStream.beginText();
            contentStream.newLineAtOffset(400, 680);
            contentStream.showText("Adresse du Client");
            contentStream.newLineAtOffset(0, -15);
            //contentStream.showText(devis.getClient().getNom());
            contentStream.newLineAtOffset(0, -15);
            //contentStream.showText(devis.getClient().getAdresse());
            contentStream.endText();

            // 📝 Tableau des travaux
            float startY = 600;
            contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
            contentStream.addRect(50, startY - 20, 500, 20);
            contentStream.fill();

            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(55, startY - 15);
            contentStream.showText("Désignation des articles");
            contentStream.newLineAtOffset(200, 0);
            contentStream.showText("Qté");
            contentStream.newLineAtOffset(50, 0);
            contentStream.showText("Coût Unitaire");
            contentStream.newLineAtOffset(100, 0);
            contentStream.showText("Total HT");
            contentStream.endText();

            // 📄 Lignes du devis
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            float lineY = startY - 40;
            for (LigneDevis ligne : devis.getLignesDevis()) {
                contentStream.beginText();
                contentStream.newLineAtOffset(55, lineY);
                contentStream.showText(ligne.getArticle().getDesignation());
                contentStream.newLineAtOffset(200, 0);
                contentStream.showText(String.valueOf(ligne.getQuantite()));
                contentStream.newLineAtOffset(50, 0);
                contentStream.showText(String.format("%.2f FCFA", ligne.getArticle().getPrixDevis()));
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText(String.format("%.2f FCFA", ligne.getQuantite() * ligne.getArticle().getPrixDevis()));
                contentStream.endText();
                lineY -= 15;
            }

            // 💰 Totaux
            contentStream.setNonStrokingColor(Color.ORANGE);
            contentStream.addRect(400, 100, 150, 50);
            contentStream.fill();

            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(410, 130);
            contentStream.showText("TOTAL HT : " + String.format("%.2f FCFA", devis.getTotal()));
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(410, 110);
            contentStream.showText("TVA 10% : " + String.format("%.2f FCFA", devis.getTotal() * 0.1));
            contentStream.endText();

        }

        document.save(outputStream);
        document.close();
    }
    public void generatePlanningPdf(List<Tache> taches, OutputStream outputStream) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.setNonStrokingColor(Color.BLUE);
            contentStream.beginText();
            contentStream.newLineAtOffset(200, 750);
            contentStream.showText("PLANNING - Calendrier de Construction");
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 680);
            contentStream.showText("Entreprise De construction");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Dakar plateau");
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Tel: 33 876 59 89  - Email: contact@construction.fr");
            contentStream.endText();

            LocalDate startDate = taches.stream()
                    .filter(t -> t.getDateDebut() != null)
                    .map(Tache::getDateDebut)
                    .min(LocalDate::compareTo)
                    .orElse(LocalDate.now());
            LocalDate endDate = taches.stream()
                    .filter(t -> t.getDateFin() != null)
                    .map(Tache::getDateFin)
                    .max(LocalDate::compareTo)
                    .orElse(startDate.plusDays(30));

            long daysBetween = endDate.toEpochDay() - startDate.toEpochDay() + 1;
            float totalWidth = 450;
            float dayWidth = totalWidth / daysBetween;
            float rowHeight = 20;
            float startX = 100;
            float startY = 600;
            DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.FRENCH);
            String monthYear = startDate.format(monthFormatter);            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.beginText();
            contentStream.newLineAtOffset(startX, startY + 20);
            contentStream.showText(monthYear);
            contentStream.endText();
            DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEEE", Locale.FRENCH);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 8);
            contentStream.setNonStrokingColor(Color.BLACK);
            float x = startX;
            for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                String dayOfWeek = date.format(dayOfWeekFormatter).toUpperCase();                contentStream.beginText();
                contentStream.newLineAtOffset(x + 2, startY);
                contentStream.showText(dayOfWeek);
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(x + 2, startY - 15);
                contentStream.showText(String.valueOf(date.getDayOfMonth()));
                contentStream.endText();

                contentStream.setStrokingColor(Color.LIGHT_GRAY);
                contentStream.addRect(x, startY - rowHeight - 10, dayWidth, rowHeight);
                contentStream.stroke();

                x += dayWidth;
            }

            float y = startY - (2 * rowHeight) - 10;
            int taskIndex = 0;
            for (Tache tache : taches) {
                if (tache.getDateDebut() == null || tache.getDateFin() == null) {
                    continue;
                }

                contentStream.setFont(PDType1Font.HELVETICA, 8);
                contentStream.setNonStrokingColor(Color.BLACK);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, y - 5);
                contentStream.showText(tache.getNom() != null ? tache.getNom() : "Tâche sans nom");
                contentStream.endText();

                long daysFromStart = tache.getDateDebut().toEpochDay() - startDate.toEpochDay();
                long taskDuration = tache.getDateFin().toEpochDay() - tache.getDateDebut().toEpochDay() + 1;
                float barX = startX + (daysFromStart * dayWidth);
                float barWidth = taskDuration * dayWidth;

                contentStream.setNonStrokingColor(getColorForTask(taskIndex));
                contentStream.addRect(barX, y - rowHeight + 5, barWidth, rowHeight - 10);
                contentStream.fill();

                x = startX;
                for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                    contentStream.setStrokingColor(Color.LIGHT_GRAY);
                    contentStream.addRect(x, y - rowHeight, dayWidth, rowHeight);
                    contentStream.stroke();
                    x += dayWidth;
                }

                y -= rowHeight;
                taskIndex++;
            }
        }

        document.save(outputStream);
        document.close();
    }

    private Color getColorForTask(int taskIndex) {
        Color[] colors = {
                new Color(255, 105, 180),
                new Color(135, 206, 250),
                new Color(124, 252, 0),
                new Color(255, 165, 0),
                new Color(147, 112, 219),
                new Color(255, 69, 0),
                new Color(64, 224, 208)
        };
        return colors[taskIndex % colors.length];
    }
}
