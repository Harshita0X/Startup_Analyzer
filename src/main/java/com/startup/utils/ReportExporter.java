package com.startup.utils;

import com.startup.models.StartupIdea;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportExporter {

    public static String exportToText(StartupIdea idea) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "StartupReport_" + idea.getCategory() + "_" + timestamp + ".txt";
        
        File reportsDir = new File("reports");
        if (!reportsDir.exists()) {
            reportsDir.mkdir();
        }
        
        File reportFile = new File(reportsDir, filename);

        try (PrintWriter out = new PrintWriter(new FileWriter(reportFile))) {
            out.println("=========================================");
            out.println("      STARTUP IDEA ANALYSIS REPORT");
            out.println("=========================================");
            out.println("ID: " + idea.getId());
            out.println("Category: " + idea.getCategory());
            out.println("Raw Idea: " + idea.getRawIdea());
            out.println("-----------------------------------------");
            out.println("Problem Statement:");
            out.println(idea.getProblemStatement());
            out.println("-----------------------------------------");
            out.println("Target Audience: " + idea.getTargetAudience());
            out.println("USP: " + idea.getUsp());
            out.println("-----------------------------------------");
            out.println("Expected Features:");
            out.println(idea.getFeaturesList());
            out.println("-----------------------------------------");
            out.println("Revenue Model: " + idea.getRevenueModel());
            out.println("Competitor Analysis: " + idea.getCompetitorAnalysis());
            out.println("Marketing Strategy: " + idea.getMarketingStrategy());
            out.println("Risk Analysis: " + idea.getRiskAnalysis());
            out.println("Future Scope: " + idea.getFutureScope());
            out.println("-----------------------------------------");
            out.println("      ADVANCED MARKET ANALYSIS");
            out.println("-----------------------------------------");
            out.println("Market Scope: " + idea.getMarketScope());
            out.println("Current Market Trend: " + idea.getCurrentMarketTrend());
            out.println("Differentiation:");
            out.println(idea.getDifferentiation());
            out.println("Uniqueness Recommendations:");
            out.println(idea.getUniquenessRecommendations());
            out.println("=========================================");
            out.println("SCORES:");
            out.println("Innovation: " + idea.getInnovationScore() + "/10");
            out.println("Market Demand: " + idea.getMarketDemandScore() + "/10");
            out.println("Feasibility: " + idea.getFeasibilityScore() + "/10");
            out.println("=========================================");
            
            return reportFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
