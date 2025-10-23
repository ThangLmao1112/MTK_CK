package main.strategy;

/**
 * STRATEGY PATTERN - Strategy Interface
 * 
 * Định nghĩa interface chung cho tất cả các chiến lược chấm điểm
 * Cho phép các algorithm chấm điểm khác nhau được sử dụng thay thế lẫn nhau
 * 
 * Lý do dùng Strategy Pattern:
 * - Tách riêng logic chấm điểm khỏi QuizManager
 * - Dễ dàng thêm chiến lược mới mà không sửa code cũ
 * - Thay đổi chiến lược trong runtime
 * - Tuân theo Single Responsibility Principle
 */
public interface ScoringStrategy {
    /**
     * Tính điểm dựa trên độ khó và thời gian làm bài
     * 
     * @param difficulty Độ khó: "easy", "medium", "hard"
     * @param timeTaken Thời gian làm bài (giây)
     * @return Điểm số
     */
    int calculateScore(String difficulty, long timeTaken);
    
    /**
     * Lấy tên của chiến lược (để hiển thị)
     */
    String getStrategyName();
}
