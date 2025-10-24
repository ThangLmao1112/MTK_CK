# Quiz Learning App - Modern GUI Edition 🎨🌏💾

## 📚 Mục tiêu dự án

Dự án **Quiz Learning App** là một ứng dụng **Desktop GUI** hiện đại với Java Swing, minh họa việc sử dụng 3 Design Patterns quan trọng:
- **Singleton Pattern** - Quản lý instance duy nhất
- **Decorator Pattern** - Mở rộng chức năng động
- **Strategy Pattern** - Thay đổi algorithm linh hoạt

### ✨ Tính năng nổi bật:
- 🌏 **Đa ngôn ngữ**: Anh-Việt (bilingual support)
- ⏱️ **Timer đếm ngược**: 30 giây/câu với cảnh báo thời gian
- 🎨 **Giao diện hiện đại**: Inspired by Kahoot/Quizlet
- 🎯 **10 câu hỏi ngẫu nhiên**: 5 trắc nghiệm + 5 text input
- 📊 **3 chiến lược chấm điểm**: Fixed, Difficulty, Speed Bonus
- 💡 **Gợi ý thông minh**: Chỉ cho câu text input
- 💾 **Database SQLite**: Lưu kết quả, xem leaderboard, quản lý người chơi
- 🏆 **Bảng xếp hạng**: Top 10 với tính năng xóa người chơi

---

## 🚀 Cách chạy

### Yêu cầu hệ thống
- **Java JDK 8 trở lên**
- **Hệ điều hành**: Windows/Mac/Linux
- **GUI**: Swing (built-in, không cần cài thêm)
- **Database**: SQLite JDBC driver (xem hướng dẫn bên dưới)

### Bước 1: Chuẩn bị SQLite JDBC Driver

1. Tải SQLite JDBC driver từ: https://github.com/xerial/sqlite-jdbc/releases
2. Tải file JAR (ví dụ: `sqlite-jdbc-3.50.0.0.jar`)
3. Đổi tên file thành `sqlite-jdbc.jar`
4. Đặt vào thư mục `lib/` trong project: `lib/sqlite-jdbc.jar`

### Bước 2: Chạy ứng dụng

#### Cách 1: Sử dụng batch file (Windows - Khuyến nghị)

```powershell
run-gui.bat
```

#### Cách 2: Chạy thủ công

1. **Biên dịch với UTF-8 encoding:**
```powershell
javac -encoding UTF-8 -cp "lib\sqlite-jdbc.jar" -d bin src/main/strategy/*.java src/main/question/*.java src/main/manager/*.java src/main/db/*.java src/main/model/*.java src/main/repository/*.java src/main/Language.java src/main/QuizAppGUI.java
```

2. **Chạy ứng dụng:**
```powershell
java -Dfile.encoding=UTF-8 -cp "bin;lib\sqlite-jdbc.jar" main.QuizAppGUI
```

**Lưu ý:** 
- Bắt buộc dùng `-encoding UTF-8` để hiển thị tiếng Việt đúng!
- Phải có `sqlite-jdbc.jar` trong classpath để database hoạt động
- File database sẽ tự động tạo tại `Database/data/quiz.db`

---

## 🎯 Design Patterns được sử dụng

### 1. **SINGLETON PATTERN** 🔒

**Vị trí:** `src/main/manager/QuizManager.java`

**Mục đích:**
- Đảm bảo chỉ có **MỘT** instance của `QuizManager` trong toàn bộ ứng dụng
- Quản lý tập trung: câu hỏi, điểm số, người chơi, chiến lược chấm điểm

**Tại sao dùng?**
- Tránh tạo nhiều instance gây lãng phí bộ nhớ
- Đảm bảo tính nhất quán của dữ liệu quiz
- Dễ dàng truy cập từ mọi nơi trong GUI

**Code minh họa:**
```java
private static QuizManager instance;

private QuizManager() {
    // Constructor private
}

public static synchronized QuizManager getInstance() {
    if (instance == null) {
        instance = new QuizManager();
    }
    return instance;
}
```

**Đặc điểm:**
- ✅ Constructor private
- ✅ Instance static
- ✅ Method getInstance() public static
- ✅ Thread-safe với synchronized
- ✅ Lazy initialization

---

### 2. **DECORATOR PATTERN** 🎨

**Vị trí:** Package `src/main/question/`

**Các class liên quan:**
- `Question.java` - Component Interface
- `BasicQuestion.java` - Concrete Component (câu text input)
- `MultipleChoiceQuestion.java` - Concrete Component (câu trắc nghiệm)
- `QuestionDecorator.java` - Abstract Decorator
- `HintQuestion.java` - Concrete Decorator (thêm gợi ý)
- `TimedQuestion.java` - Concrete Decorator (thêm đếm ngược)

**Mục đích:**
- Thêm tính năng mới cho câu hỏi **ĐỘNG** mà không sửa code gốc
- Có thể kết hợp nhiều decorator (ví dụ: câu hỏi có cả hint và timer)

**Tại sao dùng?**
- Tuân theo **Open/Closed Principle**: mở để mở rộng, đóng để sửa đổi
- Linh hoạt hơn inheritance
- Có thể thêm/bớt tính năng trong runtime

**Code minh họa:**
```java
// Câu text input CÓ gợi ý
Question q = new BasicQuestion("...", "...");
q = new HintQuestion(q, "Gợi ý: ...");
q = new TimedQuestion(q, 30);

// Câu trắc nghiệm KHÔNG CÓ gợi ý
Question mcq = new MultipleChoiceQuestion("...", options, "...");
mcq = new TimedQuestion(mcq, 30);
```

**Phân loại trong app:**
- **5 câu trắc nghiệm (MC)**: Timer 30s, KHÔNG có gợi ý
- **5 câu text input**: Timer 30s, LUÔN có gợi ý
- **10/10 câu**: Đều có timer đếm ngược

---

### 3. **STRATEGY PATTERN** 🎲

**Vị trí:** Package `src/main/strategy/`

**Các class liên quan:**
- `ScoringStrategy.java` - Strategy Interface
- `FixedScoreStrategy.java` - Điểm cố định (10 điểm/câu)
- `DifficultyScoreStrategy.java` - Điểm theo độ khó (Dễ: 5, TB: 10, Khó: 15)
- `SpeedScoreStrategy.java` - Điểm theo tốc độ (càng nhanh càng cao)

**Mục đích:**
- Cho phép thay đổi **algorithm chấm điểm** trong runtime
- Tách riêng logic chấm điểm khỏi QuizManager

**Tại sao dùng?**
- Tuân theo **Single Responsibility Principle**
- Dễ dàng thêm chiến lược mới mà không sửa code cũ
- Context (QuizManager) không cần biết chi tiết algorithm

**Code minh họa:**
```java
// Trong QuizManager
private ScoringStrategy scoringStrategy;

public void setScoringStrategy(ScoringStrategy strategy) {
    this.scoringStrategy = strategy;
}

public int calculateScore(String difficulty, long timeTaken) {
    return scoringStrategy.calculateScore(difficulty, timeTaken);
}
```

**Sử dụng trong GUI:**
```java
// Người dùng chọn chiến lược qua 3 cards trong StrategyPanel
quizManager.setScoringStrategy(new FixedScoreStrategy());
// hoặc
quizManager.setScoringStrategy(new DifficultyScoreStrategy());
// hoặc
quizManager.setScoringStrategy(new SpeedScoreStrategy());
```

---

## 🌏 Hệ thống đa ngôn ngữ

**Vị trí:** `src/main/Language.java`

**Tính năng:**
- Hỗ trợ **2 ngôn ngữ**: English & Tiếng Việt
- Toggle bằng nút tròn màu coral ở góc phải màn hình Welcome
- Tự động cập nhật toàn bộ UI khi đổi ngôn ngữ

**Cách hoạt động:**
```java
// Lấy text theo ngôn ngữ hiện tại
String text = Language.get("APP_TITLE");

// Đổi ngôn ngữ
Language.toggleLanguage(); // EN ↔ VI

// Refresh UI
refreshAllPanels(); // Tạo lại tất cả panels
```

**Keys được dịch:**
- Giao diện: APP_TITLE, WELCOME_TITLE, START_BUTTON, SUBMIT_ANSWER...
- Chiến lược: STRATEGY_1_NAME, STRATEGY_2_NAME, STRATEGY_3_NAME...
- Câu hỏi: Q1_MC_QUESTION, Q1_MC_ANSWER, Q2_TEXT_HINT...
- Thông báo: CORRECT_ANSWER, WRONG_ANSWER, TIME_UP, TIME_UP_MESSAGE...

---

## ⏱️ Hệ thống Timer

**Tính năng:**
- **30 giây cho MỌI câu hỏi** (không phân biệt độ khó)
- Hiển thị đếm ngược ở giữa progress bar
- Đổi màu cảnh báo: 🟠 Orange (>5s) → 🔴 Red (≤5s)
- **Tự động skip** khi hết thời gian
- **Thông báo dialog** trước khi skip

**Code timer:**
```java
// Tạo timer với 1000ms interval
timer = new Timer(1000, e -> {
    remainingTime--;
    updateTimerDisplay(); // Cập nhật label
    
    if (remainingTime <= 0) {
        timer.stop();
        // Hiện dialog hết time
        showModernDialog(Language.get("TIME_UP"), 
                        Language.get("TIME_UP_MESSAGE"), false);
        // Auto skip
        nextQuestion();
    }
});
```

---

## � Hệ thống Database (SQLite)

**Vị trí:** `Database/` folder

**Tính năng:**
- Lưu tự động kết quả mỗi lần chơi
- Xem bảng xếp hạng Top 10
- Xóa người chơi bằng chuột phải
- Hỗ trợ đa ngôn ngữ cho tất cả UI

**Schema:**
- **Table `results`**: Lưu kết quả quiz
  - id (PRIMARY KEY)
  - player_name
  - strategy
  - score
  - total_questions
  - correct_answers
  - duration_sec
  - played_at (timestamp)
  
- **Table `settings`**: Lưu cấu hình (dự phòng)
  - key (PRIMARY KEY)
  - value

**File database:** `Database/data/quiz.db` (tự động tạo)

**SQL Scripts:**
- `Database/scripts/001_create_tables.sql` - DDL tạo bảng
- `Database/scripts/002_seed_demo.sql` - Demo data

**Repository pattern:**
- `src/main/db/Database.java` - Connection helper
- `src/main/model/Result.java` - POJO
- `src/main/repository/ResultRepository.java` - CRUD operations

---

## 🏆 Leaderboard (Bảng Xếp Hạng)

**Tính năng:**
- Hiển thị Top 10 người chơi theo điểm cao nhất
- Hỗ trợ đa ngôn ngữ (EN/VI)
- Highlight Top 3:
  - 🥇 Rank 1: Nền vàng + chữ đậm
  - 🥈 Rank 2: Nền bạc
  - 🥉 Rank 3: Nền đồng
- Giao diện hiện đại với gradient header
- Cột hiển thị: Hạng, Người chơi, Chiến lược, Điểm, Đúng/Tổng, Thời gian, Ngày chơi

**Cách xóa người chơi:**
1. Chuột phải vào hàng người chơi
2. Chọn "Delete Player" / "Xóa Người Chơi"
3. Xác nhận trong dialog
4. Bảng tự động refresh sau khi xóa

**Truy cập:**
- Nút "🏆 Leaderboard" ở màn hình kết quả

---

## �📁 Cấu trúc thư mục

```
src/
├── main/
│   ├── QuizAppGUI.java                 (GUI Main - 1400+ lines)
│   ├── Language.java                   (Bilingual Support - 315 lines)
│   ├── manager/
│   │   └── QuizManager.java            (SINGLETON)
│   ├── question/
│   │   ├── Question.java               (DECORATOR - Interface)
│   │   ├── BasicQuestion.java          (Text input questions)
│   │   ├── MultipleChoiceQuestion.java (MC questions)
│   │   ├── QuestionDecorator.java      (DECORATOR - Abstract)
│   │   ├── HintQuestion.java           (Gợi ý - chỉ text input)
│   │   └── TimedQuestion.java          (Timer - mọi câu)
│   ├── strategy/
│   │   ├── ScoringStrategy.java        (STRATEGY - Interface)
│   │   ├── FixedScoreStrategy.java     (10 điểm/câu)
│   │   ├── DifficultyScoreStrategy.java(5/10/15 điểm)
│   │   └── SpeedScoreStrategy.java     (Bonus tốc độ)
│   ├── db/
│   │   └── Database.java               (SQLite connection)
│   ├── model/
│   │   └── Result.java                 (Result POJO)
│   └── repository/
│       └── ResultRepository.java       (CRUD operations)
Database/
├── scripts/
│   ├── 001_create_tables.sql           (DDL schema)
│   └── 002_seed_demo.sql               (Demo data)
├── data/
│   └── quiz.db                         (Created at runtime)
└── README.md                           (Database documentation)
lib/
└── sqlite-jdbc.jar                     (SQLite JDBC driver)
run-gui.bat                             (Windows launcher)
```

---

## 🎨 Giao diện

### 1. **Welcome Screen**
- Gradient header (blue → purple)
- Circular language toggle (coral red, top-right)
- Player name input với placeholder
- 3 badges: Questions, Time, Bilingual
- Start button gradient animation

### 2. **Strategy Selection Screen**
- 3 cards với hover effects:
  - 💰 **Fixed Score**: 10 điểm mọi câu
  - 🎯 **Difficulty**: 5/10/15 điểm
  - ⚡ **Speed Bonus**: +5 điểm nếu <10s
- Modern card design với rounded corners

### 3. **Quiz Screen**
- Progress bar với timer ở giữa
- Question card với rounded corners
- **MC questions**: 4 radio buttons, KHÔNG gợi ý
- **Text input questions**: TextField + CÓ gợi ý
- Submit button gradient

### 4. **Result Screen**
- Gradient celebration background
- Score display with large font
- 3 buttons: Play Again, 🏆 Leaderboard, Exit
- Tự động lưu kết quả vào database

### 5. **Leaderboard Dialog**
- Gradient header (purple → blue)
- Top 10 table với highlight Top 3
- Right-click menu để xóa người chơi
- Close button

---

## 🎮 Tính năng chính

1. **🌏 Bilingual Support (EN/VI)**
   - Toggle button tròn ở góc phải
   - Tự động refresh UI
   - Dịch questions + answers + UI + database UI

2. **⏱️ Real Countdown Timer**
   - 30 giây/câu
   - Màu cảnh báo thông minh
   - Auto-skip + notification dialog

3. **💡 Selective Hints**
   - MC questions: KHÔNG gợi ý (4 options = hints)
   - Text input: LUÔN có gợi ý (khó đoán hơn)

4. **🎯 10 Random Questions**
   - 5 Multiple Choice
   - 5 Text Input
   - Shuffle order mỗi lần chơi

5. **📊 3 Scoring Strategies**
   - Fixed: 10 điểm/câu
   - Difficulty: 5/10/15 điểm
   - Speed Bonus: +5 điểm nếu <10s

6. **💾 Database Persistence**
   - SQLite embedded database
   - Tự động lưu kết quả sau mỗi lần chơi
   - Không mất dữ liệu khi đóng app

7. **🏆 Leaderboard System**
   - Top 10 theo điểm cao nhất
   - Highlight Top 3 (vàng, bạc, đồng)
   - Confirmation dialog trước khi xóa

8. **🎨 Modern UI Design**
   - Gradient backgrounds
   - Rounded corners
   - Hover effects
   - Color-coded feedback

---

## 🎨 Color Palette

```java
PRIMARY_COLOR   = #4682FF (Royal Blue)
SUCCESS_COLOR   = #2ED573 (Green)
ERROR_COLOR     = #FF4757 (Red)
WARNING_COLOR   = #FFA800 (Orange)
LANGUAGE_BUTTON = #FF6B6B (Coral Red)
```

---

## 🌟 Ưu điểm của việc sử dụng Design Patterns

### SINGLETON
✅ Quản lý tập trung dữ liệu quiz  
✅ Tránh tạo nhiều instance không cần thiết  
✅ Thread-safe với synchronized  
✅ Dễ dàng truy cập từ mọi component GUI

### DECORATOR
✅ Thêm tính năng động (hint, timer) mà không sửa code gốc  
✅ Kết hợp nhiều decorators linh hoạt  
✅ Tuân theo Open/Closed Principle  
✅ Selective decoration (hint chỉ cho text input)

### STRATEGY
✅ Thay đổi algorithm chấm điểm trong runtime  
✅ Code dễ bảo trì và mở rộng  
✅ Tách riêng logic tính toán khỏi GUI  
✅ User-friendly selection qua cards

---

## � Technical Details

### Window Configuration
- **Size**: 1000x750 pixels (fixed, non-resizable)
- **Position**: Center screen
- **Layout**: CardLayout cho screen navigation

### Timer Implementation
- **Framework**: javax.swing.Timer
- **Interval**: 1000ms (1 second)
- **Duration**: 30 seconds per question
- **Auto-skip**: Yes, với dialog notification

### Encoding Requirements
- **Source files**: UTF-8
- **Compilation**: `javac -encoding UTF-8`
- **Runtime**: `-Dfile.encoding=UTF-8` (trong run-gui.bat)
- **Critical**: Bắt buộc cho hiển thị tiếng Việt

---

## 🐛 Troubleshooting

### Tiếng Việt bị lỗi font (???)
**Giải pháp:** Đảm bảo dùng UTF-8 encoding:
```powershell
javac -encoding UTF-8 -d bin src/main/**/*.java
java -Dfile.encoding=UTF-8 -cp bin main.QuizAppGUI
```
Hoặc chạy `run-gui.bat` đã cấu hình sẵn.

### Ứng dụng không khởi động
**Kiểm tra:**
1. Java version: `java -version` (cần JDK 8+)
2. Compilation errors: Xem output của javac
3. ClassNotFoundException: Kiểm tra `-cp bin` đúng đường dẫn
4. SQLite driver: Đảm bảo `lib\sqlite-jdbc.jar` tồn tại

### SQLite Driver không tìm thấy
**Lỗi:** `java.lang.ClassNotFoundException: org.sqlite.JDBC`  
**Giải pháp:**
1. Download SQLite JDBC driver từ [GitHub Releases](https://github.com/xerial/sqlite-jdbc/releases)
2. Đặt file JAR vào thư mục `lib\sqlite-jdbc.jar`
3. Kiểm tra classpath trong `run-gui.bat` có `-cp "bin;lib\sqlite-jdbc.jar"`

### Database không lưu kết quả
**Nguyên nhân có thể:**
1. Thư mục `Database\data` chưa tạo → Tạo thủ công hoặc chạy app sẽ tự động tạo
2. Quyền ghi file bị chặn → Chạy CMD/PowerShell as Administrator
3. Database connection lỗi → Kiểm tra logs trong console

### Leaderboard trống dù đã chơi
**Kiểm tra:**
1. File `Database\data\quiz.db` có tồn tại không?
2. Có lỗi nào trong console khi lưu kết quả không?
3. Thử run script `Database\scripts\002_seed_demo.sql` để thêm dữ liệu demo

### Timer không hoạt động
**Nguyên nhân:** Code đang chạy, timer hoạt động thực tế. Kiểm tra:
1. Label timer có hiển thị không? (giữa progress bar)
2. Màu đổi sang red khi ≤5s không?
3. Dialog "Time's up!" có xuất hiện không?

---

## � Documentation Files

- `README.md` - File này (overview + setup)
- `LANGUAGE_FEATURE.md` - Chi tiết bilingual implementation
- `MODERN_UI_README.md` - UI design specifications

---

## 🔮 Future Enhancements

Những tính năng đã hoàn thành:
- ✅ Database integration (SQLite)
- ✅ Leaderboard system với delete functionality
- ✅ Bilingual support (EN/VI)
- ✅ Modern UI với gradient và màu sắc hiện đại
- ✅ Timer với progress bar và auto-skip

Những tính năng có thể mở rộng thêm:
- �️ Export leaderboard (CSV, Excel)
- �📊 Statistics & progress tracking chi tiết hơn (charts, graphs)
- 🎵 Sound effects (background music, correct/incorrect sounds)
- 🌐 Thêm ngôn ngữ khác (Français, 中文, 日本語...)
- 🎯 Difficulty levels cho từng quiz session (Easy/Medium/Hard)
- 📱 Responsive UI cho tablet mode
- 👤 User profiles với avatar và preferences
- 🏅 Achievement system (badges cho milestones)
- 🎨 Customizable themes (dark mode, color schemes)
- 📚 Multiple quiz categories (Math, Science, History...)

---

## 📖 Tài liệu tham khảo

- **Singleton Pattern**: Creational pattern đảm bảo một class chỉ có một instance
- **Decorator Pattern**: Structural pattern mở rộng chức năng động
- **Strategy Pattern**: Behavioral pattern cho phép thay đổi algorithm
- **Java Swing**: GUI toolkit chuẩn của Java

---

## 👨‍💻 Tác giả & Credits

Dự án Quiz Learning App - Modern GUI Edition  
Mục đích: Demo Design Patterns trong Java với giao diện đẹp mắt  
Inspired by: Kahoot, Quizlet

**Chúc bạn học tốt! 🎓✨**

