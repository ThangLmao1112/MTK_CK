package main.question;

/**
 * DECORATOR PATTERN - Component Interface
 * 
 * Interface cơ bản cho tất cả các câu hỏi
 * Định nghĩa các phương thức mà cả câu hỏi cơ bản và decorator đều phải implement
 * 
 * Lý do dùng interface:
 * - Cho phép decorator và concrete class có cùng kiểu
 * - Dễ dàng thêm decorator mới mà không sửa code cũ
 */
public interface Question {
    /**
     * Hiển thị câu hỏi (có thể được decorator thêm tính năng)
     */
    void display();
    
    /**
     * Kiểm tra câu trả lời có đúng không
     */
    boolean checkAnswer(String answer);
    
    /**
     * Lấy đáp án đúng
     */
    String getCorrectAnswer();
    
    /**
     * Lấy độ khó của câu hỏi (easy, medium, hard)
     */
    String getDifficulty();
}
