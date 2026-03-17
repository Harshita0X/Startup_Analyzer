package com.startup.gui;

import com.startup.MainApplication;
import com.startup.models.StartupIdea;
import com.startup.utils.ReportExporter;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ResultScreen {
    private JPanel panel;

    public ResultScreen(StartupIdea idea) {
        // Use BackgroundPanel for "flashy" look
        panel = new BackgroundPanel("/images/bg.png");
        panel.setLayout(new BorderLayout());
        
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(25, 40, 15, 40));
        
        JLabel titleLabel = new JLabel("Strategy Lab Report");
        titleLabel.setFont(new Font("Outfit", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);
        
        panel.add(headerPanel, BorderLayout.NORTH);

        // Core TextPane for HTML Rendering
        JTextPane htmlPane = new JTextPane();
        htmlPane.setContentType("text/html");
        htmlPane.setEditable(false);
        htmlPane.setOpaque(false); // Make transparent for glass effect
        htmlPane.setBackground(new Color(0, 0, 0, 0));
        
        String htmlContent = buildHtmlReport(idea);
        htmlPane.setText(htmlContent);
        htmlPane.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(htmlPane);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "trackArc: 999; thumbArc: 999; width: 8; trackInsets: 2,2,2,2; thumbInsets: 2,2,2,2;");
        
        // Glass container for the report
        JPanel glassContainer = new JPanel(new BorderLayout());
        glassContainer.setBackground(new Color(255, 255, 255, 10));
        glassContainer.putClientProperty(FlatClientProperties.STYLE, "arc: 32;");
        glassContainer.setBorder(new EmptyBorder(30, 30, 30, 30));
        glassContainer.add(scrollPane, BorderLayout.CENTER);
        
        JPanel outerPadding = new JPanel(new BorderLayout());
        outerPadding.setOpaque(false);
        outerPadding.setBorder(new EmptyBorder(0, 40, 20, 40));
        outerPadding.add(glassContainer, BorderLayout.CENTER);
        
        panel.add(outerPadding, BorderLayout.CENTER);

        // Footer Actions
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(0, 40, 25, 40));
        
        JButton backButton = new JButton("<- Dashboard");
        backButton.putClientProperty(FlatClientProperties.STYLE, "arc: 999; background: #262626; foreground: #FFFFFF; font: bold; borderWidth: 0; margin: 10, 25, 10, 25;");
        backButton.setFocusable(false);
        backButton.addActionListener(e -> MainApplication.switchScreen(new DashboardScreen().getPanel()));

        JButton exportButton = new JButton("Export PDF (TEXT)");
        exportButton.putClientProperty(FlatClientProperties.STYLE, "arc: 999; background: #8B5CF6; foreground: #FFFFFF; font: bold; borderWidth: 0; margin: 10, 25, 10, 25;");
        exportButton.setFocusable(false);
        exportButton.addActionListener(e -> {
            String path = ReportExporter.exportToText(idea);
            if (path != null) {
                JOptionPane.showMessageDialog(panel, "Strategy exported to:\n" + path);
            } else {
                JOptionPane.showMessageDialog(panel, "Export failed.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(exportButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private String buildHtmlReport(StartupIdea idea) {
        String css = "<style>" +
                "body { font-family: 'Outfit', sans-serif; color: #E5E7EB; background: transparent; padding: 20px; line-height: 1.6; }" +
                "h1 { color: #FFFFFF; font-size: 40px; margin-bottom: 5px; font-weight: 800; }" +
                ".category { color: #8B5CF6; font-size: 14px; font-weight: bold; margin-bottom: 30px; letter-spacing: 2px; text-transform: uppercase; }" +
                "h2 { color: #FFFFFF; font-size: 24px; margin-top: 40px; margin-bottom: 20px; font-weight: bold; padding-bottom: 10px; border-bottom: 1px solid rgba(139, 92, 246, 0.3); }" +
                "p { margin: 12px 0; color: #D1D5DB; font-size: 17px; }" +
                "ul { margin-top: 15px; margin-bottom: 25px; padding-left: 0; list-style-type: none; }" +
                "li { margin-bottom: 12px; background: rgba(255, 255, 255, 0.05); padding: 15px; border-radius: 12px; border-left: 4px solid #8B5CF6; }" +
                ".stat-box { background: rgba(139, 92, 246, 0.1); border: 1px solid rgba(139, 92, 246, 0.2); padding: 20px; border-radius: 16px; margin: 20px 0; }" +
                ".stat-val { color: #8B5CF6; font-size: 28px; font-weight: 800; }" +
                ".stat-label { color: #A1A1AA; font-size: 12px; text-transform: uppercase; }" +
                "table { width: 100%; border-spacing: 15px; margin: 20px -15px; }" +
                ".swot-card { background: rgba(255, 255, 255, 0.03); border: 1px solid rgba(255, 255, 255, 0.05); border-radius: 16px; padding: 20px; vertical-align: top; }" +
                "</style>";

        StringBuilder sb = new StringBuilder();
        sb.append("<html><head>").append(css).append("</head><body>");
        
        String ideaName = (idea.getName() != null && !idea.getName().isEmpty()) ? idea.getName() : "Project X";
        sb.append("<h1>").append(ideaName).append("</h1>");
        sb.append("<div class='category'>").append(idea.getCategory()).append(" Strategy Lab Analysis</div>");

        sb.append("<h2>\uD83D\uDD0D Problem Hypothesis</h2>");
        sb.append("<p>").append(formatText(idea.getProblemStatement())).append("</p>");

        sb.append("<div class='stat-box'>");
        sb.append("<table style='width:100%; border-spacing:0; margin:0;'><tr>");
        sb.append("<td style='padding:0;'><div class='stat-label'>Innovation</div><div class='stat-val'>").append(idea.getInnovationScore()).append("%</div></td>");
        sb.append("<td style='padding:0;'><div class='stat-label'>Feasibility</div><div class='stat-val'>").append(idea.getFeasibilityScore()).append("%</div></td>");
        sb.append("<td style='padding:0;'><div class='stat-label'>Market Demand</div><div class='stat-val'>").append(idea.getMarketDemandScore()).append("%</div></td>");
        sb.append("</tr></table></div>");

        sb.append("<h2>\uD83D\uDC65 Audience Archetype</h2>");
        sb.append("<li style='background:none; border-left:none; padding:0;'><b>Demographic Focus:</b> ").append(idea.getTargetAge()).append(" \u2022 ").append(idea.getTargetIncome()).append(" \u2022 ").append(idea.getTargetLocation()).append("</li>");
        sb.append("<p>").append(formatText(idea.getCustomerPersonas() != null ? idea.getCustomerPersonas() : idea.getTargetAudience())).append("</p>");

        sb.append("<h2>\uD83D\uDCA1 Solution Paradigm</h2>");
        sb.append("<p>").append(formatText(idea.getSolution() != null ? idea.getSolution() : idea.getUsp())).append("</p>");

        sb.append("<h2>\u23F1\uFE0F Market Timing</h2>");
        sb.append("<p>").append(formatText(idea.getTiming() != null ? idea.getTiming() : idea.getMarketScope())).append("</p>");

        sb.append("<h2>\uD83D\uDCB0 Economic Model</h2>");
        sb.append("<ul>");
        sb.append("<li><b>Vertical:</b> ").append(idea.getB2bOrB2c()).append("</li>");
        sb.append("<li><b>Pricing Strategy:</b> ").append(idea.getPricingStrategy()).append("</li>");
        sb.append("</ul>");
        sb.append("<p>").append(formatText(idea.getRevenueModel())).append("</p>");

        sb.append("<h2>\u2696\uFE0F Strategic SWOT Matrix</h2>");
        sb.append("<table><tr>");
        sb.append("<td class='swot-card'><b style='color:#10B981;'>STRENGTHS</b><br><br>").append(formatText(idea.getSwotStrengths())).append("</td>");
        sb.append("<td class='swot-card'><b style='color:#EF4444;'>WEAKNESSES</b><br><br>").append(formatText(idea.getSwotWeaknesses())).append("</td>");
        sb.append("</tr><tr>");
        sb.append("<td class='swot-card'><b style='color:#3B82F6;'>OPPORTUNITIES</b><br><br>").append(formatText(idea.getSwotOpportunities())).append("</td>");
        sb.append("<td class='swot-card'><b style='color:#F59E0B;'>THREATS</b><br><br>").append(formatText(idea.getSwotThreats())).append("</td>");
        sb.append("</tr></table>");

        sb.append("</body></html>");
        return sb.toString();
    }
    
    private String formatText(String text) {
        if (text == null) return "";
        return text.replace("\n", "<br>");
    }

    public JPanel getPanel() {
        return panel;
    }
}
