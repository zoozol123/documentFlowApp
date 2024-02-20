package org.example.Logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class TxtInvoice extends Invoice {
    public TxtInvoice(Order order, LocalDateTime issueDate) throws IOException {
        super(order, issueDate);
    }

    protected void createMessage() throws IOException {
        content = "Faktura z " + issueDate + "\n";
        content += "Klient: " + order.getUserId() + "\n";
        content += "Zamówienie: " + order.getId() + "\n";
        content += "Faktura dotyczy produktów: \n";
        for(int i = 0; i < order.getProducts().size(); i++) {
            content += order.getProducts().get(i).getName() + ", cena: ";
            content += order.getProducts().get(i).getPrice() + ", ilość: ";
            content += order.getProducts().get(i).getQuantity() + ", stawka VAT: ";
            content += order.getProducts().get(i).getVatRate() + "\n";
        }
    }

    protected void addToFile() {
        String filePath = "D:\\Pobrane\\faktura.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
