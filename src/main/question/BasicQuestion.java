package main.question;

/**
 * DECORATOR PATTERN - Concrete Component
 * 
 * Câu hỏi cơ bản không có tính năng bổ sung
 * Đây là component gốc mà các decorator sẽ bọc lấy
 */
public class BasicQuestion implements Question {
    private String questionText;
    private String correctAnswer;
    private String difficulty; // easy, medium, hard
    
    public BasicQuestion(String questionText, String correctAnswer, String difficulty) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
    }
    
    @Override
    public void display() {
        System.out.println("TEXT INPUT: " + questionText);
        System.out.println("Do kho: " + getDifficultyLabel());
    }
    
    @Override
    public boolean checkAnswer(String answer) {
        // So sánh không phân biệt hoa thường và loại bỏ khoảng trắng thừa
        return correctAnswer.trim().equalsIgnoreCase(answer.trim());
    }
    
    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    @Override
    public String getDifficulty() {
        return difficulty;
    }
    
    public String getQuestionText() {
        return questionText;
    }
    
    private String getDifficultyLabel() {
        switch (difficulty.toLowerCase()) {
            case "easy":
                return "De";
            case "medium":
                return "Trung binh";
            case "hard":
                return "Kho";
            default:
                return difficulty;
        }
    }
}
