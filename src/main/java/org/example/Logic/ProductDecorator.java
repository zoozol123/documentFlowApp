package org.example.Logic;

import java.io.IOException;

abstract class ProductDecorator implements IProduct {
    protected IProduct product;
    public ProductDecorator(IProduct product) {
        this.product = product;
    }

    public void setQuantity(int newQuantity) {
        this.product.setQuantity(newQuantity);
    }

    public String getName() throws IOException {
        return this.product.getName();
    }

    public double getPrice() {
        return this.product.getPrice();
    }

    public double getVatRate() {
        return this.product.getVatRate();
    }

    public int getQuantity() {
        return this.product.getQuantity();
    }

    public Category getCategory() {
        return this.product.getCategory();
    }

    @Override
    public IProduct clone() {
        return this.product.clone();
    }

}