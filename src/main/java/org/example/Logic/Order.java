package org.example.Logic;

import java.util.ArrayList;

public class Order {
    private int id, userId;
    private ArrayList<IProduct> products;
    private boolean paid;

    public Order(int id, int userId, ArrayList<IProduct> products, boolean paid) {
        this.id = id;
        this.userId = userId;
        this.products = products;
        this.paid = false;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getTotalOrderPrice() {
        double sum = 0.0;
        for(int i = 0; i < products.size(); i++) {
            sum += products.get(i).getPrice() * products.get(i).getQuantity();
        }
        return sum;
    }

    public boolean getPaid() {
        return paid;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList<IProduct> getProducts() {
        return products;
    }
}
