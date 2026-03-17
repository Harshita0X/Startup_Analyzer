package com.startup.gui;

import com.startup.MainApplication;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class IdeaGenerationScreen {
    private JPanel panel;

    public IdeaGenerationScreen() {
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Title
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("🚀 AI Trend & Idea Generator");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(139, 92, 246));
        panel.add(titleLabel, gbc);

        // Generate Buttons based on real-time simulated trends
        gbc.gridy++; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.WEST;
        JButton techButton = new JButton("Generate Tech Idea (Trending: AI Agents)");
        JButton foodButton = new JButton("Generate Food Idea (Trending: Ghost Kitchens)");
        JButton healthButton = new JButton("Generate Health Idea (Trending: Tele-therapy)");
        
        // Output text area
        gbc.gridy++; gbc.gridx = 0; gbc.gridwidth = 2;
        JTextArea outputArea = new JTextArea(8, 40);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Simulated AI Suggestion"));

        // Action Listeners
        techButton.addActionListener(e -> outputArea.setText(generateRandomIdea("Tech")));
        foodButton.addActionListener(e -> outputArea.setText(generateRandomIdea("Food")));
        healthButton.addActionListener(e -> outputArea.setText(generateRandomIdea("Health")));

        // Layout buttons
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.add(techButton);
        buttonPanel.add(foodButton);
        buttonPanel.add(healthButton);

        gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        gbc.gridy = 2; 
        panel.add(scrollPane, gbc);

        // Navigation
        gbc.gridy = 3; gbc.gridwidth = 1;
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> MainApplication.switchScreen(new DashboardScreen().getPanel()));
        panel.add(backButton, gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.EAST;
        JButton sendToAnalyzerButton = new JButton("Use this Idea →");
        sendToAnalyzerButton.setBackground(new Color(60, 130, 200));
        sendToAnalyzerButton.setForeground(Color.WHITE);
        sendToAnalyzerButton.addActionListener(e -> {
            if (outputArea.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Generate an idea first!");
                return;
            }
            // Navigate to IdeaInputScreen (user will manually paste or we could technically auto-fill, but keeping it simple)
            JOptionPane.showMessageDialog(panel, "Copying to clipboard! Paste it in the input screen.");
            MainApplication.switchScreen(new IdeaInputScreen().getPanel());
        });
        panel.add(sendToAnalyzerButton, gbc);
    }

    private String generateRandomIdea(String category) {
        Random rand = new Random();
        if (category.equals("Tech")) {
            String[] ideas = {
                "A hyper-personalized B2B SaaS platform utilizing autonomous AI agents to completely replace tier-1 customer tech support.",
                "A zero-trust web3 authentication protocol for enterprise IoT devices.",
                "A no-code generative UI builder that turns sketched wireframes into fully functional React code in real-time."
            };
            return ideas[rand.nextInt(ideas.length)];
        } else if (category.equals("Food")) {
            String[] ideas = {
                "An ultra-fast 10-minute grocery delivery service operating exclusively through dark stores and automated sorting arms.",
                "A subscription box for high-protein, plant-based meal replacements tailored by AI based on daily biometric smartwatch data.",
                "B2B marketplace directly connecting local organic farmers with independent gourmet restaurants, bypassing traditional distributors."
            };
            return ideas[rand.nextInt(ideas.length)];
        } else {
            String[] ideas = {
                "A continuous glucose monitoring app that gamifies nutrition by pitting friends against each other in 'stable blood sugar' challenges.",
                "A VR-based exposure therapy platform for phobia treatment, licensed directly to remote psychologists.",
                "An AI-driven diagnostic tool that analyzes micro-expressions during telehealth calls to assess mental well-being in real-time."
            };
            return ideas[rand.nextInt(ideas.length)];
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
