package com.startup.storage;

import com.startup.models.StartupIdea;
import com.startup.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLStorageManager implements StorageManager {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "startup_analyzer";
    private static final String USER = "root";
    private static final String PASS = "harshita";

    public MySQLStorageManager() {
        initializeDatabase();
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL + DB_NAME, USER, PASS);
    }

    private void initializeDatabase() {
        try (Connection rootConn = DriverManager.getConnection(URL, USER, PASS);
             Statement stmt = rootConn.createStatement()) {

            // Create Database
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);

            // Connect to the specific database
            try (Connection conn = getConnection();
                 Statement dbStmt = conn.createStatement()) {

                // Create Users Table
                String createUsers = "CREATE TABLE IF NOT EXISTS users (" +
                        "username VARCHAR(50) PRIMARY KEY, " +
                        "password VARCHAR(255) NOT NULL" +
                        ")";
                dbStmt.executeUpdate(createUsers);

                // Create Ideas Table
                String createIdeas = "CREATE TABLE IF NOT EXISTS startup_ideas (" +
                        "id VARCHAR(50) PRIMARY KEY, " +
                        "username VARCHAR(50), " +
                        "name VARCHAR(255), " +
                        "rawIdea TEXT, " +
                        "category VARCHAR(50), " +
                        "problemStatement TEXT, " +
                        "targetAudience TEXT, " +
                        "usp TEXT, " +
                        "featuresList TEXT, " +
                        "revenueModel TEXT, " +
                        "competitorAnalysis TEXT, " +
                        "marketingStrategy TEXT, " +
                        "riskAnalysis TEXT, " +
                        "futureScope TEXT, " +
                        "marketScope TEXT, " +
                        "currentMarketTrend TEXT, " +
                        "differentiation TEXT, " +
                        "uniquenessRecommendations TEXT, " +
                        "customerPersonas TEXT, " +
                        "solution TEXT, " +
                        "timing TEXT, " +
                        "budget VARCHAR(100), " +
                        "teamSize VARCHAR(100), " +
                        "timeframe VARCHAR(100), " +
                        "swotStrengths TEXT, " +
                        "swotWeaknesses TEXT, " +
                        "swotOpportunities TEXT, " +
                        "swotThreats TEXT, " +
                        "targetAge VARCHAR(100), " +
                        "targetIncome VARCHAR(100), " +
                        "targetLocation VARCHAR(100), " +
                        "b2bOrB2c VARCHAR(50), " +
                        "pricingStrategy VARCHAR(100), " +
                        "innovationScore INT, " +
                        "feasibilityScore INT, " +
                        "marketDemandScore INT, " +
                        "FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE" +
                        ")";
                dbStmt.executeUpdate(createIdeas);
                
                // Alter table if exists (ignoring errors if columns already exist)
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN name VARCHAR(255)"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN customerPersonas TEXT"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN solution TEXT"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN timing TEXT"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN budget VARCHAR(100)"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN teamSize VARCHAR(100)"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN timeframe VARCHAR(100)"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN swotStrengths TEXT"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN swotWeaknesses TEXT"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN swotOpportunities TEXT"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN swotThreats TEXT"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN targetAge VARCHAR(100)"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN targetIncome VARCHAR(100)"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN targetLocation VARCHAR(100)"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN b2bOrB2c VARCHAR(50)"); } catch (SQLException ignored) {}
                try { dbStmt.executeUpdate("ALTER TABLE startup_ideas ADD COLUMN pricingStrategy VARCHAR(100)"); } catch (SQLException ignored) {}
            }

        } catch (SQLException e) {
            System.err.println("Could not initialize MySQL database. Make sure MySQL is running on localhost:3306 with root / ''");
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(User user) {
        // Upsert user
        String insertUser = "INSERT INTO users (username, password) VALUES (?, ?) ON DUPLICATE KEY UPDATE password = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertUser)) {
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getPassword());
            pstmt.executeUpdate();

            // Insert/Update Ideas
            for (StartupIdea idea : user.getSavedIdeas()) {
                saveIdea(idea, user.getUsername());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveIdea(StartupIdea idea, String username) {
        String insertIdea = "INSERT INTO startup_ideas (id, username, name, rawIdea, category, problemStatement, targetAudience, " +
                "usp, featuresList, revenueModel, competitorAnalysis, marketingStrategy, riskAnalysis, futureScope, " +
                "marketScope, currentMarketTrend, differentiation, uniquenessRecommendations, " +
                "customerPersonas, solution, timing, budget, teamSize, timeframe, swotStrengths, swotWeaknesses, swotOpportunities, swotThreats, " +
                "targetAge, targetIncome, targetLocation, b2bOrB2c, pricingStrategy, " +
                "innovationScore, feasibilityScore, marketDemandScore) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "name=?, rawIdea=?, category=?, problemStatement=?, targetAudience=?, usp=?, featuresList=?, revenueModel=?, " +
                "competitorAnalysis=?, marketingStrategy=?, riskAnalysis=?, futureScope=?, marketScope=?, currentMarketTrend=?, " +
                "differentiation=?, uniquenessRecommendations=?, customerPersonas=?, solution=?, timing=?, budget=?, teamSize=?, timeframe=?, " +
                "swotStrengths=?, swotWeaknesses=?, swotOpportunities=?, swotThreats=?, targetAge=?, targetIncome=?, targetLocation=?, b2bOrB2c=?, pricingStrategy=?, " +
                "innovationScore=?, feasibilityScore=?, marketDemandScore=?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertIdea)) {
            
            // Setting values
            pstmt.setString(1, idea.getId());
            pstmt.setString(2, username);
            pstmt.setString(3, idea.getName());
            pstmt.setString(4, idea.getRawIdea());
            pstmt.setString(5, idea.getCategory());
            pstmt.setString(6, idea.getProblemStatement());
            pstmt.setString(7, idea.getTargetAudience());
            pstmt.setString(8, idea.getUsp());
            pstmt.setString(9, idea.getFeaturesList());
            pstmt.setString(10, idea.getRevenueModel());
            pstmt.setString(11, idea.getCompetitorAnalysis());
            pstmt.setString(12, idea.getMarketingStrategy());
            pstmt.setString(13, idea.getRiskAnalysis());
            pstmt.setString(14, idea.getFutureScope());
            pstmt.setString(15, idea.getMarketScope());
            pstmt.setString(16, idea.getCurrentMarketTrend());
            pstmt.setString(17, idea.getDifferentiation());
            pstmt.setString(18, idea.getUniquenessRecommendations());
            pstmt.setString(19, idea.getCustomerPersonas());
            pstmt.setString(20, idea.getSolution());
            pstmt.setString(21, idea.getTiming());
            pstmt.setString(22, idea.getBudget());
            pstmt.setString(23, idea.getTeamSize());
            pstmt.setString(24, idea.getTimeframe());
            pstmt.setString(25, idea.getSwotStrengths());
            pstmt.setString(26, idea.getSwotWeaknesses());
            pstmt.setString(27, idea.getSwotOpportunities());
            pstmt.setString(28, idea.getSwotThreats());
            pstmt.setString(29, idea.getTargetAge());
            pstmt.setString(30, idea.getTargetIncome());
            pstmt.setString(31, idea.getTargetLocation());
            pstmt.setString(32, idea.getB2bOrB2c());
            pstmt.setString(33, idea.getPricingStrategy());
            pstmt.setInt(34, idea.getInnovationScore());
            pstmt.setInt(35, idea.getFeasibilityScore());
            pstmt.setInt(36, idea.getMarketDemandScore());

            // Duplicate update matching params
            int idx = 37;
            pstmt.setString(idx++, idea.getName());
            pstmt.setString(idx++, idea.getRawIdea());
            pstmt.setString(idx++, idea.getCategory());
            pstmt.setString(idx++, idea.getProblemStatement());
            pstmt.setString(idx++, idea.getTargetAudience());
            pstmt.setString(idx++, idea.getUsp());
            pstmt.setString(idx++, idea.getFeaturesList());
            pstmt.setString(idx++, idea.getRevenueModel());
            pstmt.setString(idx++, idea.getCompetitorAnalysis());
            pstmt.setString(idx++, idea.getMarketingStrategy());
            pstmt.setString(idx++, idea.getRiskAnalysis());
            pstmt.setString(idx++, idea.getFutureScope());
            pstmt.setString(idx++, idea.getMarketScope());
            pstmt.setString(idx++, idea.getCurrentMarketTrend());
            pstmt.setString(idx++, idea.getDifferentiation());
            pstmt.setString(idx++, idea.getUniquenessRecommendations());
            pstmt.setString(idx++, idea.getCustomerPersonas());
            pstmt.setString(idx++, idea.getSolution());
            pstmt.setString(idx++, idea.getTiming());
            pstmt.setString(idx++, idea.getBudget());
            pstmt.setString(idx++, idea.getTeamSize());
            pstmt.setString(idx++, idea.getTimeframe());
            pstmt.setString(idx++, idea.getSwotStrengths());
            pstmt.setString(idx++, idea.getSwotWeaknesses());
            pstmt.setString(idx++, idea.getSwotOpportunities());
            pstmt.setString(idx++, idea.getSwotThreats());
            pstmt.setString(idx++, idea.getTargetAge());
            pstmt.setString(idx++, idea.getTargetIncome());
            pstmt.setString(idx++, idea.getTargetLocation());
            pstmt.setString(idx++, idea.getB2bOrB2c());
            pstmt.setString(idx++, idea.getPricingStrategy());
            pstmt.setInt(idx++, idea.getInnovationScore());
            pstmt.setInt(idx++, idea.getFeasibilityScore());
            pstmt.setInt(idx, idea.getMarketDemandScore());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User loadUser(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("password"));
                loadIdeasForUser(user);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadIdeasForUser(User user) {
        String query = "SELECT * FROM startup_ideas WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, user.getUsername());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                StartupIdea idea = new StartupIdea(rs.getString("id"), rs.getString("name"), rs.getString("rawIdea"), rs.getString("category"));
                idea.setProblemStatement(rs.getString("problemStatement"));
                idea.setTargetAudience(rs.getString("targetAudience"));
                idea.setUsp(rs.getString("usp"));
                idea.setFeaturesList(rs.getString("featuresList"));
                idea.setRevenueModel(rs.getString("revenueModel"));
                idea.setCompetitorAnalysis(rs.getString("competitorAnalysis"));
                idea.setMarketingStrategy(rs.getString("marketingStrategy"));
                idea.setRiskAnalysis(rs.getString("riskAnalysis"));
                idea.setFutureScope(rs.getString("futureScope"));
                idea.setMarketScope(rs.getString("marketScope"));
                idea.setCurrentMarketTrend(rs.getString("currentMarketTrend"));
                idea.setDifferentiation(rs.getString("differentiation"));
                idea.setUniquenessRecommendations(rs.getString("uniquenessRecommendations"));
                idea.setCustomerPersonas(rs.getString("customerPersonas"));
                idea.setSolution(rs.getString("solution"));
                idea.setTiming(rs.getString("timing"));
                idea.setBudget(rs.getString("budget"));
                idea.setTeamSize(rs.getString("teamSize"));
                idea.setTimeframe(rs.getString("timeframe"));
                idea.setSwotStrengths(rs.getString("swotStrengths"));
                idea.setSwotWeaknesses(rs.getString("swotWeaknesses"));
                idea.setSwotOpportunities(rs.getString("swotOpportunities"));
                idea.setSwotThreats(rs.getString("swotThreats"));
                idea.setTargetAge(rs.getString("targetAge"));
                idea.setTargetIncome(rs.getString("targetIncome"));
                idea.setTargetLocation(rs.getString("targetLocation"));
                idea.setB2bOrB2c(rs.getString("b2bOrB2c"));
                idea.setPricingStrategy(rs.getString("pricingStrategy"));
                idea.setInnovationScore(rs.getInt("innovationScore"));
                idea.setFeasibilityScore(rs.getInt("feasibilityScore"));
                idea.setMarketDemandScore(rs.getInt("marketDemandScore"));
                
                user.addIdea(idea);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> loadAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT username FROM users";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                users.add(loadUser(rs.getString("username")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
