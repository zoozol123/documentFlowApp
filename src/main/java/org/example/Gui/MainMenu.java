package org.example.Gui;

import org.example.Logic.Order;
import org.example.Logic.User;
import org.example.Logic.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MainMenu extends JFrame implements ActionListener {
    private JTextField loginField;
    private JPasswordField passwordField;
    JLayeredPane layeredPane = new JLayeredPane();
    UserPanel userPanel;
    EmployeePanel employeePanel = new EmployeePanel();
    JButton loginButton;

    User user;
    public MainMenu() {
        user = new User(2, "2");
        userPanel = new UserPanel(user);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Login:"));
        loginField = new JTextField();
        panel.add(loginField);

        panel.add(new JLabel("Hasło:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        panel.add(new JLabel(""));
        loginButton = new JButton("Zaloguj");
        loginButton.addActionListener(this);
        panel.add(loginButton);

        panel.setBounds(250, 200, 300, 80);

        layeredPane.setBounds(0, 0, 800, 600);
        layeredPane.add(panel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(userPanel, Integer.valueOf(1));
        layeredPane.add(employeePanel, Integer.valueOf(1));

        this.setLayout(null);
        this.add(layeredPane);
        this.setTitle("Document flow");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.getContentPane().setBackground(Color.BLUE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == loginButton) {
            int id = Integer.parseInt(loginField.getText());
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            Users users = new Users();
            users.addUser(user);

            if(Objects.equals(id, 1) && password.equals("1")) {
                employeePanel.setVisible(true);
                loginField.setText("");
                passwordField.setText("");
            }
            else if(Objects.equals(user.getId(), 2) && Objects.equals(user.getPassword(), "2")) {
                userPanel.setVisible(true);
                userPanel.setDisplayNewProductAlert();
                loginField.setText("");
                passwordField.setText("");
            }
        }
    }
}
