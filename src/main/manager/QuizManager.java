package main.manager;

import main.question.Question;
import main.strategy.ScoringStrategy;
import main.strategy.FixedScoreStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 * SINGLETON PATTERN
 * 
 * QuizManager quản lý toàn bộ hệ thống quiz
 * Đảm bảo chỉ có MỘT instance duy nhất trong toàn bộ ứng dụng
 * 
 * Lý do dùng Singleton:
 * - Quản lý tập trung dữ liệu câu hỏi, điểm số, người chơi
 * - Tránh tạo nhiều instance gây lãng phí bộ nhớ
 * - Đảm bảo tính nhất quán của dữ liệu trong toàn chương trình
 */
public class QuizManager {
    
    // SINGLETON PATTERN: Instance duy nhất (static)
    private static QuizManager instance;
    
    // Dữ liệu quản lý
    private List<Question> questions;
    private int totalScore;
    private String playerName;
    
    // STRATEGY PATTERN: Chiến lược chấm điểm có thể thay đổi
    private ScoringStrategy scoringStrategy;
    
    /**
     * SINGLETON PATTERN: Constructor private
     * Ngăn không cho tạo instance từ bên ngoài
     */
    private QuizManager() {
        this.questions = new ArrayList<>();
        this.totalScore = 0;
        this.playerName = "Player";
        // Chiến lược mặc định
        this.scoringStrategy = new FixedScoreStrategy();
    }
    
    /**
     * SINGLETON PATTERN: Method public để lấy instance duy nhất
     * Lazy initialization: Chỉ tạo instance khi cần
     * Thread-safe với synchronized
     */
    public static synchronized QuizManager getInstance() {
        if (instance == null) {
            instance = new QuizManager();
        }
        return instance;
    }
    
    /**
     * STRATEGY PATTERN: Thay đổi chiến lược chấm điểm linh hoạt
     * Cho phép thay đổi cách tính điểm mà không sửa code QuizManager
     */
    public void setScoringStrategy(ScoringStrategy strategy) {
        this.scoringStrategy = strategy;
    }
    
    /**
     * STRATEGY PATTERN: Tính điểm dựa trên strategy hiện tại
     */
    public int calculateScore(String difficulty, long timeTaken) {
        return scoringStrategy.calculateScore(difficulty, timeTaken);
    }
    
    // Quản lý câu hỏi
    public void addQuestion(Question question) {
        questions.add(question);
    }
    
    public Question getQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }
    
    public int getTotalQuestions() {
        return questions.size();
    }
    
    // Quản lý điểm
    public void addScore(int score) {
        this.totalScore += score;
    }
    
    public int getTotalScore() {
        return totalScore;
    }
    
    public void resetScore() {
        this.totalScore = 0;
    }
    
    // Quản lý người chơi
    public void setPlayerName(String name) {
        this.playerName = name;
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    // Lấy tên strategy hiện tại
    public String getScoringStrategyName() {
        return scoringStrategy.getStrategyName();
    }
    
    /**
     * Reset toàn bộ quiz (nếu cần chơi lại)
     */
    public void resetQuiz() {
        questions.clear();
        totalScore = 0;
    }
    
    /**
     * SINGLETON PATTERN: Ngăn clone object
     * Đảm bảo không thể tạo bản sao của instance
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cannot clone singleton instance");
    }
}
