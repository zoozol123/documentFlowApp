package org.example.Gui;

import com.sun.jdi.IntegerValue;
import org.example.Logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserPanel extends JPanel implements ActionListener {
    JButton placeOrderButton;
    JButton payInvoiceButton;
    JButton followCategoryButton, stopFollowingCategoryButton;
    JButton logoutButton;
    JLabel alert;
    WarehouseManager manager;
    User user;
    double summary = 0.0;
    ArrayList<IProduct> orderedProducts = new ArrayList<>();
    static int orderID = 1;

    public UserPanel(User user) {
        alert = new JLabel("Dodano nowy produkt z obserwowanej kategorii!");
        alert.setBounds(224, 430, 400, 50);
        alert.setFont(new Font("Arial", Font.BOLD, 16));
        alert.setForeground(Color.white);
        alert.setVisible(false);

        placeOrderButton = new JButton();
        placeOrderButton.setBounds(250, 75, 300, 50);
        placeOrderButton.addActionListener(this);
        placeOrderButton.setText("Złóż zamówienie");
        placeOrderButton.setFocusable(false);

        payInvoiceButton = new JButton();
        payInvoiceButton.setBounds(250, 150, 300, 50);
        payInvoiceButton.addActionListener(this);
        payInvoiceButton.setText("Zapłać fakturę");
        payInvoiceButton.setFocusable(false);

        followCategoryButton = new JButton();
        followCategoryButton.setBounds(250, 225, 300, 50);
        followCategoryButton.addActionListener(this);
        followCategoryButton.setText("Obserwuj kategorię");
        followCategoryButton.setFocusable(false);

        stopFollowingCategoryButton = new JButton();
        stopFollowingCategoryButton.setBounds(250, 300, 300, 50);
        stopFollowingCategoryButton.addActionListener(this);
        stopFollowingCategoryButton.setText("Przestań obserwować kategorię");
        stopFollowingCategoryButton.setFocusable(false);

        logoutButton = new JButton();
        logoutButton.setBounds(250, 375, 300, 50);
        logoutButton.addActionListener(this);
        logoutButton.setText("Wyloguj");
        logoutButton.setFocusable(false);


        this.user = user;
        this.setBackground(Color.BLUE);
        this.setBounds(0, 0, 800, 600);
        this.setVisible(false);
        this.setLayout(null);
        this.add(alert);
        this.add(placeOrderButton);
        this.add(payInvoiceButton);
        this.add(followCategoryButton);
        this.add(stopFollowingCategoryButton);
        this.add(logoutButton);
    }

    public void clear() {
        placeOrderButton.setVisible(false);
        payInvoiceButton.setVisible(false);
        followCategoryButton.setVisible(false);
        stopFollowingCategoryButton.setVisible(false);
        logoutButton.setVisible(false);
        alert.setVisible(false);
    }

    public void showButtons() {
        placeOrderButton.setVisible(true);
        payInvoiceButton.setVisible(true);
        followCategoryButton.setVisible(true);
        stopFollowingCategoryButton.setVisible(true);
        logoutButton.setVisible(true);
    }

    public JLabel createTitleLabel(String titleString) {
        JLabel title = new JLabel(titleString);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBounds(250, 20, 300, 40);
        title.setForeground(Color.WHITE);

        return title;
    }

    public void backButtonActionListenerAdder(JButton button, JLabel title, JPanel panel) {
        button.setBounds(250, 320, 300, 40);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                title.setVisible(false);
                panel.setVisible(false);
                button.setVisible(false);
                showButtons();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        manager = WarehouseManager.getInstance();
        if(e.getSource() == placeOrderButton) {
            clear();
            JLabel title = createTitleLabel("Złóż zamówienie");

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 2));
            panel.setBounds(250, 200, 300, 100);

            JLabel chooseProductLabel = new JLabel("Wybierz produkt z listy:");
            String[] products = manager.getProductsAsStringArray();
            JComboBox<String> productComboBox = new JComboBox<>(products);
            productComboBox.setSelectedIndex(0);

            JLabel quantityLabel = new JLabel("Podaj ilość: ");
            JTextField quantityTextField = new JTextField();

            JLabel finalPriceLabel = new JLabel("Suma: ");
            JLabel sumLabel = new JLabel("0.0");

            JLabel empty1 = new JLabel("");
            JButton addToOrderButton = new JButton("Dodaj");
            addToOrderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = (String) productComboBox.getSelectedItem();
                    int quantity = Integer.parseInt(quantityTextField.getText());
                    IProduct product = manager.getProducts().get(name);
                    Double price = product.getPrice();
                    summary += price * quantity;

                    quantityTextField.setText("");
                    sumLabel.setText(Double.toString(summary));

                    IProduct orderedProduct = manager.getProducts().get(name);
                    int oldQuantity = manager.getProducts().get(name).getQuantity();
                    manager.getProducts().get(name).setQuantity(oldQuantity - quantity);
                    orderedProduct.setQuantity(quantity);
                    orderedProducts.add(orderedProduct);
                }
            });

            JLabel empty = new JLabel("");
            JButton orderButton = new JButton("Zamów");
            orderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    manager.placeOrder( new Order(orderID, user.getId(), orderedProducts, false) );

                    JOptionPane.showMessageDialog(UserPanel.this, "Pomyślnie złożono zamówienie: " + orderID);
                    quantityTextField.setText("");
                    sumLabel.setText("0.0");
                    summary = 0;
                    orderedProducts = new ArrayList<>();
                    orderID++;
                }
            });

            JButton backButton = new JButton("Powrót");
            backButtonActionListenerAdder(backButton, title, panel);

            panel.add(chooseProductLabel);
            panel.add(productComboBox);
            panel.add(quantityLabel);
            panel.add(quantityTextField);
            panel.add(empty1);
            panel.add(addToOrderButton);
            panel.add(finalPriceLabel);
            panel.add(sumLabel);
            panel.add(empty);
            panel.add(orderButton);

            this.add(title);
            this.add(panel);
            this.add(backButton);
            this.setVisible(true);
        }
        if(e.getSource() == payInvoiceButton) {
            clear();
            JLabel title = createTitleLabel("Opłać fakturę");

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));
            panel.setBounds(250, 200, 300, 100);

            JLabel chooseInvoiceLabel = new JLabel("Wybierz fakturę z listy:");
            String[] invoices = manager.getOrdersAsStringArray();
            JComboBox<String> invoiceComboBox = new JComboBox<>(invoices);
            invoiceComboBox.setSelectedIndex(0);

            JLabel totalPriceLabel = new JLabel("Całkowita kwota faktury");
            int idx = invoiceComboBox.getSelectedIndex();
            JLabel totalPriceNumberLabel = new JLabel(String.valueOf(manager.getOrders().get(idx).getTotalOrderPrice()));

            JLabel empty = new JLabel("");
            JButton payButton = new JButton("Zapłać");
            payButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int idx = invoiceComboBox.getSelectedIndex();
                    manager.payForOrder(idx);

                    JOptionPane.showMessageDialog(UserPanel.this, "Zapłacono fakturę.");
                }
            });

            JButton backButton = new JButton("Powrót");
            backButtonActionListenerAdder(backButton, title, panel);

            panel.add(chooseInvoiceLabel);
            panel.add(invoiceComboBox);
            panel.add(totalPriceLabel);
            panel.add(totalPriceNumberLabel);
            panel.add(empty);
            panel.add(payButton);

            this.add(title);
            this.add(panel);
            this.add(backButton);
            this.setVisible(true);
        }
        if(e.getSource() == followCategoryButton) {
            clear();
            JLabel title = createTitleLabel("Obserwuj kategorię");

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 2));
            panel.setBounds(250, 200, 300, 100);

            JLabel chooseCategoryLabel = new JLabel("Wybierz kategorię z listy:");
            String[] categories = manager.getCategoriesAsStringArray();
            JComboBox<String> categoriesComboBox = new JComboBox<>(categories);
            categoriesComboBox.setSelectedIndex(0);

            JLabel empty = new JLabel("");
            JButton followButton = new JButton("Obserwuj");
            followButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int idx = categoriesComboBox.getSelectedIndex();
                    Category userCategory = (Category) manager.getCategories().get(idx);
                    user.setObservedCategory(userCategory);

                    manager.subscribe(user);
                    JOptionPane.showMessageDialog(UserPanel.this, "Dodano kategorię: " + userCategory.getCategoryName() + " do obserwowanych");
                }
            });

            JButton backButton = new JButton("Powrót");
            backButtonActionListenerAdder(backButton, title, panel);

            panel.add(chooseCategoryLabel);
            panel.add(categoriesComboBox);
            panel.add(empty);
            panel.add(followButton);

            this.add(title);
            this.add(panel);
            this.add(backButton);
            this.setVisible(true);
        }
        if(e.getSource() == stopFollowingCategoryButton) {
            clear();
            JLabel title = createTitleLabel("Przestań obserwować kategorię");

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 2));
            panel.setBounds(250, 200, 300, 100);

            JLabel chooseCategoryLabel = new JLabel("Wybierz kategorię z listy:");
            String[] categories = manager.getCategoriesAsStringArray();
            JComboBox<String> categoriesComboBox = new JComboBox<>(categories);
            categoriesComboBox.setSelectedIndex(0);

            JLabel empty = new JLabel("");
            JButton followButton = new JButton("Anuluj obserwację");
            followButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int idx = categoriesComboBox.getSelectedIndex();
                    user.setObservedCategory(null);

                    manager.unsubscribe(user);
                    JOptionPane.showMessageDialog(UserPanel.this, "Usunięto kategorię z obserwowanych");
                    alert.setVisible(false);
                }
            });

            JButton backButton = new JButton("Powrót");
            backButtonActionListenerAdder(backButton, title, panel);

            panel.add(chooseCategoryLabel);
            panel.add(categoriesComboBox);
            panel.add(empty);
            panel.add(followButton);

            this.add(title);
            this.add(panel);
            this.add(backButton);
            this.setVisible(true);
        }
        if(e.getSource() == logoutButton) {
            this.setVisible(false);
        }
    }

    public void setDisplayNewProductAlert() {
        if(user.getAlert()) {
            alert.setVisible(true);
        }
    }
}