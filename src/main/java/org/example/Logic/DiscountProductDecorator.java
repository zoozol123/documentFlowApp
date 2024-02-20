package org.example.Logic;

public class DiscountProductDecorator extends ProductDecorator {
    private double price;
    public DiscountProductDecorator(IProduct product, double discount) {
        super(product);
        
        this.price = addDiscount(this.product.getPrice(), discount);
    }

    public double addDiscount(double price, double discount) {
        double discountedPrice = price * (1 - discount / 100);
        return Math.round(discountedPrice * 100.0) / 100.0;
    }

    @Override
    public double getPrice() {
        return this.price;
    }
}