package main.question;

/**
 * DECORATOR PATTERN - Concrete Decorator
 * 
 * Thêm tính năng gợi ý (hint) cho câu hỏi
 * Khi display(), hiển thị cả câu hỏi gốc và gợi ý
 */
public class HintQuestion extends QuestionDecorator {
    private String hint;
    
    public HintQuestion(Question question, String hint) {
        super(question);
        this.hint = hint;
    }
    
    /**
     * DECORATOR PATTERN: Mở rộng chức năng display()
     * Gọi display() của question gốc, sau đó thêm hint
     */
    @Override
    public void display() {
        // Hiển thị câu hỏi gốc
        decoratedQuestion.display();
        
        // Thêm tính năng mới: hiển thị gợi ý
        System.out.println("[GOI Y] " + hint);
    }
    
    public String getHint() {
        return hint;
    }
}
