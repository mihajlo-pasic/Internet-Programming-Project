package servlets;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;


import dao.RentalDAO;
import dto.Iznajmljivanje;

@WebServlet("/InvoiceServlet")
public class InvoiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int iznajmljivanjeId = Integer.parseInt(request.getParameter("iznajmljivanjeId"));
        Iznajmljivanje iznajmljivanje = RentalDAO.getRentalById(iznajmljivanjeId);

        try {
            Document document = new Document();
            String filePath = getServletContext().getRealPath("/") + "racun_" + iznajmljivanjeId + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));

            document.open();
            document.add(new Paragraph("Račun za iznajmljivanje vozila"));
            document.add(new Paragraph("Identifikator vozila: " + iznajmljivanje.getIdentifikator()));
            document.add(new Paragraph("Datum početka: " + iznajmljivanje.getDatumPocetka()));
            document.add(new Paragraph("Datum završetka: " + iznajmljivanje.getDatumZavrsetka()));
            document.add(new Paragraph("Trajanje vožnje: " + iznajmljivanje.getTrajanje() + " sekundi"));
            document.add(new Paragraph("Ukupna cena: " + iznajmljivanje.getCijena() + " RSD"));
            document.close();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=racun_" + iznajmljivanjeId + ".pdf");
//            java.nio.file.Files.copy(java.nio.file.Paths.get(filePath), response.getOutputStream());
            if (new java.io.File(filePath).exists()) {
                java.nio.file.Files.copy(java.nio.file.Paths.get(filePath), response.getOutputStream());
            } else {
                System.out.println("Fajl nije pronađen: " + filePath);
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
