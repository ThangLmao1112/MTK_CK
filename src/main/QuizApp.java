package main;

import main.manager.QuizManager;
import main.question.*;
import main.strategy.*;
import java.util.Scanner;

/**
 * Quiz Learning App - Main Application
 * Demonstrates: Singleton, Decorator, and Strategy Design Patterns
 */
public class QuizApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // SINGLETON PATTERN: Lấy instance duy nhất của QuizManager
        QuizManager quizManager = QuizManager.getInstance();
        
        System.out.println("╔═══════════════════════════════════════════╗");
        System.out.println("║    CHÀO MỪNG ĐÉN VỚI QUIZ LEARNING APP   ║");
        System.out.println("╚═══════════════════════════════════════════╝");
        System.out.println();
        
        // Nhập tên người chơi
        System.out.print("Nhập tên của bạn: ");
        String playerName = scanner.nextLine();
        quizManager.setPlayerName(playerName);
        
        // Chọn chiến lược chấm điểm (STRATEGY PATTERN)
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│  CHỌN CHIẾN LƯỢC CHẤM ĐIỂM:        │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│ 1. Fixed Score (Điểm cố định)       │");
        System.out.println("│ 2. Difficulty Score (Theo độ khó)  │");
        System.out.println("│ 3. Speed Score (Theo tốc độ)        │");
        System.out.println("└─────────────────────────────────────┘");
        System.out.print("Lựa chọn của bạn: ");
        
        int strategyChoice = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        
        // STRATEGY PATTERN: Thay đổi chiến lược chấm điểm linh hoạt
        switch (strategyChoice) {
            case 1:
                quizManager.setScoringStrategy(new FixedScoreStrategy());
                System.out.println("✓ Đã chọn: Điểm cố định (10 điểm/câu)\n");
                break;
            case 2:
                quizManager.setScoringStrategy(new DifficultyScoreStrategy());
                System.out.println("✓ Đã chọn: Điểm theo độ khó (Dễ: 5, Trung bình: 10, Khó: 15)\n");
                break;
            case 3:
                quizManager.setScoringStrategy(new SpeedScoreStrategy());
                System.out.println("✓ Đã chọn: Điểm theo tốc độ (Càng nhanh càng cao điểm)\n");
                break;
            default:
                quizManager.setScoringStrategy(new FixedScoreStrategy());
                System.out.println("✓ Mặc định: Điểm cố định\n");
        }
        
        // Tạo danh sách câu hỏi
        setupQuestions(quizManager);
        
        // Bắt đầu quiz
        System.out.println("════════════════════════════════════════════");
        System.out.println("           BẮT ĐẦU QUIZ!");
        System.out.println("════════════════════════════════════════════\n");
        
        // Thực hiện quiz
        for (int i = 0; i < quizManager.getTotalQuestions(); i++) {
            Question question = quizManager.getQuestion(i);
            
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("CÂU HỎI " + (i + 1) + "/" + quizManager.getTotalQuestions());
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            
            // Hiển thị câu hỏi (DECORATOR PATTERN có thể thêm gợi ý, thời gian, v.v.)
            question.display();
            
            System.out.print("\nCâu trả lời của bạn: ");
            long startTime = System.currentTimeMillis();
            String answer = scanner.nextLine();
            long endTime = System.currentTimeMillis();
            long timeTaken = (endTime - startTime) / 1000; // seconds
            
            // Kiểm tra câu trả lời
            boolean isCorrect = question.checkAnswer(answer);
            
            if (isCorrect) {
                System.out.println("✓ CHÍNH XÁC!");
                int score = quizManager.calculateScore(question.getDifficulty(), timeTaken);
                quizManager.addScore(score);
                System.out.println("Điểm nhận được: " + score);
            } else {
                System.out.println("✗ SAI RỒI!");
                System.out.println("Đáp án đúng: " + question.getCorrectAnswer());
            }
            
            System.out.println("Tổng điểm hiện tại: " + quizManager.getTotalScore());
            System.out.println();
        }
        
        // Hiển thị kết quả cuối cùng
        displayFinalResults(quizManager);
        
        scanner.close();
    }
    
    /**
     * Thiết lập các câu hỏi cho quiz
     * DECORATOR PATTERN: Thêm các tính năng bổ sung cho câu hỏi
     */
    private static void setupQuestions(QuizManager quizManager) {
        // Câu hỏi 1: Câu hỏi cơ bản
        Question q1 = new BasicQuestion(
            "Java được phát triển bởi công ty nào?",
            "Sun Microsystems",
            "easy"
        );
        quizManager.addQuestion(q1);
        
        // Câu hỏi 2: DECORATOR - Thêm gợi ý
        Question q2 = new BasicQuestion(
            "Design pattern nào đảm bảo chỉ có một instance duy nhất?",
            "Singleton",
            "medium"
        );
        // DECORATOR PATTERN: Thêm chức năng gợi ý cho câu hỏi
        q2 = new HintQuestion(q2, "Gợi ý: Pattern này bắt đầu bằng chữ 'S'");
        quizManager.addQuestion(q2);
        
        // Câu hỏi 3: DECORATOR - Thêm nhiều decorator (Hint + Timed)
        Question q3 = new BasicQuestion(
            "Từ khóa nào dùng để kế thừa class trong Java?",
            "extends",
            "hard"
        );
        // DECORATOR PATTERN: Có thể chain nhiều decorator
        q3 = new HintQuestion(q3, "Gợi ý: Từ khóa này có 7 chữ cái");
        q3 = new TimedQuestion(q3, 30); // 30 giây
        quizManager.addQuestion(q3);
    }
    
    /**
     * Hiển thị kết quả cuối cùng
     */
    private static void displayFinalResults(QuizManager quizManager) {
        System.out.println("\n╔═══════════════════════════════════════════╗");
        System.out.println("║           KẾT QUẢ CUỐI CÙNG              ║");
        System.out.println("╚═══════════════════════════════════════════╝");
        System.out.println("Người chơi: " + quizManager.getPlayerName());
        System.out.println("Tổng điểm: " + quizManager.getTotalScore());
        System.out.println("Chiến lược chấm điểm: " + quizManager.getScoringStrategyName());
        
        // Đánh giá
        int score = quizManager.getTotalScore();
        System.out.println("\n┌────────────────────────────────────────┐");
        if (score >= 30) {
            System.out.println("│  ⭐⭐⭐ XUẤT SẮC! ⭐⭐⭐              │");
        } else if (score >= 20) {
            System.out.println("│  ⭐⭐ KHÁ TỐT! ⭐⭐                  │");
        } else if (score >= 10) {
            System.out.println("│  ⭐ CẦN CỐ GẮNG THÊM! ⭐            │");
        } else {
            System.out.println("│  HÃY HỌC THÊM NHÉ!                   │");
        }
        System.out.println("└────────────────────────────────────────┘");
        System.out.println("\nCảm ơn bạn đã tham gia Quiz Learning App!");
    }
}
