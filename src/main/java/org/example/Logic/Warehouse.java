package org.example.Logic;

import org.example.Logic.Product;

import java.io.IOException;
import java.util.*;

public class Warehouse<T> {
    private static Map<String, IProduct> products;
    private static ArrayList<Category> categories;
    private List<CategoryObserver> subsribers;
    private static ArrayList<Order> orders;

    public Warehouse() {
        products = new HashMap<>();
        categories = new ArrayList<>();
        subsribers = new ArrayList<>();
        orders = new ArrayList<>();
    }
    public void subscribe(CategoryObserver observer) {
        subsribers.add(observer);
    }

    public void unsubscribe(CategoryObserver observer) {
        subsribers.remove(observer);
    }

    public void addProduct(IProduct product) throws IOException {
        products.put(product.getName(), product);
        notifyObservers(product);
    }

    private void notifyObservers(IProduct product) {
        for (CategoryObserver subscriber : subsribers) {
            if( Objects.equals(subscriber.getObservedCategory().name, product.getCategory().name) )
                subscriber.onElementAdded(product);
        }
    }

    public void placeOrder(Order order) {
        orders.add(order);
    }

    public Map<String, IProduct> getProducts() {
        return products;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<Order> getOrders() { return orders; }
}
