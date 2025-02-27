package com.example.construction.services;

import com.example.construction.models.Devis;
import com.example.construction.models.LigneDevis;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

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
}
