package com.example.transportapp;

public class Recommendation {

    public String getCategory(double monthlyCost) {

        if (monthlyCost < 1000) {
            return "Low Cost";
        } else if (monthlyCost < 2000) {
            return "Moderate";
        } else if (monthlyCost < 3000) {
            return "High";
        } else if (monthlyCost < 5000) {
            return "Very High";
        } else {
            return "Excessive";
        }
    }

    public String getRecommendation(String category) {

        switch (category) {
            case "Low Cost":
                return "Your transport costs are low. This is a cost-effective option.";

            case "Moderate":
                return "Your transport costs are moderate. Consider comparing other transport options.";

            case "High":
                return "Your transport costs are high. Consider looking for a more affordable option.";

            case "Very High":
                return "Your transport costs are very high. Consider changing to a more cost-effective transport option.";

            default:
                return "Your transport costs are excessive. Consider significantly reducing your transport costs.";
        }
    }
}