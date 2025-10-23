package main.strategy;

/**
 * STRATEGY PATTERN - Concrete Strategy
 * 
 * Chiến lược chấm điểm cố định
 * Mọi câu hỏi đúng đều nhận điểm như nhau, bất kể độ khó hay thời gian
 */
public class FixedScoreStrategy implements ScoringStrategy {
    private static final int FIXED_SCORE = 10;
    
    /**
     * STRATEGY PATTERN: Implement algorithm cụ thể
     * Trả về điểm cố định cho mọi câu trả lời đúng
     */
    @Override
    public int calculateScore(String difficulty, long timeTaken) {
        // Điểm cố định, không phụ thuộc vào difficulty hay timeTaken
        return FIXED_SCORE;
    }
    
    @Override
    public String getStrategyName() {
        return "Fixed Score Strategy (Điểm cố định)";
    }
}
