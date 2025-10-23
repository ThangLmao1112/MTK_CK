package main.question;

/**
 * DECORATOR PATTERN - Concrete Decorator
 * 
 * Thêm tính năng giới hạn thời gian cho câu hỏi
 * Hiển thị thời gian cho phép khi display()
 */
public class TimedQuestion extends QuestionDecorator {
    private int timeLimit; // giây
    
    public TimedQuestion(Question question, int timeLimit) {
        super(question);
        this.timeLimit = timeLimit;
    }
    
    /**
     * DECORATOR PATTERN: Mở rộng chức năng display()
     * Thêm thông báo về thời gian giới hạn
     */
    @Override
    public void display() {
        // Hiển thị câu hỏi gốc
        decoratedQuestion.display();
        
        // Thêm tính năng mới: hiển thị thời gian
        System.out.println("[THOI GIAN] " + timeLimit + " giay");
    }
    
    public int getTimeLimit() {
        return timeLimit;
    }
}
