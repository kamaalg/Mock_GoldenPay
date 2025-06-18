package com.example.mock_golden_pay.dto;

public enum CreditTier {
    NONE(0,   0),
    BRONZE(345, 10),
    SILVER(2500, 9),
    GOLD(8000, 8);

    private final int salary;
    private final int percentage;

    CreditTier(int minScore, int percentage) {
        this.salary   = minScore;
        this.percentage = percentage;
    }

    /**
     * Returns the highest tier whose minScore ≤ score.
     * Assumes the constants are declared in ascending order by minScore.
     */
    public static int percentageFor(int score) {
        CreditTier match = NONE;
        for (CreditTier tier : values()) {
            if (score >= tier.salary) {
                match = tier;
            } else {
                break;
            }
        }
        return match.percentage;
    }
}
