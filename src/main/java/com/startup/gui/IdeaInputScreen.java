package com.startup.gui;

import com.startup.MainApplication;
import com.startup.models.StartupIdea;
import com.startup.models.User;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.UUID;

public class IdeaInputScreen {
    private JPanel panel;

    public IdeaInputScreen() {
        // Use BackgroundPanel for "flashy" look
        panel = new BackgroundPanel("/images/bg.png");
        panel.setLayout(new BorderLayout());
        
        // Navigation Bar
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navBar.setOpaque(false);
        navBar.setBorder(new EmptyBorder(25, 40, 0, 40));
        JButton backBtn = new JButton("<- Back");
        backBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 999; background: #262626; foreground: #FFFFFF; font: bold; borderWidth: 0;");
        backBtn.setFocusable(false);
        backBtn.addActionListener(e -> MainApplication.switchScreen(new DashboardScreen().getPanel()));
        navBar.add(backBtn);
        panel.add(navBar, BorderLayout.NORTH);

        // Center Form (Glass container)
        JPanel centerContainer = new JPanel(new GridBagLayout());
        centerContainer.setOpaque(false);
        
        JPanel glassPanel = new JPanel();
        glassPanel.setLayout(new BoxLayout(glassPanel, BoxLayout.Y_AXIS));
        glassPanel.setBackground(new Color(255, 255, 255, 15));
        glassPanel.setBorder(new EmptyBorder(50, 60, 50, 60));
        glassPanel.setPreferredSize(new Dimension(800, 650));
        glassPanel.setMaximumSize(new Dimension(800, 700));

        // -- TITLE --
        JLabel titleLabel = new JLabel("Idea Lab");
        titleLabel.setFont(new Font("Outfit", Font.BOLD, 42));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        glassPanel.add(titleLabel);

        JLabel subtitleLabel = new JLabel("Describe your vision, and we'll build the strategy.");
        subtitleLabel.setFont(new Font("Outfit", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(161, 161, 170));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setBorder(new EmptyBorder(5, 0, 35, 0));
        glassPanel.add(subtitleLabel);

        // -- NAME FIELD --
        JLabel nameLabel = new JLabel("Venture Name");
        nameLabel.setFont(new Font("Outfit", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        glassPanel.add(nameLabel);
        glassPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(700, 50));
        nameField.putClientProperty(FlatClientProperties.STYLE, "arc: 16; background: #242427; foreground: #FFFFFF; borderWidth: 1; borderColor: #3F3F46; margin: 5, 20, 5, 20;");
        nameField.setFont(new Font("Outfit", Font.PLAIN, 18));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        glassPanel.add(nameField);
        glassPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // -- DESCRIPTION AREA --
        JLabel descLabel = new JLabel("Business Concept");
        descLabel.setFont(new Font("Outfit", Font.BOLD, 14));
        descLabel.setForeground(Color.WHITE);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        glassPanel.add(descLabel);
        glassPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        JTextArea ideaTextArea = new JTextArea();
        ideaTextArea.setLineWrap(true);
        ideaTextArea.setWrapStyleWord(true);
        ideaTextArea.setFont(new Font("Outfit", Font.PLAIN, 16));
        ideaTextArea.setBackground(new Color(24, 24, 27));
        ideaTextArea.setForeground(Color.WHITE);
        ideaTextArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(ideaTextArea);
        scrollPane.setMaximumSize(new Dimension(700, 180));
        scrollPane.setPreferredSize(new Dimension(700, 180));
        scrollPane.setBorder(null);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        glassPanel.add(scrollPane);
        glassPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // Options row
        JPanel optionsRow = new JPanel(new GridLayout(1, 3, 20, 0));
        optionsRow.setOpaque(false);
        optionsRow.setMaximumSize(new Dimension(700, 70));
        optionsRow.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JPanel catCol = createOptionCol("Industry", new String[]{"Tech", "Food", "Education", "Health", "Fashion"});
        JComboBox<String> catCombo = getCombo(catCol);
        
        JPanel formatCol = createOptionCol("Analysis Depth", new String[]{"Short Summary", "Investor Pitch", "Full Breakdown"});
        JComboBox<String> formatCombo = getCombo(formatCol);

        JPanel budgetCol = createOptionCol("Expected Budget", new String[]{"Under $10k", "$10k - $50k", "$50k - $250k", "$250k+"});
        JComboBox<String> budgetCombo = getCombo(budgetCol);

        optionsRow.add(catCol);
        optionsRow.add(formatCol);
        optionsRow.add(budgetCol);
        glassPanel.add(optionsRow);
        glassPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // CREATE BUTTON
        JButton analyzeButton = new JButton("Generate Strategy Lab \u2192");
        analyzeButton.putClientProperty(FlatClientProperties.STYLE, "arc: 999; background: #8B5CF6; foreground: #FFFFFF; font: bold; margin: 12, 40, 12, 40;");
        analyzeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        analyzeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        analyzeButton.addActionListener(e -> {
            String n = nameField.getText().trim();
            if (n.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please provide a name for your idea.");
                return;
            }
            String rawIdea = ideaTextArea.getText().trim();

            String format = (String) formatCombo.getSelectedItem();
            String category = (String) catCombo.getSelectedItem();
            String id = UUID.randomUUID().toString().substring(0, 8);
            
            StartupIdea idea = new StartupIdea(id, n, rawIdea, category);
            idea.setReportFormat(format);
            idea.setBudget((String) budgetCombo.getSelectedItem());
            idea.setTeamSize("2-5 Team"); // Defaults for simplicity in this view
            idea.setTimeframe("3-6 Months");
            
            MainApplication.analysisEngine.analyze(idea);

            User currentUser = SessionManager.getCurrentUser();
            currentUser.addIdea(idea);
            MainApplication.storageManager.saveUser(currentUser);

            MainApplication.switchScreen(new ResultScreen(idea).getPanel());
        });
        glassPanel.add(analyzeButton);

        centerContainer.add(glassPanel);
        panel.add(centerContainer, BorderLayout.CENTER);
    }

    private JPanel createOptionCol(String label, String[] items) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setOpaque(false);
        
        JLabel l = new JLabel(label);
        l.setFont(new Font("Outfit", Font.BOLD, 12));
        l.setForeground(new Color(161, 161, 170));
        l.setBorder(new EmptyBorder(0, 0, 5, 0));
        
        JComboBox<String> combo = new JComboBox<>(items);
        combo.putClientProperty(FlatClientProperties.STYLE, "arc: 12; background: #262626; foreground: #FFFFFF;");
        combo.setMaximumSize(new Dimension(220, 40));
        
        col.add(l);
        col.add(combo);
        return col;
    }

    @SuppressWarnings("unchecked")
    private JComboBox<String> getCombo(JPanel col) {
        return (JComboBox<String>) col.getComponent(1);
    }

    public JPanel getPanel() {
        return panel;
    }
}
