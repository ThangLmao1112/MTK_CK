package main.question;

/**
 * DECORATOR PATTERN - Concrete Component
 * 
 * Câu hỏi trắc nghiệm (Multiple Choice)
 * Có 4 đáp án để chọn
 */
public class MultipleChoiceQuestion implements Question {
    private String questionText;
    private String correctAnswer;
    private String[] options; // 4 đáp án
    private String difficulty;
    
    public MultipleChoiceQuestion(String questionText, String correctAnswer, String[] options, String difficulty) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.options = options;
        this.difficulty = difficulty;
    }
    
    @Override
    public void display() {
        System.out.println("MULTIPLE CHOICE: " + questionText);
        System.out.println("Do kho: " + getDifficultyLabel());
        for (int i = 0; i < options.length; i++) {
            System.out.println((char)('A' + i) + ". " + options[i]);
        }
    }
    
    @Override
    public boolean checkAnswer(String answer) {
        // Có thể nhập A, B, C, D hoặc đáp án trực tiếp
        answer = answer.trim().toUpperCase();
        
        // Nếu nhập A, B, C, D
        if (answer.length() == 1 && answer.charAt(0) >= 'A' && answer.charAt(0) <= 'D') {
            int index = answer.charAt(0) - 'A';
            if (index < options.length) {
                return options[index].trim().equalsIgnoreCase(correctAnswer.trim());
            }
        }
        
        // Nếu nhập đáp án trực tiếp
        return correctAnswer.trim().equalsIgnoreCase(answer);
    }
    
    @Override
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    
    @Override
    public String getDifficulty() {
        return difficulty;
    }
    
    public String[] getOptions() {
        return options;
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
