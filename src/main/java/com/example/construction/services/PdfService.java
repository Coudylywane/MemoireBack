package com.example.construction.services;

import com.example.construction.models.Devis;
import com.example.construction.models.LigneDevis;
import org.springframework.stereotype.Service;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class PdfService {
    public void generateDevisPdf(Devis devis, OutputStream outputStream) throws IOException, IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.beginText();
        contentStream.newLineAtOffset(100, 700);
        contentStream.showText("Devis pour " + devis.getClient());
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Date : " + devis.getDateCreation().toString());
        contentStream.newLineAtOffset(0, -20);

        for (LigneDevis ligne : devis.getLignesDevis()) {
            String lineText = ligne.getArticle().getDesignation() + " - " + ligne.getQuantite() + " x " + ligne.getArticle().getPrixDevis();
            contentStream.showText(lineText);
            contentStream.newLineAtOffset(0, -15);
        }

        contentStream.showText("Total : " + devis.getTotal());
        contentStream.endText();
        contentStream.close();

        document.save(outputStream);
        document.close();
    }
}
