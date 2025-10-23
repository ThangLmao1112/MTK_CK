package main.strategy;

/**
 * STRATEGY PATTERN - Concrete Strategy
 * 
 * Chiến lược chấm điểm theo tốc độ
 * Trả lời càng nhanh càng nhiều điểm
 */
public class SpeedScoreStrategy implements ScoringStrategy {
    private static final int BASE_SCORE = 10;
    private static final int BONUS_PER_FAST_SECOND = 2;
    private static final int FAST_THRESHOLD = 5; // Nếu trả lời dưới 5 giây = nhanh
    
    /**
     * STRATEGY PATTERN: Implement algorithm cụ thể
     * Tính điểm dựa trên tốc độ trả lời
     * Càng nhanh càng nhiều điểm bonus
     */
    @Override
    public int calculateScore(String difficulty, long timeTaken) {
        int score = BASE_SCORE;
        
        // Thêm điểm bonus nếu trả lời nhanh
        if (timeTaken < FAST_THRESHOLD) {
            int bonus = (int)(FAST_THRESHOLD - timeTaken) * BONUS_PER_FAST_SECOND;
            score += bonus;
        }
        
        // Có thể kết hợp với độ khó
        if (difficulty.equalsIgnoreCase("hard")) {
            score += 5;
        } else if (difficulty.equalsIgnoreCase("medium")) {
            score += 2;
        }
        
        return Math.max(score, 5); // Tối thiểu 5 điểm
    }
    
    @Override
    public String getStrategyName() {
        return "Speed Score Strategy (Điểm theo tốc độ)";
    }
}
