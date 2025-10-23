package main;

/**
 * Language utility class for bilingual support (English/Vietnamese)
 * Handles all UI text translations with proper UTF-8 encoding
 */
public class Language {
    private static String currentLanguage = "EN"; // Default: English
    
    // Language codes
    public static final String ENGLISH = "EN";
    public static final String VIETNAMESE = "VI";
    
    /**
     * Set the current language
     */
    public static void setLanguage(String lang) {
        currentLanguage = lang;
    }
    
    /**
     * Get the current language
     */
    public static String getLanguage() {
        return currentLanguage;
    }
    
    /**
     * Toggle between English and Vietnamese
     */
    public static void toggleLanguage() {
        currentLanguage = currentLanguage.equals(ENGLISH) ? VIETNAMESE : ENGLISH;
    }
    
    /**
     * Get text in current language
     */
    public static String get(String key) {
        if (currentLanguage.equals(VIETNAMESE)) {
            return getVietnamese(key);
        }
        return getEnglish(key);
    }
    
    // ====== ENGLISH TEXTS ======
    private static String getEnglish(String key) {
        switch (key) {
            // Welcome Screen
            case "APP_TITLE": return "Quiz Learning App";
            case "APP_SUBTITLE": return "Master Design Patterns with Interactive Quizzes";
            case "APP_DESCRIPTION": return "Learn Singleton, Decorator & Strategy Patterns";
            case "BADGE_QUESTIONS": return "10 Questions";
            case "BADGE_PATTERNS": return "3 Patterns";
            case "BADGE_FUN": return "Fun Learning";
            case "ENTER_NAME": return "Enter your name";
            case "START_BUTTON": return "Start Learning";
            case "LANGUAGE_BUTTON": return "Tiếng Việt";
            
            // Strategy Screen
            case "STRATEGY_TITLE": return "Choose Your Scoring Strategy";
            case "STRATEGY_SUBTITLE": return "Each strategy calculates points differently";
            case "STRATEGY_FIXED": return "Fixed Score";
            case "STRATEGY_FIXED_DESC": return "Equal points for all questions";
            case "STRATEGY_FIXED_POINTS": return "10 points per question";
            case "STRATEGY_DIFFICULTY": return "Difficulty Based";
            case "STRATEGY_DIFFICULTY_DESC": return "Harder questions = More points";
            case "STRATEGY_DIFFICULTY_POINTS": return "Easy: 5 | Medium: 10 | Hard: 15";
            case "STRATEGY_SPEED": return "Speed Bonus";
            case "STRATEGY_SPEED_DESC": return "Fast answers = Extra points";
            case "STRATEGY_SPEED_POINTS": return "Quick: +5 | Normal: +0";
            case "SELECT_BUTTON": return "Select";
            
            // Quiz Screen
            case "QUESTION_PROGRESS": return "Question";
            case "HINT_LABEL": return "[HINT]";
            case "TIME_LABEL": return "[TIME LIMIT]";
            case "SECONDS": return "seconds";
            case "TIME_UP": return "TIME'S UP!";
            case "TIME_UP_MESSAGE": return "Time's up for this question!\n\nMoving to the next question...";
            case "YOUR_ANSWER": return "Your answer:";
            case "SUBMIT_ANSWER": return "Submit Answer";
            case "NEXT_QUESTION": return "Next Question";
            case "CORRECT": return "Correct!";
            case "INCORRECT": return "Incorrect!";
            case "CORRECT_ANSWER_WAS": return "The correct answer was:";
            case "DIFFICULTY_EASY": return "Easy";
            case "DIFFICULTY_MEDIUM": return "Medium";
            case "DIFFICULTY_HARD": return "Hard";
            
            // Result Screen
            case "RESULT_TITLE": return "Quiz Complete!";
            case "RESULT_CONGRATULATIONS": return "Congratulations";
            case "RESULT_SCORE": return "Your Score";
            case "RESULT_OUT_OF": return "out of";
            case "RESULT_RATING_PERFECT": return "Perfect! Design Pattern Master!";
            case "RESULT_RATING_GREAT": return "Great job! You're learning well!";
            case "RESULT_RATING_GOOD": return "Good effort! Keep practicing!";
            case "RESULT_RATING_KEEP_TRYING": return "Keep trying! You'll get better!";
            case "PLAY_AGAIN": return "Play Again";
            case "EXIT": return "Exit";
            
            // Questions - Multiple Choice
            case "Q1_MC": return "What is the main purpose of the Singleton pattern?";
            case "Q1_A": return "Ensure a class has only one instance";
            case "Q1_B": return "Allow multiple inheritance";
            case "Q1_C": return "Increase performance";
            case "Q1_D": return "Simplify code structure";
            case "Q1_HINT": return "Think about instance control";
            
            case "Q2_MC": return "Which keyword is used in Singleton pattern?";
            case "Q2_A": return "static";
            case "Q2_B": return "final";
            case "Q2_C": return "abstract";
            case "Q2_D": return "volatile";
            case "Q2_HINT": return "It's related to class-level members";
            
            case "Q3_MC": return "What does the Decorator pattern do?";
            case "Q3_A": return "Add new functionality to objects";
            case "Q3_B": return "Create object families";
            case "Q3_C": return "Manage object lifecycle";
            case "Q3_D": return "Control access to objects";
            case "Q3_HINT": return "Think about extending behavior dynamically";
            
            case "Q4_MC": return "Which pattern allows selecting algorithms at runtime?";
            case "Q4_A": return "Strategy";
            case "Q4_B": return "Factory";
            case "Q4_C": return "Observer";
            case "Q4_D": return "Adapter";
            case "Q4_HINT": return "It's about choosing between different behaviors";
            
            case "Q5_MC": return "In Decorator pattern, decorators should:";
            case "Q5_A": return "Implement the same interface as the object";
            case "Q5_B": return "Extend a different class";
            case "Q5_C": return "Use multiple inheritance";
            case "Q5_D": return "Override all methods";
            case "Q5_HINT": return "Think about compatibility";
            
            // Questions - Text Input
            case "Q6_TEXT": return "What keyword makes Singleton constructor not accessible from outside?";
            case "Q6_ANSWER": return "private";
            case "Q6_HINT": return "It's an access modifier";
            
            case "Q7_TEXT": return "Name the pattern that wraps objects to add behavior.";
            case "Q7_ANSWER": return "decorator";
            case "Q7_HINT": return "It's one of the three patterns in this app";
            
            case "Q8_TEXT": return "In Strategy pattern, each algorithm is encapsulated in a separate ___?";
            case "Q8_ANSWER": return "class";
            case "Q8_HINT": return "It's a fundamental OOP concept";
            
            case "Q9_TEXT": return "Singleton pattern uses a ___ method to get the instance.";
            case "Q9_ANSWER": return "static";
            case "Q9_HINT": return "Related to class-level access";
            
            case "Q10_TEXT": return "Strategy pattern promotes using ___ over inheritance.";
            case "Q10_ANSWER": return "composition";
            case "Q10_HINT": return "Think about object relationships";
            
            default: return key;
        }
    }
    
    // ====== VIETNAMESE TEXTS ======
    private static String getVietnamese(String key) {
        switch (key) {
            // Welcome Screen
            case "APP_TITLE": return "Ứng Dụng Học Qua Câu Hỏi";
            case "APP_SUBTITLE": return "Thành Thạo Design Patterns Qua Bài Quiz Tương Tác";
            case "APP_DESCRIPTION": return "Học Singleton, Decorator & Strategy Patterns";
            case "BADGE_QUESTIONS": return "10 Câu Hỏi";
            case "BADGE_PATTERNS": return "3 Mẫu Thiết Kế";
            case "BADGE_FUN": return "Học Vui";
            case "ENTER_NAME": return "Nhập tên của bạn";
            case "START_BUTTON": return "Bắt Đầu Học";
            case "LANGUAGE_BUTTON": return "English";
            
            // Strategy Screen
            case "STRATEGY_TITLE": return "Chọn Chiến Lược Tính Điểm";
            case "STRATEGY_SUBTITLE": return "Mỗi chiến lược tính điểm khác nhau";
            case "STRATEGY_FIXED": return "Điểm Cố Định";
            case "STRATEGY_FIXED_DESC": return "Điểm bằng nhau cho mọi câu hỏi";
            case "STRATEGY_FIXED_POINTS": return "10 điểm mỗi câu";
            case "STRATEGY_DIFFICULTY": return "Theo Độ Khó";
            case "STRATEGY_DIFFICULTY_DESC": return "Câu khó hơn = Nhiều điểm hơn";
            case "STRATEGY_DIFFICULTY_POINTS": return "Dễ: 5 | Trung bình: 10 | Khó: 15";
            case "STRATEGY_SPEED": return "Thưởng Tốc Độ";
            case "STRATEGY_SPEED_DESC": return "Trả lời nhanh = Thêm điểm";
            case "STRATEGY_SPEED_POINTS": return "Nhanh: +5 | Bình thường: +0";
            case "SELECT_BUTTON": return "Chọn";
            
            // Quiz Screen
            case "QUESTION_PROGRESS": return "Câu hỏi";
            case "HINT_LABEL": return "[GỢI Ý]";
            case "TIME_LABEL": return "[THỜI GIAN]";
            case "SECONDS": return "giây";
            case "TIME_UP": return "HẾT GIỜ!";
            case "TIME_UP_MESSAGE": return "Hết thời gian cho câu hỏi này!\n\nChuyển sang câu tiếp theo...";
            case "YOUR_ANSWER": return "Câu trả lời của bạn:";
            case "SUBMIT_ANSWER": return "Nộp Câu Trả Lời";
            case "NEXT_QUESTION": return "Câu Hỏi Tiếp";
            case "CORRECT": return "Chính xác!";
            case "INCORRECT": return "Sai rồi!";
            case "CORRECT_ANSWER_WAS": return "Đáp án đúng là:";
            case "DIFFICULTY_EASY": return "Dễ";
            case "DIFFICULTY_MEDIUM": return "Trung bình";
            case "DIFFICULTY_HARD": return "Khó";
            
            // Result Screen
            case "RESULT_TITLE": return "Hoàn Thành!";
            case "RESULT_CONGRATULATIONS": return "Chúc mừng";
            case "RESULT_SCORE": return "Điểm Của Bạn";
            case "RESULT_OUT_OF": return "trên";
            case "RESULT_RATING_PERFECT": return "Hoàn hảo! Bạn là bậc thầy Design Pattern!";
            case "RESULT_RATING_GREAT": return "Tuyệt vời! Bạn đang học rất tốt!";
            case "RESULT_RATING_GOOD": return "Tốt đấy! Hãy tiếp tục luyện tập!";
            case "RESULT_RATING_KEEP_TRYING": return "Cố gắng lên! Bạn sẽ giỏi hơn!";
            case "PLAY_AGAIN": return "Chơi Lại";
            case "EXIT": return "Thoát";
            
            // Questions - Multiple Choice
            case "Q1_MC": return "Mục đích chính của mẫu Singleton là gì?";
            case "Q1_A": return "Đảm bảo một lớp chỉ có một thể hiện";
            case "Q1_B": return "Cho phép đa kế thừa";
            case "Q1_C": return "Tăng hiệu suất";
            case "Q1_D": return "Đơn giản hóa cấu trúc code";
            case "Q1_HINT": return "Nghĩ về việc kiểm soát thể hiện";
            
            case "Q2_MC": return "Từ khóa nào được dùng trong mẫu Singleton?";
            case "Q2_A": return "static";
            case "Q2_B": return "final";
            case "Q2_C": return "abstract";
            case "Q2_D": return "volatile";
            case "Q2_HINT": return "Nó liên quan đến thành viên cấp lớp";
            
            case "Q3_MC": return "Mẫu Decorator làm gì?";
            case "Q3_A": return "Thêm chức năng mới cho đối tượng";
            case "Q3_B": return "Tạo họ đối tượng";
            case "Q3_C": return "Quản lý vòng đời đối tượng";
            case "Q3_D": return "Kiểm soát truy cập đối tượng";
            case "Q3_HINT": return "Nghĩ về việc mở rộng hành vi động";
            
            case "Q4_MC": return "Mẫu nào cho phép chọn thuật toán khi chạy?";
            case "Q4_A": return "Strategy";
            case "Q4_B": return "Factory";
            case "Q4_C": return "Observer";
            case "Q4_D": return "Adapter";
            case "Q4_HINT": return "Nó về việc chọn giữa các hành vi khác nhau";
            
            case "Q5_MC": return "Trong mẫu Decorator, decorator nên:";
            case "Q5_A": return "Implement cùng interface với đối tượng";
            case "Q5_B": return "Kế thừa một lớp khác";
            case "Q5_C": return "Dùng đa kế thừa";
            case "Q5_D": return "Override tất cả các phương thức";
            case "Q5_HINT": return "Nghĩ về tính tương thích";
            
            // Questions - Text Input
            case "Q6_TEXT": return "Từ khóa nào làm cho constructor của Singleton không thể truy cập từ bên ngoài?";
            case "Q6_ANSWER": return "private";
            case "Q6_HINT": return "Đó là một access modifier";
            
            case "Q7_TEXT": return "Tên mẫu thiết kế bọc đối tượng để thêm hành vi là gì?";
            case "Q7_ANSWER": return "decorator";
            case "Q7_HINT": return "Đó là một trong ba mẫu trong ứng dụng này";
            
            case "Q8_TEXT": return "Trong mẫu Strategy, mỗi thuật toán được đóng gói trong một ___ riêng?";
            case "Q8_ANSWER": return "class";
            case "Q8_HINT": return "Đó là một khái niệm OOP cơ bản";
            
            case "Q9_TEXT": return "Mẫu Singleton dùng phương thức ___ để lấy thể hiện.";
            case "Q9_ANSWER": return "static";
            case "Q9_HINT": return "Liên quan đến truy cập cấp lớp";
            
            case "Q10_TEXT": return "Mẫu Strategy khuyến khích dùng ___ thay vì kế thừa.";
            case "Q10_ANSWER": return "composition";
            case "Q10_HINT": return "Nghĩ về mối quan hệ đối tượng";
            
            default: return key;
        }
    }
}
