package com.startup.engine;

import com.startup.models.StartupIdea;
import java.util.HashMap;
import java.util.Map;

public class AnalysisEngine {

    private Map<String, String> techKeywords;
    private Map<String, String> foodKeywords;
    private Map<String, String> healthKeywords;
    private Map<String, String> eduKeywords;
    // Add fashion or others as needed

    public AnalysisEngine() {
        initializeKeywords();
    }

    private void initializeKeywords() {
        techKeywords = new HashMap<>();
        techKeywords.put("ai", "Incorporate Artificial Intelligence to automate processes and provide predictive insights.");
        techKeywords.put("app", "Develop a seamless mobile application for iOS and Android platforms.");
        techKeywords.put("blockchain", "Utilize blockchain technology for secure, transparent, and decentralized transactions.");
        techKeywords.put("saas", "Offer a Software as a Service (SaaS) model with subscription tiers.");

        foodKeywords = new HashMap<>();
        foodKeywords.put("vegan", "Target health-conscious and ethically-minded consumers with a plant-based menu.");
        foodKeywords.put("delivery", "Partner with local delivery services or establish an in-house fleet for rapid food delivery.");
        foodKeywords.put("organic", "Source ingredients from local organic farms to guarantee freshness and low environmental impact.");

        healthKeywords = new HashMap<>();
        healthKeywords.put("fitness", "Provide customizable workout routines and diet plans.");
        healthKeywords.put("mental", "Focus on mental well-being by offering meditation guides and tele-therapy.");
        healthKeywords.put("tracker", "Integrate with wearable devices to monitor vitals in real-time.");

        eduKeywords = new HashMap<>();
        eduKeywords.put("kids", "Create gamified learning modules tailored for elementary students.");
        eduKeywords.put("coding", "Offer interactive programming tutorials and a built-in code editor.");
        eduKeywords.put("language", "Connect users with native speakers for immersive language practice.");
    }

    public void analyze(StartupIdea idea) {
        String category = idea.getCategory();
        String rawText = idea.getRawIdea().toLowerCase();

        // 1. Problem Statement
        idea.setProblemStatement(generateProblemStatement(category, rawText, idea.getReportFormat()));

        // 2. Target Audience
        idea.setTargetAudience(generateTargetAudience(category, rawText, idea.getReportFormat()));

        // 3. Unique Selling Proposition (USP)
        idea.setUsp(generateUSP(category, rawText, idea.getReportFormat()));

        // 4. Features List based on Keywords (HashMap)
        idea.setFeaturesList(extractFeatures(category, rawText, idea.getReportFormat()));

        // 5. Revenue Model
        idea.setRevenueModel(generateRevenueModel(category, rawText, idea.getReportFormat()));

        // 6. Competitor Analysis
        idea.setCompetitorAnalysis(generateCompetitorAnalysis(category, idea.getReportFormat()));

        // 7. Marketing Strategy
        idea.setMarketingStrategy(generateMarketingStrategy(category, idea.getReportFormat()));

        // 8. Risk Analysis
        idea.setRiskAnalysis(generateRiskAnalysis(category, idea.getReportFormat()));

        // 9. Future Scope
        idea.setFutureScope(generateFutureScope(category, idea.getReportFormat()));

        // 10. Advanced Analysis
        idea.setMarketScope(generateMarketScope(category, idea.getReportFormat()));
        idea.setCurrentMarketTrend(generateMarketTrend(category, idea.getReportFormat()));
        idea.setDifferentiation(generateDifferentiation(category, rawText, idea.getReportFormat()));
        idea.setUniquenessRecommendations(generateUniquenessRecommendations(category, rawText, idea.getReportFormat()));

        // 11. Fedric AI Redesign Fields
        idea.setCustomerPersonas(generateCustomerPersonas(category, rawText, idea.getReportFormat()));
        idea.setSolution(generateSolution(category, rawText, idea.getReportFormat()));
        idea.setTiming(generateTiming(category, idea.getReportFormat()));

        // 12. Phase 4 Advanced Features
        idea.setSwotStrengths(generateSwotStrengths(category, rawText));
        idea.setSwotWeaknesses(generateSwotWeaknesses(category, rawText));
        idea.setSwotOpportunities(generateSwotOpportunities(category, rawText));
        idea.setSwotThreats(generateSwotThreats(category, rawText));
        
        idea.setTargetAge(generateTargetAge(category));
        idea.setTargetIncome(generateTargetIncome(category));
        idea.setTargetLocation(generateTargetLocation(category));

        idea.setB2bOrB2c(generateB2bOrB2c(category, rawText));
        idea.setPricingStrategy(generatePricingStrategy(category, rawText));

        // Score generation
        ScoringSystem.generateScores(idea);
    }

    private String generateProblemStatement(String category, String rawText, String format) {
        if ("Short Summary".equals(format)) return "Current " + category + " solutions are inefficient.";
        String base = "Current solutions in the " + category + " space are either too expensive, inefficient, or lack specific features addressed by this idea. ";
        if ("Investor Pitch (Concise)".equals(format)) return "The Problem: " + base + "We are losing billions in unoptimized workflows.";
        return base + "\nTechnical debt in legacy systems prevents rapid iteration, creating a structural gap that this solution fills using modern architecture.";
    }

    private String generateTargetAudience(String category, String rawText, String format) {
        String base;
        switch (category.toLowerCase()) {
            case "tech": base = "Tech-savvy individuals, SMEs looking for automation."; break;
            case "food": base = "Foodies, busy professionals, health-conscious eaters."; break;
            case "education": base = "Students, lifetime learners, educational institutions."; break;
            case "health": base = "Fitness enthusiasts, patients needing remote care, elderly."; break;
            case "fashion": base = "Trend-conscious youth, sustainable fashion advocates."; break;
            default: base = "General consumers interested in " + category + ".";
        }
        if ("Short Summary".equals(format)) return base;
        if ("Investor Pitch (Concise)".equals(format)) return "Primary: " + base + " secondary: Enterprise.";
        return base + " Detailed breakdown: 60% B2C focus initially, transitioning to 80% B2B ARR within 36 months.";
    }

    private String generateUSP(String category, String rawText, String format) {
        String base = "A highly niche, targeted approach in the " + category + " industry prioritizing user experience and affordability.";
        if ("Short Summary".equals(format)) return base;
        if ("Investor Pitch (Concise)".equals(format)) return "Our Moat: " + base + " First to market with this specific combo.";
        return base + " Architecturally, the system leverages decoupled microservices allowing 5x faster feature deployment than legacy competitors.";
    }

    private String extractFeatures(String category, String rawText, String format) {
        StringBuilder features = new StringBuilder();
        Map<String, String> currentMap = getMapForCategory(category);

        boolean found = false;
        for (String keyword : currentMap.keySet()) {
            if (rawText.contains(keyword)) {
                features.append("- ").append(currentMap.get(keyword)).append("\n");
                found = true;
            }
        }

        if (!found) {
            features.append("- Core functional prototype for ").append(category).append(".\n");
            features.append("- User-friendly dashboard and profile management.\n");
        }
        
        if ("Full Technical Breakdown".equals(format)) {
            features.append("- API Gateway & OAuth 2.0 Integration\n");
            features.append("- Real-time WebSocket event streaming\n");
            features.append("- Containerized deployments via Docker/Kubernetes\n");
        }

        return features.toString().trim();
    }

    private Map<String, String> getMapForCategory(String category) {
        switch (category.toLowerCase()) {
            case "tech": return techKeywords;
            case "food": return foodKeywords;
            case "health": return healthKeywords;
            case "education": return eduKeywords;
            default: return new HashMap<>(); // Empty map for generic
        }
    }

    private String generateRevenueModel(String category, String rawText, String format) {
        String base = "Tiered subscription model, singular product sales, and targeted advertising.";
        if (rawText.contains("app") || rawText.contains("saas")) {
            base = "Freemium model with monthly/yearly subscriptions + In-app purchases.";
        }
        if ("Short Summary".equals(format)) return base;
        if ("Investor Pitch (Concise)".equals(format)) return "High Margin: " + base + " Projected 80% Gross Margin.";
        return base + " Includes Enterprise SLAs, API call metering ($0.001/req), and white-label licensing options.";
    }

    private String generateCompetitorAnalysis(String category, String format) {
        String base = "Existing giants and local startups. Requires a SWOT analysis to identify specific market gaps currently unfulfilled.";
        if ("Short Summary".equals(format)) return base;
        return base + " Key weakness in competitors: Monolithic architecture preventing rapid adoption of AI paradigms.";
    }

    private String generateMarketingStrategy(String category, String format) {
        String base = "Social media marketing (Instagram/TikTok), influencer partnerships, content marketing (blogs/SEO), and local community engagement.";
        if ("Short Summary".equals(format)) return base;
        if ("Investor Pitch (Concise)".equals(format)) return "Go-to-Market: Product-led growth (PLG), viral loops, and " + base;
        return base + " Technical SEO focus on long-tail programmatic pages. Building open-source tools as lead-magnets for enterprise sales.";
    }
    
    private String generateRiskAnalysis(String category, String format) {
        String base = "High competition in " + category + " sector, initial customer acquisition cost, tech scalability issues.";
        if ("Short Summary".equals(format)) return base;
        if ("Investor Pitch (Concise)".equals(format)) return "Mitigated Risks: " + base + " We offset this via strategic partnerships.";
        return base + " System architecture requires senior DevOps. Cloud infrastructure costs could balloon during hyper-growth without strict auto-scaling policies.";
    }

    private String generateFutureScope(String category, String format) {
        String base = "Expansion to international markets, integration of advanced AI analytics, building an active community forum.";
        if ("Short Summary".equals(format)) return base;
        return base + " Building a proprietary LLM fine-tuned on user generation data to create an impenetrable data moat.";
    }

    private String generateMarketScope(String category, String format) {
        String base;
        switch (category.toLowerCase()) {
            case "tech": base = "Global reach, multi-billion dollar Total Addressable Market (TAM), expanding rapidly with remote work & automation adoption."; break;
            case "food": base = "Primarily local to regional TAM initially, expanding nationally. Extremely high frequency of purchase."; break;
            case "education": base = "Massive global e-learning market projected to reach $1T by 2030, accessible via internet penetration."; break;
            case "health": base = "A growing, non-discretionary market driven by aging populations and preventive wellness awareness."; break;
            case "fashion": base = "Highly fragmented, high-margin global market with rapid turnover and shifting sustainability priorities."; break;
            default: base = "Scalable total addressable market with potential for both B2B and B2C segments.";
        }
        if ("Short Summary".equals(format)) return base;
        return base + " SAM (Serviceable Addressable Market) calculated at 15% of TAM in year 1.";
    }

    private String generateMarketTrend(String category, String format) {
        String base;
        switch (category.toLowerCase()) {
            case "tech": base = "Hyper-automation, generative AI native applications, zero-trust security architecture, and edge computing."; break;
            case "food": base = "Plant-based alternatives, ultra-fast delivery, ghost kitchens, and sustainable packaging."; break;
            case "education": base = "Micro-learning, AI-tutors, skill-based over degree-based hiring, and immersive VR classrooms."; break;
            case "health": base = "Telehealth first approach, wearable integration for preventative care, and personalized medicine via genetics."; break;
            case "fashion": base = "Circular economy (resale/rental), AR try-ons, and hyper-personalized direct-to-consumer funnels."; break;
            default: base = "Shift towards subscription models, community-led growth, and AI-driven personalization.";
        }
        return base; // Real-time dynamics added here in future API
    }

    private String generateDifferentiation(String category, String rawIdea, String format) {
        String base;
        if (rawIdea.toLowerCase().contains("ai") || rawIdea.toLowerCase().contains("smart")) {
            base = "Unlike competitors relying on static logic, this leverages dynamic intelligence to automate the user journey, dramatically reducing drop-off rates.";
        } else if (rawIdea.toLowerCase().contains("local") || rawIdea.toLowerCase().contains("community")) {
            base = "Differentiates by building a high-trust localized network, whereas current giants operate on a cold, disconnected global scale.";
        } else {
            base = "A refined, frictionless user experience combined with an aggressively optimized pricing model that undercuts legacy platforms.";
        }
        if ("Full Technical Breakdown".equals(format)) return base + " Tech Stack difference: Migration from standard SQL to distributed NoSQL graphs for relationship mapping.";
        return base;
    }

    private String generateUniquenessRecommendations(String category, String rawIdea, String format) {
        if ("Short Summary".equals(format)) return "1. Niche Down\n2. Gamification\n3. Proprietary Data";
        return """
               1. Niche Down: Target a very specific sub-segment of the %s market before expanding.
               2. Gamification: Introduce a subtle reward system to increase Daily Active Users naturally.
               3. Proprietary Data: Ensure your platform captures unique behavioral data that competitors don't have.
               4. 'Aha' Moment: Optimize onboarding so users experience the core value within the first 60 seconds.
               """.formatted(category);
    }

    private String generateCustomerPersonas(String category, String rawIdea, String format) {
        String p1, p2, p3;
        switch (category.toLowerCase()) {
            case "tech":
                p1 = "Alex, the IT Manager — Age: 36 | Goals: Automate operations | Pain points: Legacy integrations.";
                p2 = "Sam, the Freelance Developer — Age: 28 | Goals: Faster shipping | Pain points: DevOps overhead.";
                p3 = "Jordan, the Startup CTO — Age: 42 | Goals: Scale infrastructure | Pain points: Cloud cost spikes.";
                break;
            case "food":
                p1 = "Emma, the Health Conscious Consumer — Age: 30 | Goals: Eat organic | Pain points: Limited local options.";
                p2 = "David, the Busy Executive — Age: 45 | Goals: Quick meals | Pain points: Unhealthy fast food.";
                p3 = "Sarah, the Vegan Foodie — Age: 25 | Goals: Discover new tastes | Pain points: Cross-contamination in restaurants.";
                break;
            case "health":
                p1 = "Michael, the Fitness Enthusiast — Age: 29 | Goals: Track macros | Pain points: Disjointed apps.";
                p2 = "Linda, the Remote Patient — Age: 65 | Goals: Manage chronic condition | Pain points: Complicated interfaces.";
                p3 = "Dr. Patel, the General Practitioner — Age: 50 | Goals: Monitor patients | Pain points: Too much paperwork.";
                break;
            case "education":
                p1 = "Chloe, the College Student — Age: 20 | Goals: Ace exams | Pain points: Expensive tutoring.";
                p2 = "Mark, the Upskilling Professional — Age: 34 | Goals: Learn coding | Pain points: Lack of practical projects.";
                p3 = "Ms. Davis, the High School Teacher — Age: 41 | Goals: Engage students | Pain points: Boring curriculum tools.";
                break;
            default:
                p1 = "Persona A, the Enthusiast — Age: 30 | Goals: Maximizing utility | Pain points: Pricing barriers.";
                p2 = "Persona B, the Professional — Age: 40 | Goals: Streamlining work | Pain points: Inefficiency.";
                p3 = "Persona C, the Skeptic — Age: 25 | Goals: Trying new tech | Pain points: Steep learning curve.";
                break;
        }
        return p1 + "\n\n" + p2 + "\n\n" + p3;
    }

    private String generateSolution(String category, String rawIdea, String format) {
        return "This solution delivers hyper-localized, specialized tools combined with predictive modeling to directly serve the " + category + " market. Users receive real-time alerts and visualizations showing exactly how they can optimize their daily workflows. The platform integrates seamlessly with existing tech stacks, deploying AI-driven movement predictions to provide actionable insights. A mobile app and API enable seamless access for both consumers and enterprise partners.";
    }

    private String generateTiming(String category, String format) {
        return "Advances in " + category + " tech and AI allow market dynamics to be predicted with unprecedented accuracy. Growing interest in specialized, niched-down digital experiences creates demand for precise data. The surge in remote work and digitization globally also fuels a perfect storm for personalized, vertical-specific SaaS solutions right now.";
    }

    // --- Phase 4 Generators ---

    private String generateSwotStrengths(String category, String rawIdea) {
        if (rawIdea.toLowerCase().contains("ai")) return "High technical innovation; Automation capabilities; Low long-term marginal cost.";
        return "Niche focused user base; Deep understanding of " + category + " pain points; Lean initial deployment.";
    }

    private String generateSwotWeaknesses(String category, String rawIdea) {
        if (category.equalsIgnoreCase("Tech")) return "High upfront development cost; Reliance on cloud infrastructure stability.";
        if (category.equalsIgnoreCase("Food")) return "Supply chain vulnerabilities; Short shelf-life logistics; High local competition.";
        return "Brand anonymity in year 1; Potential scaling bottlenecks without heavy funding.";
    }

    private String generateSwotOpportunities(String category, String rawIdea) {
        return "Expanding into adjacent verticals; B2B enterprise licensing; Potential for data monetization once scale is reached in the " + category + " sector.";
    }

    private String generateSwotThreats(String category, String rawIdea) {
        return "Incumbents lowering prices to squeeze out new entrants; Fast-following copycats if IP isn't defensible; Regulatory shifts in " + category + ".";
    }

    private String generateTargetAge(String category) {
        return switch (category.toLowerCase()) {
            case "education" -> "15 - 35 (Students & Upskillers)";
            case "health" -> "25 - 65+ (Broad spectrum)";
            case "fashion" -> "18 - 34 (Gen Z & Millennials)";
            default -> "25 - 45 (Core Professionals)";
        };
    }

    private String generateTargetIncome(String category) {
        return switch (category.toLowerCase()) {
            case "tech", "health" -> "$60k - $150k+ (Mid to High)";
            case "food", "fashion" -> "$30k - $80k (Mass Market to Premium)";
            default -> "$50k - $100k (Middle Class)";
        };
    }

    private String generateTargetLocation(String category) {
        return switch (category.toLowerCase()) {
            case "food", "health" -> "Dense Urban / Metro Areas";
            case "tech", "education" -> "Global / Remote / Tier-1 Tech Hubs";
            default -> "Suburban & Urban mix";
        };
    }

    private String generateB2bOrB2c(String category, String rawIdea) {
        if (rawIdea.toLowerCase().contains("enterprise") || rawIdea.toLowerCase().contains("business")) {
            return "B2B (Business-to-Business)";
        }
        if (rawIdea.toLowerCase().contains("consumer") || category.equalsIgnoreCase("Fashion")) {
            return "B2C (Business-to-Consumer)";
        }
        return "B2B2C (Business-to-Business-to-Consumer)";
    }

    private String generatePricingStrategy(String category, String rawIdea) {
        if (rawIdea.toLowerCase().contains("subscription") || rawIdea.toLowerCase().contains("monthly")) {
            return "Subscription-based MMR ($15-$99/mo)";
        }
        if (category.equalsIgnoreCase("Food") || category.equalsIgnoreCase("Fashion")) {
            return "Per-unit Transactional / Commission Override (10-20%)";
        }
        return "Freemium with Enterprise Tiering";
    }
}
