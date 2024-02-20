package org.example.Logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WarehouseManager {
    private static WarehouseManager instance;
    private static Warehouse warehouse;

    private WarehouseManager() {
        this.warehouse = new Warehouse();
    }

    public static WarehouseManager getInstance() {
        if(instance ==  null) {
            instance = new WarehouseManager();
        }
        return instance;
    }

    public void addProduct(IProduct product) throws IOException {
        warehouse.addProduct(product);
    }

    public void deleteProduct(String productName) {
        warehouse.getProducts().remove(productName);
    }

    public void addCategory(Category category) {
        warehouse.getCategories().add(category);
    }

    public void deleteCategory(Category category) {
        warehouse.getCategories().remove(category);
    }

    public void supply(String productName, IProduct product) {
        warehouse.getProducts().put(productName, product);
    }

    public ArrayList<Order> getOrders() {
        return warehouse.getOrders();
    }

    public void payForOrder(int idx) {
        Order order = (Order) warehouse.getOrders().get(idx);
        order.setPaid(true);
    }

    public void subscribe(User user) {
        warehouse.subscribe(user);
    }

    public void unsubscribe(User user) {
        warehouse.unsubscribe(user);
    }

    public void placeOrder(Order order) {
        warehouse.placeOrder(order);
    }

    public String[] getCategoriesAsStringArray() {
        String[] categoriesStrings = new String[warehouse.getCategories().size()];
        for(int i = 0; i < warehouse.getCategories().size(); i++) {
            Category category = (Category) warehouse.getCategories().get(i);
            categoriesStrings[i] = category.name;
        }
        return categoriesStrings;
    }

    public void setDiscount(String name, IProduct product) {
        warehouse.getProducts().put(name, product);
    }


    public String[] getProductsAsStringArray() {
        Map<String, IProduct> productMap = warehouse.getProducts();
        String[] productList = productMap.keySet().toArray(new String[0]);

        return productList;
    }
    public String[] getOrdersAsStringArray() {
        String[] orderIDs = new String[warehouse.getOrders().size()];
        for (int i = 0; i < warehouse.getOrders().size(); i++) {
            Order tempOrder = (Order) warehouse.getOrders().get(i);
            orderIDs[i] = "Zamówienie" + String.valueOf(tempOrder.getId());
        }

        return orderIDs;
    }

    public String[] getPaidOrdersAsStringArray() {
        ArrayList<Order> tempPaidOrders = new ArrayList<>();

        for (int i = 0; i < warehouse.getOrders().size(); i++) {
            Order tempOrder = (Order) warehouse.getOrders().get(i);
            if(tempOrder.getPaid())
                tempPaidOrders.add(tempOrder);
        }
        String[] paidOrderIDs = new String[tempPaidOrders.size()];
        for(int i = 0; i < tempPaidOrders.size(); i++) {
            paidOrderIDs[i] = "Opłacone zamówienie: " + tempPaidOrders.get(i).getId();
        }

        return paidOrderIDs;
    }

    public Map<String, IProduct> getProducts() {
        return warehouse.getProducts();
    }

    public ArrayList<Category> getCategories() {
        return warehouse.getCategories();
    }
}
