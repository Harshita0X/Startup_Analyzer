package com.startup.gui;

import com.startup.MainApplication;
import com.startup.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class LoginScreen {
    private JPanel panel;

    public LoginScreen() {
        // Use BackgroundPanel for "flashy" look
        panel = new BackgroundPanel("/images/bg.png");
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Glassmorphism effect container
        JPanel glassPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 30)); // Semi-transparent white
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        glassPanel.setOpaque(false);
        glassPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel titleLabel = new JLabel("Starthub", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Outfit", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Revolutionizing Startup Ideas", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Outfit", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(200, 200, 200));

        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.insets = new Insets(10, 10, 10, 10);
        innerGbc.fill = GridBagConstraints.HORIZONTAL;
        innerGbc.gridx = 0; innerGbc.gridy = 0; innerGbc.gridwidth = 2;
        
        glassPanel.add(titleLabel, innerGbc);
        innerGbc.gridy = 1;
        glassPanel.add(subtitleLabel, innerGbc);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(Color.WHITE);
        innerGbc.gridy = 2; innerGbc.gridwidth = 1;
        glassPanel.add(userLabel, innerGbc);

        JTextField userField = new JTextField(15);
        userField.setBackground(new Color(255, 255, 255, 20));
        userField.setForeground(Color.WHITE);
        userField.setCaretColor(Color.WHITE);
        innerGbc.gridx = 1;
        glassPanel.add(userField, innerGbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.WHITE);
        innerGbc.gridx = 0; innerGbc.gridy = 3;
        glassPanel.add(passLabel, innerGbc);

        JPasswordField passField = new JPasswordField(15);
        passField.setBackground(new Color(255, 255, 255, 20));
        passField.setForeground(Color.WHITE);
        passField.setCaretColor(Color.WHITE);
        innerGbc.gridx = 1;
        glassPanel.add(passField, innerGbc);

        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(139, 92, 246));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        innerGbc.gridx = 0; innerGbc.gridy = 4; innerGbc.gridwidth = 1;
        innerGbc.ipady = 10;
        glassPanel.add(loginButton, innerGbc);

        JButton signupButton = new JButton("Sign Up");
        signupButton.setFocusPainted(false);
        innerGbc.gridx = 1;
        glassPanel.add(signupButton, innerGbc);

        panel.add(glassPanel, gbc);

        loginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            
            User user = MainApplication.storageManager.loadUser(username);
            if (user != null && user.getPassword().equals(password)) {
                SessionManager.setCurrentUser(user);
                MainApplication.switchScreen(new DashboardScreen().getPanel());
            } else {
                JOptionPane.showMessageDialog(panel, "Invalid username or password");
            }
        });

        signupButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please enter both username and password");
                return;
            }

            if (MainApplication.storageManager.loadUser(username) != null) {
                JOptionPane.showMessageDialog(panel, "Username already exists");
                return;
            }

            User newUser = new User(username, password);
            MainApplication.storageManager.saveUser(newUser);
            JOptionPane.showMessageDialog(panel, "Account created! You can now login.");
        });
    }

    public JPanel getPanel() {
        return panel;
    }
}
