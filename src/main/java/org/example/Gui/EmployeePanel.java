package org.example.Gui;

import com.itextpdf.text.DocumentException;
import org.example.Logic.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class EmployeePanel extends JPanel implements ActionListener {
    JButton addProductButton, generateInvoiceButton, deliveryButton;
    JButton addDiscountButton, deleteProductButton, logoutButton;
    String quantity;
    double summary = 0.0;
    WarehouseManager manager;

    public EmployeePanel() {
        deliveryButton = new JButton();
        deliveryButton.setBounds(250, 30, 300, 50);
        deliveryButton.addActionListener(this);
        deliveryButton.setText("Dodaj towar (dostawa)");
        deliveryButton.setFocusable(false);

        addProductButton = new JButton();
        addProductButton.setBounds(250, 100, 300, 50);
        addProductButton.addActionListener(this);
        addProductButton.setText("Dodaj nowy produkt");
        addProductButton.setFocusable(false);

        deleteProductButton = new JButton();
        deleteProductButton.setBounds(250, 170, 300, 50);
        deleteProductButton.addActionListener(this);
        deleteProductButton.setText("Usuń produkt");
        deleteProductButton.setFocusable(false);

        generateInvoiceButton = new JButton();
        generateInvoiceButton.setBounds(250, 240, 300, 50);
        generateInvoiceButton.addActionListener(this);
        generateInvoiceButton.setText("Wystaw fakturę");
        generateInvoiceButton.setFocusable(false);

        addDiscountButton = new JButton();
        addDiscountButton.setBounds(250, 310, 300, 50);
        addDiscountButton.addActionListener(this);
        addDiscountButton.setText("Nałóż zniżkę na towar");
        addDiscountButton.setFocusable(false);

        logoutButton = new JButton();
        logoutButton.setBounds(250, 380, 300, 50);
        logoutButton.addActionListener(this);
        logoutButton.setText("Wyloguj");
        logoutButton.setFocusable(false);

        this.setBackground(Color.BLUE);
        this.setBounds(0, 0, 800, 600);
        this.setVisible(false);
        this.setLayout(null);
        this.add(deliveryButton);
        this.add(addProductButton);
        this.add(deleteProductButton);
        this.add(generateInvoiceButton);
        this.add(addDiscountButton);
        this.add(logoutButton);

        this.manager = WarehouseManager.getInstance();
        manager.addCategory(new Category("elektronika"));
        manager.addCategory(new Category("spożywcze"));
        manager.addCategory(new Category("sportowe"));
    }

    public void clear() {
        deliveryButton.setVisible(false);
        addProductButton.setVisible(false);
        generateInvoiceButton.setVisible(false);
        addDiscountButton.setVisible(false);
        deleteProductButton.setVisible(false);
        logoutButton.setVisible(false);
    }

    public void showButtons() {
        deliveryButton.setVisible(true);
        addProductButton.setVisible(true);
        generateInvoiceButton.setVisible(true);
        addDiscountButton.setVisible(true);
        deleteProductButton.setVisible(true);
        logoutButton.setVisible(true);
    }

    public JLabel createTitleLabel(String titleString) {
        JLabel title = new JLabel(titleString);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setForeground(Color.WHITE);

        return title;
    }

    public void backButtonActionListenerAdder(JButton button, JLabel title, JPanel panel) {
        button.setBounds(250, 350, 300, 40);
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
        if(e.getSource() == deliveryButton) {
            clear();
            JLabel title = createTitleLabel("Uzupełnij stan magazynu");

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));
            panel.setBounds(250, 200, 300, 100);

            JLabel chooseProductLabel = new JLabel("Wybierz produkt z listy:");
            String[] products = manager.getProductsAsStringArray();
            JComboBox<String> productComboBox = new JComboBox<>(products);
            productComboBox.setSelectedIndex(0);

            JLabel deliveryQuantityLabel = new JLabel("Podaj ilość: ");
            JTextField deliveryQuantityTextField = new JTextField();

            JLabel empty = new JLabel("");
            JButton addButton = new JButton("Uzupełnij");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedProduct = (String) productComboBox.getSelectedItem();
                    int deliveryQuantity = Integer.parseInt(deliveryQuantityTextField.getText());

                    IProduct newProduct = manager.getProducts().get(selectedProduct).clone();
                    int tempQuantity = newProduct.getQuantity();

                    newProduct.setQuantity(deliveryQuantity + tempQuantity);
                    manager.supply(selectedProduct, newProduct);

                    JOptionPane.showMessageDialog(EmployeePanel.this, "Pomyślnie uzupełniono stan magazynu");
                    deliveryQuantityTextField.setText("");
                }
            });

            JButton backButton = new JButton("Powrót");
            backButtonActionListenerAdder(backButton, title, panel);

            panel.add(chooseProductLabel);
            panel.add(productComboBox);
            panel.add(deliveryQuantityLabel);
            panel.add(deliveryQuantityTextField);
            panel.add(empty);
            panel.add(addButton);

            this.add(title);
            this.add(panel);
            this.add(backButton);
            this.setVisible(true);
        }
        if(e.getSource() == addProductButton) {
            clear();
            JLabel title = createTitleLabel("Dodaj produkt");

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(7, 2));
            panel.setBounds(250, 200, 300, 150);

            JLabel nameLabel = new JLabel("Podaj nazwę: ");
            JTextField nameTextField = new JTextField();

            JLabel nameTranslationLabel = new JLabel("Czy tłumaczyć nazwę na ang?");
            String[] choices = {"Tak", "Nie"};
            JComboBox<String> choiceComboBox = new JComboBox<>(choices);
            choiceComboBox.setSelectedIndex(0);

            JLabel chooseCategoryLabel = new JLabel("Wybierz kategorię z listy:");
            String[] categories = manager.getCategoriesAsStringArray();
            JComboBox<String> categoriesComboBox = new JComboBox<>(categories);
            categoriesComboBox.setSelectedIndex(0);

            JLabel quantityLabel = new JLabel("Ilość");
            JTextField quantityTextField = new JTextField();

            JLabel priceLabel = new JLabel("Cena");
            JTextField priceTextField = new JTextField();

            JLabel vatRateLabel = new JLabel("Stawka VAT (%)");
            JTextField vatRateTextField = new JTextField();

            JButton backButton = new JButton("Powrót");
            backButtonActionListenerAdder(backButton, title, panel);

            JLabel empty = new JLabel("");
            JButton addButton = new JButton("Dodaj");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameTextField.getText();
                    String choice = (String) choiceComboBox.getSelectedItem();
                    String categoryName = (String) categoriesComboBox.getSelectedItem();
                    int quantity = Integer.parseInt(quantityTextField.getText());
                    double price = Double.parseDouble(priceTextField.getText());
                    double vatRate = Double.parseDouble(vatRateTextField.getText());
                    IProduct product = null;
                    if(Objects.equals(choice, "Tak")) {
                        try {
                            product = new NameTranslationProductDecorator(new Product(name, price, vatRate, quantity, new Category(categoryName)), name);
                            manager.addProduct(product);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if(Objects.equals(choice, "Nie")) {
                        product = new Product(name, price, vatRate, quantity, new Category(categoryName));
                        try {
                            manager.addProduct(product);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    JOptionPane.showMessageDialog(EmployeePanel.this, "Pomyślnie dodano nowy produkt");
                }
            });

            panel.add(nameLabel);
            panel.add(nameTextField);
            panel.add(nameTranslationLabel);
            panel.add(choiceComboBox);
            panel.add(chooseCategoryLabel);
            panel.add(categoriesComboBox);
            panel.add(quantityLabel);
            panel.add(quantityTextField);
            panel.add(priceLabel);
            panel.add(priceTextField);
            panel.add(vatRateLabel);
            panel.add(vatRateTextField);
            panel.add(empty);
            panel.add(addButton);

            this.add(title);
            this.add(panel);
            this.add(backButton);
            this.setVisible(true);
        }
        if(e.getSource() == deleteProductButton) {
            clear();
            JLabel title = createTitleLabel("Usuń produkt");

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(2, 2));
            panel.setBounds(250, 200, 300, 100);

            JLabel chooseProductLabel = new JLabel("Wybierz produkt z listy:");
            String[] products = manager.getProductsAsStringArray();
            JComboBox<String> productComboBox = new JComboBox<>(products);
            productComboBox.setSelectedIndex(0);

            JLabel empty = new JLabel("");
            JButton generateButton = new JButton("Usuń");
            generateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    manager.deleteProduct( (String) productComboBox.getSelectedItem());
                    JOptionPane.showMessageDialog(EmployeePanel.this, "Pomyślnie usunięto produkt");
                }
            });

            JButton backButton = new JButton("Powrót");
            backButtonActionListenerAdder(backButton, title, panel);

            panel.add(chooseProductLabel);
            panel.add(productComboBox);
            panel.add(empty);
            panel.add(generateButton);

            this.add(title);
            this.add(panel);
            this.add(backButton);
            this.setVisible(true);
        }
        if(e.getSource() == generateInvoiceButton) {
            clear();
            JLabel title = createTitleLabel("Wystaw fakturę");

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));
            panel.setBounds(250, 200, 300, 100);

            JLabel chooseOrderLabel = new JLabel("Wybierz zamówienie z listy:");
            String[] orders = manager.getPaidOrdersAsStringArray();
            JComboBox<String> orderComboBox = new JComboBox<>(orders);
            orderComboBox.setSelectedIndex(0);

            JLabel chooseFormatLabel = new JLabel("Wybierz format faktury:");
            String[] formats = {".PDF", ".TXT"};
            JComboBox<String> chooseFormatComboBox = new JComboBox<>(formats);
            chooseFormatComboBox.setSelectedIndex(0);

            JLabel empty = new JLabel("");
            JButton generateButton = new JButton("Wystaw");
            generateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String format = (String) chooseFormatComboBox.getSelectedItem();
                    int idx = orderComboBox.getSelectedIndex();
                    Order selectedOrder = manager.getOrders().get(idx);

                    if(Objects.equals(format, ".PDF")){
                        PdfInvoice invoice = new PdfInvoice(selectedOrder, LocalDateTime.now());
                        try {
                            invoice.generateInvoice();
                        } catch (DocumentException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    } else if (Objects.equals(format, ".TXT")) {
                        TxtInvoice invoice = null;
                        try {
                            invoice = new TxtInvoice(selectedOrder, LocalDateTime.now());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            invoice.generateInvoice();
                        } catch (DocumentException ex) {
                            throw new RuntimeException(ex);
                        } catch (FileNotFoundException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }

                    JOptionPane.showMessageDialog(EmployeePanel.this, "Pomyślnie wystawiono fakturę");
                    manager.getOrders().remove(idx);
                }
            });

            JButton backButton = new JButton("Powrót");
            backButtonActionListenerAdder(backButton, title, panel);

            panel.add(chooseOrderLabel);
            panel.add(orderComboBox);
            panel.add(chooseFormatLabel);
            panel.add(chooseFormatComboBox);
            panel.add(empty);
            panel.add(generateButton);

            this.add(title);
            this.add(panel);
            this.add(backButton);
            this.setVisible(true);
        }
        if(e.getSource() == addDiscountButton) {
            clear();
            JLabel title = createTitleLabel("Nałóż zniżkę na produkt");

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));
            panel.setBounds(250, 200, 300, 100);

            JLabel chooseProductLabel = new JLabel("Wybierz produkt z listy:");
            String[] products = manager.getProductsAsStringArray();
            JComboBox<String> productComboBox = new JComboBox<>(products);
            productComboBox.setSelectedIndex(0);

            JLabel discountValueLabel = new JLabel("Podaj wartość zniżki (%): ");
            JTextField discountTextField = new JTextField();

            JLabel empty = new JLabel("");
            JButton addDiscountButton = new JButton("Dodaj zniżkę");
            addDiscountButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = (String) productComboBox.getSelectedItem();
                    double discount = Double.parseDouble(discountTextField.getText());

                    /*
                    IProduct tempProduct = manager.getProducts().get(name);
                    double vatRate = tempProduct.getVatRate();
                    int quantity = tempProduct.getQuantity();
                    double price = tempProduct.getPrice();
                    String categoryName = tempProduct.getCategory().getCategoryName();


                    IProduct product;

                    product = new DiscountProductDecorator(new Product(name, price, vatRate, quantity, new Category(categoryName)), discount);
                    */

                    IProduct tempProduct = manager.getProducts().get(name).clone();
                    IProduct product = new DiscountProductDecorator(tempProduct, discount);

                    manager.setDiscount(name, product);

                    JOptionPane.showMessageDialog(EmployeePanel.this, "Dodano zniżkę na produkt: " + name +
                            ", stara cena: " + tempProduct.getPrice() + ", nowa cena: " + product.getPrice());
                    discountTextField.setText("");
                }
            });

            JButton backButton = new JButton("Powrót");
            backButtonActionListenerAdder(backButton, title, panel);

            panel.add(chooseProductLabel);
            panel.add(productComboBox);
            panel.add(discountValueLabel);
            panel.add(discountTextField);
            panel.add(empty);
            panel.add(addDiscountButton);

            this.add(title);
            this.add(panel);
            this.add(backButton);
            this.setVisible(true);
        }
        if(e.getSource() == logoutButton) {
            this.setVisible(false);
        }
    }
}
