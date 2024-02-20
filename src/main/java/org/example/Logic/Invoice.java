package org.example.Logic;

import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

abstract class Invoice {
    Order order;
    double amount;
    LocalDateTime issueDate;
    String content ="";

    public Invoice(Order order, LocalDateTime issueDate) {
        this.order = order;
        this.amount = order.getTotalOrderPrice();
        this.issueDate = issueDate;
    }

    public void generateInvoice() throws DocumentException, IOException {
        templateMethod();
    }

    protected abstract void createMessage() throws IOException;
    protected abstract void addToFile() throws FileNotFoundException, DocumentException;

    public void templateMethod() throws DocumentException, IOException {
        createMessage();
        addToFile();
    }
}
