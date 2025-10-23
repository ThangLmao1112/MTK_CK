package main.question;

/**
 * DECORATOR PATTERN - Abstract Decorator
 * 
 * Class trừu tượng cho tất cả các decorator
 * Giữ reference đến Question được decorate và ủy quyền các method cho nó
 * 
 * Lý do dùng Decorator Pattern:
 * - Thêm tính năng động cho câu hỏi mà không sửa code gốc
 * - Có thể kết hợp nhiều decorator (hint + timed + double score, v.v.)
 * - Tuân theo Open/Closed Principle: mở để mở rộng, đóng để sửa đổi
 */
public abstract class QuestionDecorator implements Question {
    // Reference đến Question được decorate
    public Question decoratedQuestion;
    
    public QuestionDecorator(Question question) {
        this.decoratedQuestion = question;
    }
    
    // Ủy quyền các method cơ bản cho question gốc
    @Override
    public boolean checkAnswer(String answer) {
        return decoratedQuestion.checkAnswer(answer);
    }
    
    @Override
    public String getCorrectAnswer() {
        return decoratedQuestion.getCorrectAnswer();
    }
    
    @Override
    public String getDifficulty() {
        return decoratedQuestion.getDifficulty();
    }
    
    // display() sẽ được override bởi các concrete decorator
    @Override
    public abstract void display();
}
