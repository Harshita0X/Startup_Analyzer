package com.startup.gui;

import com.startup.MainApplication;
import com.startup.models.StartupIdea;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class IdeaComparisonScreen {
    private JPanel panel;

    public IdeaComparisonScreen(List<StartupIdea> userIdeas) {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        // Navigation Bar
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navBar.setOpaque(false);
        navBar.setBorder(new EmptyBorder(20, 20, 0, 20));
        JButton backBtn = new JButton("<- Back to Dashboard");
        backBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 999; background: #262626; foreground: #FFFFFF; font: bold; borderWidth: 0;");
        backBtn.setFocusable(false);
        backBtn.addActionListener(e -> MainApplication.switchScreen(new DashboardScreen().getPanel()));
        navBar.add(backBtn);

        JLabel titleLabel = new JLabel("Idea Comparison Tool");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(0, 20, 0, 0));
        navBar.add(titleLabel);
        panel.add(navBar, BorderLayout.NORTH);

        if (userIdeas == null || userIdeas.size() < 2) {
            JPanel center = new JPanel(new GridBagLayout());
            center.setOpaque(false);
            JLabel msg = new JLabel("You need to save at least 2 ideas to compare them!");
            msg.setForeground(Color.GRAY);
            msg.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            center.add(msg);
            panel.add(center, BorderLayout.CENTER);
            return;
        }

        // Selection Row
        JPanel selectionRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        selectionRow.setOpaque(false);
        
        JComboBox<IdeaWrapper> box1 = new JComboBox<>();
        JComboBox<IdeaWrapper> box2 = new JComboBox<>();
        box1.putClientProperty(FlatClientProperties.STYLE, "arc: 12; padding: 5, 10, 5, 10; font: bold");
        box2.putClientProperty(FlatClientProperties.STYLE, "arc: 12; padding: 5, 10, 5, 10; font: bold");
        
        for (StartupIdea idea : userIdeas) {
            box1.addItem(new IdeaWrapper(idea));
            box2.addItem(new IdeaWrapper(idea));
        }
        
        if (userIdeas.size() > 1) box2.setSelectedIndex(1);

        selectionRow.add(new JLabel("Idea A:"));
        selectionRow.add(box1);
        selectionRow.add(new JLabel("  vs  "));
        selectionRow.add(new JLabel("Idea B:"));
        selectionRow.add(box2);

        // Comparison Area
        JPanel compareArea = new JPanel(new GridLayout(1, 2, 20, 0));
        compareArea.setOpaque(false);
        compareArea.setBorder(new EmptyBorder(10, 40, 40, 40));

        JPanel col1 = new JPanel(new BorderLayout());
        col1.setOpaque(false);
        JPanel col2 = new JPanel(new BorderLayout());
        col2.setOpaque(false);

        JTextPane pane1 = createPane();
        JTextPane pane2 = createPane();

        col1.add(new JScrollPane(pane1), BorderLayout.CENTER);
        col2.add(new JScrollPane(pane2), BorderLayout.CENTER);
        compareArea.add(col1);
        compareArea.add(col2);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(selectionRow, BorderLayout.NORTH);
        centerPanel.add(compareArea, BorderLayout.CENTER);
        panel.add(centerPanel, BorderLayout.CENTER);

        // Listeners to update
        Runnable updateComparison = () -> {
            StartupIdea i1 = ((IdeaWrapper) box1.getSelectedItem()).idea;
            StartupIdea i2 = ((IdeaWrapper) box2.getSelectedItem()).idea;
            pane1.setText(generateHtml(i1, i1.getMarketDemandScore() >= i2.getMarketDemandScore()));
            pane2.setText(generateHtml(i2, i2.getMarketDemandScore() >= i1.getMarketDemandScore()));
        };

        box1.addActionListener(e -> updateComparison.run());
        box2.addActionListener(e -> updateComparison.run());
        
        // Initial load
        updateComparison.run();
    }

    private JTextPane createPane() {
        JTextPane pane = new JTextPane();
        pane.setContentType("text/html");
        pane.setEditable(false);
        pane.putClientProperty(FlatClientProperties.STYLE, "arc: 16; background: #121212; border: 1,1,1,1,#333");
        return pane;
    }

    private String generateHtml(StartupIdea idea, boolean isWinnerInDemand) {
        String winnerBadge = isWinnerInDemand ? "<span style='background-color:#065f46; color:#34d399; padding: 4px 8px; border-radius:12px; font-size:12px'>\uD83C\uDFC6 Higher Demand</span>" : "";
        
        return "<html><body style='font-family: \"Segoe UI\", sans-serif; color: #E0E0E0; padding: 15px; background-color: #121212;'>"
                + "<h2 style='color: white; margin-top:0; border-bottom: 2px solid #333; padding-bottom: 10px;'>" + idea.getName() + " " + winnerBadge + "</h2>"
                + "<div style='color: #a1a1aa; font-size: 13px; margin-bottom: 20px;'>" + idea.getRawIdea() + "</div>"
                + "<h3>\uD83D\uDCCA Scores</h3>"
                + getProgressBarHTML("Innovation", idea.getInnovationScore(), "#a855f7")
                + getProgressBarHTML("Feasibility", idea.getFeasibilityScore(), "#3b82f6")
                + getProgressBarHTML("Market Demand", idea.getMarketDemandScore(), "#10b981")
                + "<h3>\uD83C\uDFAF Demographics</h3>"
                + "<p><b>Age:</b> " + (idea.getTargetAge() == null ? "N/A" : idea.getTargetAge()) + "</p>"
                + "<p><b>Income:</b> " + (idea.getTargetIncome() == null ? "N/A" : idea.getTargetIncome()) + "</p>"
                + "<p><b>Model:</b> " + (idea.getB2bOrB2c() == null ? "N/A" : idea.getB2bOrB2c()) + "</p>"
                + "<h3>\uD83D\uDDE1\uFE0F SWOT Baseline</h3>"
                + "<p><b style='color:#34d399'>Strengths:</b> " + (idea.getSwotStrengths()==null?"N/A":idea.getSwotStrengths()) + "</p>"
                + "<p><b style='color:#f87171'>Weaknesses:</b> " + (idea.getSwotWeaknesses()==null?"N/A":idea.getSwotWeaknesses()) + "</p>"
                + "</body></html>";
    }

    private String getProgressBarHTML(String label, int score, String color) {
        int pct = score * 10;
        return "<div style='margin-bottom: 8px;'>"
                + "<div style='display: flex; justify-content: space-between; font-size: 12px; margin-bottom: 4px;'>"
                + "<span>" + label + "</span><span style='float:right'>" + score + "/10</span></div>"
                + "<div style='background-color: #27272A; height: 10px; border-radius: 5px; width: 100%;'>"
                + "<div style='background-color: " + color + "; height: 10px; border-radius: 5px; width: " + pct + "%;'></div>"
                + "</div></div>";
    }

    public JPanel getPanel() {
        return panel;
    }

    private static class IdeaWrapper {
        StartupIdea idea;
        public IdeaWrapper(StartupIdea idea) { this.idea = idea; }
        @Override
        public String toString() { return idea.getName(); }
    }
}
