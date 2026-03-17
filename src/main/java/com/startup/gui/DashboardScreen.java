package com.startup.gui;

import com.startup.MainApplication;
import com.startup.models.StartupIdea;
import com.startup.models.User;
import com.formdev.flatlaf.FlatClientProperties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;
import java.util.List;

public class DashboardScreen {
    private JPanel panel;

    public DashboardScreen() {
        // Use BackgroundPanel for "flashy" look
        panel = new BackgroundPanel("/images/bg.png");
        panel.setLayout(new BorderLayout());

        // Main Scroll Pane (Transparent to show background)
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setOpaque(false);
        mainContent.setBorder(new EmptyBorder(30, 40, 40, 40));

        // -- TOP HEADER ROW --
        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        topRow.setMaximumSize(new Dimension(1600, 60));
        
        User currentUser = SessionManager.getCurrentUser();
        JLabel welcomeLabel = new JLabel("Welcome back, " + currentUser.getUsername() + " \u2728");
        welcomeLabel.setFont(new Font("Outfit", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);

        JPanel rightActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightActions.setOpaque(false);

        JButton createBtn = new JButton("+ New Idea");
        createBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 999; background: #8B5CF6; foreground: #FFFFFF; font: bold;");
        createBtn.setFocusable(false);
        createBtn.addActionListener(e -> MainApplication.switchScreen(new IdeaInputScreen().getPanel()));

        JButton compareBtn = new JButton("Compare Ideas \u2696\uFE0F");
        compareBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 999; background: #2563EB; foreground: #FFFFFF; font: bold;");
        compareBtn.setFocusable(false);
        compareBtn.addActionListener(e -> MainApplication.switchScreen(new IdeaComparisonScreen(currentUser.getSavedIdeas()).getPanel()));

        JButton generatorBtn = new JButton("AI Generator \uD83D\uDD25");
        generatorBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 999; background: #374151; foreground: #FFFFFF; font: bold;");
        generatorBtn.setFocusable(false);
        generatorBtn.addActionListener(e -> MainApplication.switchScreen(new IdeaGenerationScreen().getPanel()));

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 999;");
        logoutBtn.setFocusable(false);
        logoutBtn.addActionListener(e -> {
            SessionManager.logout();
            MainApplication.switchScreen(new LoginScreen().getPanel());
        });

        rightActions.add(compareBtn);
        rightActions.add(generatorBtn);
        rightActions.add(createBtn);
        rightActions.add(logoutBtn);

        topRow.add(welcomeLabel, BorderLayout.WEST);
        topRow.add(rightActions, BorderLayout.EAST);
        mainContent.add(topRow);
        mainContent.add(Box.createRigidArea(new Dimension(0, 40)));

        // -- HERO TITLE --
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        titlePanel.setMaximumSize(new Dimension(1600, 70));

        JLabel title1 = new JLabel("Fuel your next ");
        title1.setFont(new Font("Outfit", Font.BOLD, 42));
        title1.setForeground(Color.WHITE);

        JLabel title2 = new JLabel(" multi-million ");
        title2.setFont(new Font("Outfit", Font.BOLD, 42));
        title2.setForeground(Color.WHITE);
        title2.setOpaque(true);
        title2.setBackground(new Color(139, 92, 246)); 
        // title2 styling: 'arc' is not supported on JLabel directly

        JLabel title3 = new JLabel(" venture");
        title3.setFont(new Font("Outfit", Font.BOLD, 42));
        title3.setForeground(Color.WHITE);

        titlePanel.add(title1);
        titlePanel.add(title2);
        titlePanel.add(title3);
        mainContent.add(titlePanel);

        JLabel subtitle = new JLabel("Intelligent insights for the modern visionary");
        subtitle.setFont(new Font("Outfit", Font.PLAIN, 18));
        subtitle.setForeground(new Color(161, 161, 170));
        subtitle.setBorder(new EmptyBorder(10, 0, 30, 0));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        subPanel.setOpaque(false);
        subPanel.setMaximumSize(new Dimension(1600, 50));
        subPanel.add(subtitle);
        mainContent.add(subPanel);

        // -- TRENDING CARDS --
        JLabel trendingLabel = new JLabel("\uD83D\uDCC8 Trending Opportunities");
        trendingLabel.setFont(new Font("Outfit", Font.BOLD, 24));
        trendingLabel.setForeground(Color.WHITE);
        JPanel trendTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        trendTitlePanel.setOpaque(false);
        trendTitlePanel.setMaximumSize(new Dimension(1600, 40));
        trendTitlePanel.add(trendingLabel);
        mainContent.add(trendTitlePanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel trendingCards = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        trendingCards.setOpaque(false);
        String[] cardImages = {"/images/card1.png", "/images/card2.png", "/images/card3.png", "/images/card4.png"};
        String[] cardTitles = {"Crypto Banking", "Ethical AI", "Health Monitoring", "Greener Logistics"};
        for (int i = 0; i < 4; i++) {
            trendingCards.add(createTrendingCard(cardTitles[i], cardImages[i]));
        }
        
        JScrollPane trendScroll = new JScrollPane(trendingCards);
        trendScroll.setOpaque(false);
        trendScroll.getViewport().setOpaque(false);
        trendScroll.setBorder(null);
        trendScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        trendScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        trendScroll.setMaximumSize(new Dimension(1600, 260));
        trendScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainContent.add(trendScroll);
        mainContent.add(Box.createRigidArea(new Dimension(0, 40)));

        // -- IDEAS LIST --
        JLabel listLabel = new JLabel("\uD83E\uDDE0 Your Strategy Lab");
        listLabel.setFont(new Font("Outfit", Font.BOLD, 24));
        listLabel.setForeground(Color.WHITE);
        JPanel listTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        listTitlePanel.setOpaque(false);
        listTitlePanel.setMaximumSize(new Dimension(1600, 40));
        listTitlePanel.add(listLabel);
        mainContent.add(listTitlePanel);
        mainContent.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel ideasListPanel = new JPanel();
        ideasListPanel.setLayout(new BoxLayout(ideasListPanel, BoxLayout.Y_AXIS));
        ideasListPanel.setOpaque(false);

        List<StartupIdea> savedIdeas = currentUser.getSavedIdeas();
        if (savedIdeas.isEmpty()) {
            JLabel emptyLabel = new JLabel("Your strategy lab is empty. Launch your first idea! \uD83D\uDE80");
            emptyLabel.setFont(new Font("Outfit", Font.ITALIC, 16));
            emptyLabel.setForeground(Color.GRAY);
            ideasListPanel.add(emptyLabel);
        } else {
            for (StartupIdea idea : savedIdeas) {
                ideasListPanel.add(createIdeaRow(idea));
                ideasListPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            }
        }
        
        ideasListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainContent.add(ideasListPanel);

        JScrollPane mainScroll = new JScrollPane(mainContent);
        mainScroll.setOpaque(false);
        mainScroll.getViewport().setOpaque(false);
        mainScroll.setBorder(null);
        mainScroll.getVerticalScrollBar().setUnitIncrement(20);
        panel.add(mainScroll, BorderLayout.CENTER);
    }

    private JPanel createTrendingCard(String title, String imagePath) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(240, 220));
        card.setBackground(new Color(255, 255, 255, 15)); // Glass card
        card.setBorder(new EmptyBorder(12, 12, 12, 12));

        JPanel imgBox = new JPanel(new BorderLayout());
        imgBox.setOpaque(false);
        imgBox.setPreferredSize(new Dimension(216, 120));
        
        try {
            URL url = getClass().getResource(imagePath);
            if (url != null) {
                BufferedImage originalImage = ImageIO.read(url);
                Image scaled = originalImage.getScaledInstance(216, 120, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaled));
                imgBox.add(imageLabel, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Outfit", Font.BOLD, 16));
        titleLabel.setBorder(new EmptyBorder(10, 0, 5, 0));

        JLabel blurb = new JLabel("High market demand \u2022 2025");
        blurb.setForeground(new Color(161, 161, 170));
        blurb.setFont(new Font("Outfit", Font.PLAIN, 12));

        card.add(imgBox, BorderLayout.NORTH);
        card.add(titleLabel, BorderLayout.CENTER);
        card.add(blurb, BorderLayout.SOUTH);
        return card;
    }

    private JPanel createIdeaRow(StartupIdea idea) {
        JPanel row = new JPanel(new BorderLayout(20, 0));
        row.setBackground(new Color(255, 255, 255, 10)); // Glass row
        row.setMaximumSize(new Dimension(1600, 140));
        row.setBorder(new EmptyBorder(20, 20, 20, 20));

        // LEFT Image mock
        JPanel imgBox = new JPanel(new BorderLayout());
        imgBox.setBackground(new Color(255, 255, 255, 10));
        imgBox.setPreferredSize(new Dimension(100, 100));
        
        try {
            URL url = getClass().getResource("/images/card1.png"); // Use a tech card as thumbnail
            if (url != null) {
                BufferedImage originalImage = ImageIO.read(url);
                Image scaled = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaled));
                imgBox.add(imageLabel, BorderLayout.CENTER);
            }
        } catch (Exception e) {}
        
        // CENTER Data
        JPanel centerData = new JPanel();
        centerData.setLayout(new BoxLayout(centerData, BoxLayout.Y_AXIS));
        centerData.setOpaque(false);
        
        String ideaName = (idea.getName() != null && !idea.getName().isEmpty()) ? idea.getName() : "Untitled Venture";
        JLabel title = new JLabel(ideaName);
        title.setFont(new Font("Outfit", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        
        JLabel meta = new JLabel(idea.getCategory().toUpperCase() + " \u2022 STRATEGIC ANALYSIS");
        meta.setFont(new Font("Outfit", Font.BOLD, 11));
        meta.setForeground(new Color(139, 92, 246)); // Purple accent
        
        String snippet = idea.getRawIdea().length() > 100 ? idea.getRawIdea().substring(0, 100) + "..." : idea.getRawIdea();
        JLabel blurb = new JLabel(snippet);
        blurb.setFont(new Font("Outfit", Font.PLAIN, 14));
        blurb.setForeground(new Color(212, 212, 216));
        
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        scorePanel.setOpaque(false);
        JLabel scoreLabel = new JLabel("\u2728 Innovation: " + (idea.getInnovationScore() > 0 ? idea.getInnovationScore() : "--"));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Outfit", Font.BOLD, 12));
        scorePanel.add(scoreLabel);

        centerData.add(title);
        centerData.add(meta);
        centerData.add(Box.createRigidArea(new Dimension(0, 10)));
        centerData.add(blurb);
        centerData.add(Box.createRigidArea(new Dimension(0, 10)));
        centerData.add(scorePanel);

        // RIGHT actions
        JPanel rightActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightActions.setOpaque(false);
        
        JButton launchBtn = new JButton("Open Report");
        launchBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 999; background: #FFFFFF; foreground: #000000; font: bold; margin: 10, 25, 10, 25;");
        launchBtn.setFocusable(false);
        launchBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        launchBtn.addActionListener(e -> MainApplication.switchScreen(new ResultScreen(idea).getPanel()));
        
        JButton deleteBtn = new JButton("\uD83D\uDDD1\uFE0F");
        deleteBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 999; background: #EF4444; foreground: #FFFFFF; font: bold; margin: 10, 15, 10, 15;");
        deleteBtn.setFocusable(false);
        deleteBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(panel, "Discard this strategy?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                User u = SessionManager.getCurrentUser();
                u.getSavedIdeas().remove(idea);
                try (java.sql.Connection conn = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/startup_analyzer", "root", "harshita");
                     java.sql.PreparedStatement pstmt = conn.prepareStatement("DELETE FROM startup_ideas WHERE id = ?")) {
                    pstmt.setString(1, idea.getId());
                    pstmt.executeUpdate();
                } catch (Exception ex) { ex.printStackTrace(); }
                MainApplication.switchScreen(new DashboardScreen().getPanel());
            }
        });
        
        rightActions.add(launchBtn);
        rightActions.add(deleteBtn);

        row.add(imgBox, BorderLayout.WEST);
        row.add(centerData, BorderLayout.CENTER);
        row.add(rightActions, BorderLayout.EAST);
        
        return row;
    }

    public JPanel getPanel() {
        return panel;
    }
}
