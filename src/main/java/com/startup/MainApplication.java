package com.startup;

import com.startup.gui.LoginScreen;
import com.startup.storage.MySQLStorageManager;
import com.startup.storage.StorageManager;
import com.startup.engine.AnalysisEngine;

import javax.swing.*;
import java.awt.Font;
import com.formdev.flatlaf.FlatDarkLaf;

public class MainApplication {

    public static StorageManager storageManager;
    public static AnalysisEngine analysisEngine;
    public static JFrame mainFrame;

    public static void main(String[] args) {
        // Initialize dependencies
        System.out.println("[INFO] Starting application bootstrap...");
        try {
            System.out.println("[INFO] Connecting to database...");
            storageManager = new MySQLStorageManager();
            System.out.println("[INFO] Database connected successfully.");
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to connect to MySQL: " + e.getMessage());
        }
        
        System.out.println("[INFO] Initializing analysis engine...");
        analysisEngine = new AnalysisEngine();

        // Setup Main Frame
        System.out.println("[INFO] Launching UI Task...");
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("[INFO] Configuring Look and Feel...");
                // Apply modern dark theme
                FlatDarkLaf.setup();
                UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
                UIManager.put("Button.arc", 8);
                UIManager.put("Component.arc", 8);
                UIManager.put("TextComponent.arc", 8);
            } catch (Exception e) {
                System.err.println("[ERROR] LookAndFeel setup failed:");
                e.printStackTrace();
            }

            System.out.println("[INFO] Creating Main Frame...");
            mainFrame = new JFrame("Starthub - AI Startup Strategy Lab");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(1280, 850);
            mainFrame.setLocationRelativeTo(null);

            // Start with Login Screen
            System.out.println("[INFO] Initializing Login Screen...");
            switchScreen(new LoginScreen().getPanel());
            
            System.out.println("[INFO] Setting frame visible...");
            mainFrame.setVisible(true);
            System.out.println("[INFO] Startup sequence complete.");
        });
    }

    public static void switchScreen(JPanel panel) {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(panel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
