package com.startup.models;

import java.io.Serializable;

public class StartupIdea implements Serializable {
    private String id;
    private String name;
    private String rawIdea;
    private String category; // Tech, Food, Education, Health, Fashion

    // Analysis results
    private String reportFormat; // e.g. "Short Summary", "Investor Pitch", "Full Technical"
    
    private String problemStatement;
    private String targetAudience;
    private String usp;
    private String featuresList;
    private String revenueModel;
    private String competitorAnalysis;
    private String marketingStrategy;
    private String riskAnalysis;
    private String futureScope;
    
    // Advanced Analysis Results
    private String marketScope;
    private String currentMarketTrend;
    private String differentiation;
    private String uniquenessRecommendations;

    // Fedric AI Redesign Fields
    private String customerPersonas;
    private String solution;
    private String timing;

    // Phase 4 Inputs
    private String budget;
    private String teamSize;
    private String timeframe;

    // Phase 4 Generated Fields
    private String swotStrengths;
    private String swotWeaknesses;
    private String swotOpportunities;
    private String swotThreats;
    
    // Deep Target Audience
    private String targetAge;
    private String targetIncome;
    private String targetLocation;
    
    // Business Model specifics
    private String b2bOrB2c;
    private String pricingStrategy;

    // Scores
    private int innovationScore;
    private int feasibilityScore;
    private int marketDemandScore;

    public StartupIdea(String id, String name, String rawIdea, String category) {
        this.id = id;
        this.name = name;
        this.rawIdea = rawIdea;
        this.category = category;
    }

    // Getters and Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRawIdea() { return rawIdea; }
    public void setRawIdea(String rawIdea) { this.rawIdea = rawIdea; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getReportFormat() { return reportFormat; }
    public void setReportFormat(String reportFormat) { this.reportFormat = reportFormat; }

    public String getProblemStatement() { return problemStatement; }
    public void setProblemStatement(String problemStatement) { this.problemStatement = problemStatement; }

    public String getTargetAudience() { return targetAudience; }
    public void setTargetAudience(String targetAudience) { this.targetAudience = targetAudience; }

    public String getUsp() { return usp; }
    public void setUsp(String usp) { this.usp = usp; }

    public String getFeaturesList() { return featuresList; }
    public void setFeaturesList(String featuresList) { this.featuresList = featuresList; }

    public String getRevenueModel() { return revenueModel; }
    public void setRevenueModel(String revenueModel) { this.revenueModel = revenueModel; }

    public String getCompetitorAnalysis() { return competitorAnalysis; }
    public void setCompetitorAnalysis(String competitorAnalysis) { this.competitorAnalysis = competitorAnalysis; }

    public String getMarketingStrategy() { return marketingStrategy; }
    public void setMarketingStrategy(String marketingStrategy) { this.marketingStrategy = marketingStrategy; }

    public String getRiskAnalysis() { return riskAnalysis; }
    public void setRiskAnalysis(String riskAnalysis) { this.riskAnalysis = riskAnalysis; }

    public String getFutureScope() { return futureScope; }
    public void setFutureScope(String futureScope) { this.futureScope = futureScope; }

    public String getMarketScope() { return marketScope; }
    public void setMarketScope(String marketScope) { this.marketScope = marketScope; }

    public String getCurrentMarketTrend() { return currentMarketTrend; }
    public void setCurrentMarketTrend(String currentMarketTrend) { this.currentMarketTrend = currentMarketTrend; }

    public String getDifferentiation() { return differentiation; }
    public void setDifferentiation(String differentiation) { this.differentiation = differentiation; }

    public String getUniquenessRecommendations() { return uniquenessRecommendations; }
    public void setUniquenessRecommendations(String uniquenessRecommendations) { this.uniquenessRecommendations = uniquenessRecommendations; }

    public String getCustomerPersonas() { return customerPersonas; }
    public void setCustomerPersonas(String customerPersonas) { this.customerPersonas = customerPersonas; }

    public String getSolution() { return solution; }
    public void setSolution(String solution) { this.solution = solution; }

    public String getTiming() { return timing; }
    public void setTiming(String timing) { this.timing = timing; }

    public String getBudget() { return budget; }
    public void setBudget(String budget) { this.budget = budget; }

    public String getTeamSize() { return teamSize; }
    public void setTeamSize(String teamSize) { this.teamSize = teamSize; }

    public String getTimeframe() { return timeframe; }
    public void setTimeframe(String timeframe) { this.timeframe = timeframe; }

    public String getSwotStrengths() { return swotStrengths; }
    public void setSwotStrengths(String swotStrengths) { this.swotStrengths = swotStrengths; }

    public String getSwotWeaknesses() { return swotWeaknesses; }
    public void setSwotWeaknesses(String swotWeaknesses) { this.swotWeaknesses = swotWeaknesses; }

    public String getSwotOpportunities() { return swotOpportunities; }
    public void setSwotOpportunities(String swotOpportunities) { this.swotOpportunities = swotOpportunities; }

    public String getSwotThreats() { return swotThreats; }
    public void setSwotThreats(String swotThreats) { this.swotThreats = swotThreats; }

    public String getTargetAge() { return targetAge; }
    public void setTargetAge(String targetAge) { this.targetAge = targetAge; }

    public String getTargetIncome() { return targetIncome; }
    public void setTargetIncome(String targetIncome) { this.targetIncome = targetIncome; }

    public String getTargetLocation() { return targetLocation; }
    public void setTargetLocation(String targetLocation) { this.targetLocation = targetLocation; }

    public String getB2bOrB2c() { return b2bOrB2c; }
    public void setB2bOrB2c(String b2bOrB2c) { this.b2bOrB2c = b2bOrB2c; }

    public String getPricingStrategy() { return pricingStrategy; }
    public void setPricingStrategy(String pricingStrategy) { this.pricingStrategy = pricingStrategy; }



    public int getInnovationScore() { return innovationScore; }
    public void setInnovationScore(int innovationScore) { this.innovationScore = innovationScore; }

    public int getFeasibilityScore() { return feasibilityScore; }
    public void setFeasibilityScore(int feasibilityScore) { this.feasibilityScore = feasibilityScore; }

    public int getMarketDemandScore() { return marketDemandScore; }
    public void setMarketDemandScore(int marketDemandScore) { this.marketDemandScore = marketDemandScore; }
}
