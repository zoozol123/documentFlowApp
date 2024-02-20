package org.example.Logic;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfInvoice extends Invoice{
    public PdfInvoice(Order order, LocalDateTime issueDate) {
        super(order, issueDate);
    }

    protected void createMessage() throws IOException {
        content = "Faktura z " + issueDate + "\n";
        content += "Klient: " + order.getUserId() + "\n";
        content += "Zamówienie: " + order.getId() + "\n";
        content += "Faktura dotyczy produktów: \n";
        for(int i = 0; i < order.getProducts().size(); i++) {
            content += order.getProducts().get(i).getName() + ", cena: ";
            content += order.getProducts().get(i).getPrice() + ", ilosc: ";
            content += order.getProducts().get(i).getQuantity() + ", stawka VAT: ";
            content += order.getProducts().get(i).getVatRate() + "\n";
        }
    }

    protected void addToFile() throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("D:\\Pobrane\\faktura.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph(content, font);

        document.add(paragraph);
        document.close();
    }
}
