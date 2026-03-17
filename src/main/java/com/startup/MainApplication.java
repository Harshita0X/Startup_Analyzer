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
        try {
            storageManager = new MySQLStorageManager();
        } catch (Exception e) {
            System.err.println("WARN: Failed to connect to MySQL. Ensure XAMPP/MySQL is running.");
        }
        
        analysisEngine = new AnalysisEngine();

        // Setup Main Frame
        SwingUtilities.invokeLater(() -> {
            try {
                // Apply modern dark theme
                FlatDarkLaf.setup();
                UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
                UIManager.put("Button.arc", 8);
                UIManager.put("Component.arc", 8);
                UIManager.put("TextComponent.arc", 8);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mainFrame = new JFrame("Mind Mates - AI Startup Strategy Lab");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(1280, 850);
            mainFrame.setLocationRelativeTo(null);

            // Start with Login Screen
            switchScreen(new LoginScreen().getPanel());
            
            mainFrame.setVisible(true);
        });
    }

    public static void switchScreen(JPanel panel) {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(panel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }
}
