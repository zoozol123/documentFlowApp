package org.example.Logic;

public class Product implements IProduct {
    private String name;
    private double price;
    private double vatRate;
    private int quantity;
    private Category category;
    public Product(String name, double price, double vatRate, int quantity, Category category) {
        this.vatRate = vatRate;
        this.quantity = quantity;
        this.category = category;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public double getVatRate() {
        return this.vatRate;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public Category getCategory() {
        return this.category;
    }

    @Override
    public IProduct clone() {
        try {
            return (IProduct) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setQuantity(int newQuantity) { this.quantity = newQuantity; }
}
