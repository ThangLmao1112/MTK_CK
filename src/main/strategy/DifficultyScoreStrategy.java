package main.strategy;

/**
 * STRATEGY PATTERN - Concrete Strategy
 * 
 * Chiến lược chấm điểm theo độ khó
 * Câu dễ ít điểm, câu khó nhiều điểm
 */
public class DifficultyScoreStrategy implements ScoringStrategy {
    private static final int EASY_SCORE = 5;
    private static final int MEDIUM_SCORE = 10;
    private static final int HARD_SCORE = 15;
    
    /**
     * STRATEGY PATTERN: Implement algorithm cụ thể
     * Tính điểm dựa trên độ khó của câu hỏi
     */
    @Override
    public int calculateScore(String difficulty, long timeTaken) {
        switch (difficulty.toLowerCase()) {
            case "easy":
                return EASY_SCORE;
            case "medium":
                return MEDIUM_SCORE;
            case "hard":
                return HARD_SCORE;
            default:
                return MEDIUM_SCORE; // Mặc định
        }
    }
    
    @Override
    public String getStrategyName() {
        return "Difficulty Score Strategy (Điểm theo độ khó)";
    }
}
