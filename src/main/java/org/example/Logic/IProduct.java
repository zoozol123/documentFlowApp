package org.example.Logic;

import java.io.IOException;

public interface IProduct extends Cloneable{
    String getName() throws IOException;
    double getPrice();
    double getVatRate();
    int getQuantity();
    Category getCategory();

    IProduct clone();

    void setQuantity(int newQuantity);
}