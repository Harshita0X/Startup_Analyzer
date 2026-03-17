package com.startup.engine;

import com.startup.models.StartupIdea;
import java.util.Random;

public class ScoringSystem {

    /**
     * Generates random but plausible scores based on keywords in the idea.
     */
    public static void generateScores(StartupIdea idea) {
        Random random = new Random();
        String text = idea.getRawIdea().toLowerCase();

        int innovation = 5 + random.nextInt(4); // Base 5-8
        int feasibility = 6 + random.nextInt(4); // Base 6-9
        int marketDemand = 6 + random.nextInt(4); // Base 6-9

        // Boost innovation if certain buzzwords are used
        if (text.contains("ai") || text.contains("blockchain") || text.contains("quantum")) {
            innovation = Math.min(10, innovation + 2);
            feasibility = Math.max(1, feasibility - 1); // Complex tech lowers feasibility slightly
        }

        // Boost market demand for trendy topics
        if (text.contains("health") || text.contains("delivery") || text.contains("remote")) {
            marketDemand = Math.min(10, marketDemand + 2);
        }

        // --- Phase 4: Feasibility Calculator Logic ---
        if (idea.getBudget() != null) {
            String b = idea.getBudget();
            if (b.equals("Under $10k")) feasibility -= 2;
            else if (b.equals("$250k+")) feasibility += 2;
            
            String t = idea.getTeamSize();
            if (t != null && t.equals("Solo Founder")) feasibility -= 1;
            else if (t != null && t.equals("6-15 Team")) feasibility += 1;
            
            String time = idea.getTimeframe();
            if (time != null && time.equals("< 3 Months")) feasibility -= 2; // Rushed
            else if (time != null && time.equals("6-12 Months")) feasibility += 1; // Good planning
        }
        
        // Ensure bounds
        feasibility = Math.max(1, Math.min(10, feasibility));
        innovation = Math.max(1, Math.min(10, innovation));
        marketDemand = Math.max(1, Math.min(10, marketDemand));

        idea.setInnovationScore(innovation);
        idea.setFeasibilityScore(feasibility);
        idea.setMarketDemandScore(marketDemand);
    }
}
