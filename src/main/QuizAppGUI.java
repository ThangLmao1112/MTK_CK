package main;

import main.manager.QuizManager;
import main.question.*;
import main.strategy.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Quiz Learning App - GUI Desktop Application
 * Demonstrates: Singleton, Decorator, and Strategy Design Patterns
 * 10 questions - Mix of Multiple Choice and Text Input
 */
public class QuizAppGUI extends JFrame {
    // SINGLETON PATTERN
    private QuizManager quizManager;
    
    // UI Components
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    // Modern Color Palette - Inspired by Kahoot/Quizlet
    private final Color PRIMARY_COLOR = new Color(70, 130, 255); // Vibrant Blue
    private final Color SECONDARY_COLOR = new Color(130, 90, 255); // Purple
    private final Color SUCCESS_COLOR = new Color(46, 213, 115); // Green
    private final Color ERROR_COLOR = new Color(255, 71, 87); // Red
    private final Color WARNING_COLOR = new Color(255, 168, 0); // Orange
    private final Color BG_COLOR = new Color(245, 247, 250);
    private final Color CARD_BG = Color.WHITE;
    private final Color TEXT_PRIMARY = new Color(30, 39, 46);
    private final Color TEXT_SECONDARY = new Color(108, 117, 125);
    
    private int currentQuestionIndex = 0;
    private long questionStartTime = 0;
    private static final int TOTAL_QUESTIONS = 10;
    
    // Timer for time-limited questions
    private Timer countdownTimer;
    private int remainingSeconds = 0;
    
    // Multiple choice components
    private ButtonGroup answerButtonGroup;
    private JRadioButton[] answerButtons;
    
    public QuizAppGUI() {
        quizManager = QuizManager.getInstance();
        
        setTitle("Quiz Learning App - Modern UI");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Modern rounded corners (Windows 11 style)
        try {
            setUndecorated(false);
        } catch (Exception e) {
            // Fallback for older systems
        }
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BG_COLOR);
        
        mainPanel.add(createWelcomePanel(), "WELCOME");
        mainPanel.add(createStrategyPanel(), "STRATEGY");
        mainPanel.add(createQuizPanel(), "QUIZ");
        mainPanel.add(createResultPanel(), "RESULT");
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(null); // Absolute layout for custom positioning
        panel.setBackground(BG_COLOR);
        
        // Create gradient background panel
        JPanel headerPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY_COLOR, getWidth(), getHeight(), SECONDARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        headerPanel.setBounds(0, 0, 1000, 280);
        panel.add(headerPanel);
        
        // Language toggle button (top-right corner) - Circular design
        JButton langButton = new JButton(Language.get("LANGUAGE_BUTTON")) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw circular background
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                
                // Draw text
                g2.setColor(getForeground());
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getAscent();
                int x = (getWidth() - textWidth) / 2;
                int y = (getHeight() + textHeight) / 2 - 2;
                g2.drawString(getText(), x, y);
                
                g2.dispose();
            }
        };
        
        langButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        langButton.setForeground(Color.WHITE);
        langButton.setBackground(new Color(255, 107, 107)); // Coral Red - vibrant and eye-catching
        langButton.setBorder(BorderFactory.createEmptyBorder());
        langButton.setFocusPainted(false);
        langButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        langButton.setContentAreaFilled(false);
        langButton.setOpaque(false);
        langButton.setBounds(850, 20, 130, 45);
        
        // Hover effect with color change
        langButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                langButton.setBackground(new Color(255, 159, 67)); // Orange on hover
            }
            public void mouseExited(MouseEvent e) {
                langButton.setBackground(new Color(255, 107, 107)); // Back to coral red
            }
        });
        
        // Toggle language and refresh UI
        langButton.addActionListener(e -> {
            Language.toggleLanguage();
            refreshAllPanels();
        });
        
        headerPanel.add(langButton);
        
        // Main title with shadow effect
        JLabel titleLabel = new JLabel(Language.get("APP_TITLE"));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 52));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 50, 1000, 65);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel(Language.get("APP_SUBTITLE"));
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        subtitleLabel.setForeground(new Color(255, 255, 255, 230));
        subtitleLabel.setBounds(0, 125, 1000, 30);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(subtitleLabel);
        
        // Description text
        JLabel descLabel = new JLabel(Language.get("APP_DESCRIPTION"));
        descLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        descLabel.setForeground(new Color(255, 255, 255, 200));
        descLabel.setBounds(0, 160, 1000, 25);
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(descLabel);
        
        // Info badges
        JPanel badgePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        badgePanel.setOpaque(false);
        badgePanel.setBounds(0, 200, 1000, 40);
        
        badgePanel.add(createBadge(Language.get("BADGE_QUESTIONS"), new Color(255, 255, 255, 200)));
        badgePanel.add(createBadge(Language.get("BADGE_PATTERNS"), new Color(255, 255, 255, 200)));
        badgePanel.add(createBadge(Language.get("BADGE_FUN"), new Color(255, 255, 255, 200)));
        
        headerPanel.add(badgePanel);
        
        // Main card for input
        JPanel cardPanel = createCard();
        cardPanel.setBounds(300, 320, 400, 280);
        cardPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        welcomeLabel.setForeground(TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0;
        cardPanel.add(welcomeLabel, gbc);
        
        JLabel instructionLabel = new JLabel(Language.get("ENTER_NAME"));
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        instructionLabel.setForeground(TEXT_SECONDARY);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 20, 20);
        cardPanel.add(instructionLabel, gbc);
        
        JTextField nameField = createModernTextField(Language.get("ENTER_NAME"));
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 20, 10, 20);
        cardPanel.add(nameField, gbc);
        
        JButton startButton = createModernButton(Language.get("START_BUTTON"), PRIMARY_COLOR);
        startButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty() || name.equals(Language.get("ENTER_NAME"))) {
                showModernDialog("Please enter your name!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            quizManager.setPlayerName(name);
            cardLayout.show(mainPanel, "STRATEGY");
        });
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 20, 20, 20);
        cardPanel.add(startButton, gbc);
        
        panel.add(cardPanel);
        
        return panel;
    }
    
    private JPanel createStrategyPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Header
        JLabel titleLabel = new JLabel(Language.get("STRATEGY_TITLE"));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
        gbc.insets = new Insets(30, 0, 5, 0);
        panel.add(titleLabel, gbc);
        
        JLabel subtitleLabel = new JLabel(Language.get("STRATEGY_SUBTITLE"));
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 40, 0);
        panel.add(subtitleLabel, gbc);
        
        // Strategy cards in a row
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 20, 0, 20);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        // Card 1: Fixed Score
        gbc.gridx = 0;
        JPanel card1 = createModernStrategyCard(
            Language.get("STRATEGY_FIXED"),
            Language.get("STRATEGY_FIXED_DESC"),
            Language.get("STRATEGY_FIXED_POINTS"),
            PRIMARY_COLOR,
            "BALANCED"
        );
        addStrategyCardClick(card1, () -> {
            quizManager.setScoringStrategy(new FixedScoreStrategy());
            startQuiz();
        });
        panel.add(card1, gbc);
        
        // Card 2: Difficulty Score
        gbc.gridx = 1;
        JPanel card2 = createModernStrategyCard(
            Language.get("STRATEGY_DIFFICULTY"),
            Language.get("STRATEGY_DIFFICULTY_DESC"),
            Language.get("STRATEGY_DIFFICULTY_POINTS"),
            SUCCESS_COLOR,
            "STRATEGIC"
        );
        addStrategyCardClick(card2, () -> {
            quizManager.setScoringStrategy(new DifficultyScoreStrategy());
            startQuiz();
        });
        panel.add(card2, gbc);
        
        // Card 3: Speed Score
        gbc.gridx = 2;
        JPanel card3 = createModernStrategyCard(
            Language.get("STRATEGY_SPEED"),
            Language.get("STRATEGY_SPEED_DESC"),
            Language.get("STRATEGY_SPEED_POINTS"),
            WARNING_COLOR,
            "COMPETITIVE"
        );
        addStrategyCardClick(card3, () -> {
            quizManager.setScoringStrategy(new SpeedScoreStrategy());
            startQuiz();
        });
        panel.add(card3, gbc);
        
        return panel;
    }
    
    private JPanel createQuizPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BG_COLOR);
        
        // Progress bar at top
        JPanel progressContainer = new JPanel(null);
        progressContainer.setBounds(50, 30, 900, 60);
        progressContainer.setBackground(CARD_BG);
        progressContainer.setBorder(createModernBorder());
        
        JLabel progressLabel = new JLabel();
        progressLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        progressLabel.setForeground(TEXT_PRIMARY);
        progressLabel.setBounds(20, 15, 300, 30);
        progressContainer.add(progressLabel);
        
        // Timer label (for time-limited questions) - CENTER position
        JLabel timerLabel = new JLabel("");
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        timerLabel.setForeground(WARNING_COLOR);
        timerLabel.setBounds(350, 8, 200, 44);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        progressContainer.add(timerLabel);
        
        JLabel scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        scoreLabel.setForeground(SUCCESS_COLOR);
        scoreLabel.setBounds(600, 15, 280, 30);
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        progressContainer.add(scoreLabel);
        
        panel.add(progressContainer);
        
        // Question card
        JPanel questionCard = createCard();
        questionCard.setBounds(50, 110, 900, 520);
        questionCard.setLayout(null);
        
        // Question text area
        JTextArea questionArea = new JTextArea();
        questionArea.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setEditable(false);
        questionArea.setBackground(CARD_BG);
        questionArea.setForeground(TEXT_PRIMARY);
        questionArea.setBounds(30, 30, 840, 120);
        questionArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        questionCard.add(questionArea);
        
        // Answer panel
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        answerPanel.setBackground(CARD_BG);
        answerPanel.setBounds(30, 170, 840, 280);
        questionCard.add(answerPanel);
        
        // Submit button
        JButton submitButton = createModernButton("SUBMIT ANSWER", PRIMARY_COLOR);
        submitButton.setBounds(350, 460, 200, 50);
        questionCard.add(submitButton);
        
        panel.add(questionCard);
        
        submitButton.addActionListener(e -> handleSubmit(questionArea, answerPanel, progressLabel, scoreLabel, timerLabel));
        
        panel.putClientProperty("questionArea", questionArea);
        panel.putClientProperty("answerPanel", answerPanel);
        panel.putClientProperty("progressLabel", progressLabel);
        panel.putClientProperty("scoreLabel", scoreLabel);
        panel.putClientProperty("timerLabel", timerLabel);
        panel.putClientProperty("submitButton", submitButton);
        panel.putClientProperty("questionCard", questionCard);
        
        return panel;
    }
    
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(BG_COLOR);
        
        // Celebration header
        JPanel celebrationPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, SUCCESS_COLOR, getWidth(), getHeight(), PRIMARY_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        celebrationPanel.setBounds(0, 0, 1000, 200);
        panel.add(celebrationPanel);
        
        JLabel congratsLabel = new JLabel(Language.get("RESULT_CONGRATULATIONS").toUpperCase());
        congratsLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        congratsLabel.setForeground(Color.WHITE);
        congratsLabel.setBounds(0, 60, 1000, 50);
        congratsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(congratsLabel);
        
        JLabel completeLabel = new JLabel(Language.get("RESULT_TITLE"));
        completeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        completeLabel.setForeground(new Color(255, 255, 255, 200));
        completeLabel.setBounds(0, 120, 1000, 30);
        completeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(completeLabel);
        
        // Result card
        JPanel resultCard = createCard();
        resultCard.setBounds(250, 240, 500, 380);
        resultCard.setLayout(null);
        
        JLabel nameLabel = new JLabel();
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        nameLabel.setForeground(TEXT_SECONDARY);
        nameLabel.setBounds(0, 30, 500, 25);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultCard.add(nameLabel);
        
        JLabel scoreLabel = new JLabel();
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 72));
        scoreLabel.setForeground(SUCCESS_COLOR);
        scoreLabel.setBounds(0, 70, 500, 80);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultCard.add(scoreLabel);
        
        JLabel pointsLabel = new JLabel(Language.get("RESULT_SCORE").toUpperCase());
        pointsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        pointsLabel.setForeground(TEXT_SECONDARY);
        pointsLabel.setBounds(0, 155, 500, 20);
        pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultCard.add(pointsLabel);
        
        JLabel ratingLabel = new JLabel();
        ratingLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        ratingLabel.setBounds(0, 200, 500, 35);
        ratingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultCard.add(ratingLabel);
        
        JLabel strategyLabel = new JLabel();
        strategyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        strategyLabel.setForeground(TEXT_SECONDARY);
        strategyLabel.setBounds(0, 245, 500, 20);
        strategyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultCard.add(strategyLabel);
        
        // Buttons
        JButton playAgainButton = createModernButton(Language.get("PLAY_AGAIN"), PRIMARY_COLOR);
        playAgainButton.setBounds(80, 295, 180, 50);
        playAgainButton.addActionListener(e -> {
            resetQuiz();
            cardLayout.show(mainPanel, "WELCOME");
        });
        resultCard.add(playAgainButton);
        
        JButton exitButton = createModernButton(Language.get("EXIT"), ERROR_COLOR);
        exitButton.setBounds(280, 295, 140, 50);
        exitButton.addActionListener(e -> System.exit(0));
        resultCard.add(exitButton);
        
        panel.add(resultCard);
        
        panel.putClientProperty("nameLabel", nameLabel);
        panel.putClientProperty("scoreLabel", scoreLabel);
        panel.putClientProperty("strategyLabel", strategyLabel);
        panel.putClientProperty("ratingLabel", ratingLabel);
        
        return panel;
    }
    
    private void startQuiz() {
        currentQuestionIndex = 0;
        quizManager.resetScore();
        quizManager.resetQuiz();
        
        setupQuestions();
        
        cardLayout.show(mainPanel, "QUIZ");
        
        JPanel quizPanel = (JPanel) mainPanel.getComponent(2);
        JTextArea questionArea = (JTextArea) quizPanel.getClientProperty("questionArea");
        JPanel answerPanel = (JPanel) quizPanel.getClientProperty("answerPanel");
        JLabel progressLabel = (JLabel) quizPanel.getClientProperty("progressLabel");
        JLabel scoreLabel = (JLabel) quizPanel.getClientProperty("scoreLabel");
        JLabel timerLabel = (JLabel) quizPanel.getClientProperty("timerLabel");
        
        displayQuestion(0, questionArea, answerPanel, progressLabel, scoreLabel, timerLabel);
    }
    
    private void setupQuestions() {
        Random random = new Random();
        String[] difficulties = {"easy", "medium", "hard"};
        
        // Multiple Choice Questions (5 total)
        String[][] mcQuestionKeys = {
            {"Q1_MC", "Q1_A", "Q1_B|Q1_C|Q1_A|Q1_D", "Q1_HINT"},
            {"Q2_MC", "Q2_A", "Q2_B|Q2_C|Q2_A|Q2_D", "Q2_HINT"},
            {"Q3_MC", "Q3_A", "Q3_B|Q3_A|Q3_C|Q3_D", "Q3_HINT"},
            {"Q4_MC", "Q4_A", "Q4_B|Q4_A|Q4_C|Q4_D", "Q4_HINT"},
            {"Q5_MC", "Q5_A", "Q5_B|Q5_C|Q5_A|Q5_D", "Q5_HINT"}
        };
        
        // Text Input Questions (5 total)
        String[][] textQuestionKeys = {
            {"Q6_TEXT", "Q6_ANSWER", "easy", "Q6_HINT"},
            {"Q7_TEXT", "Q7_ANSWER", "medium", "Q7_HINT"},
            {"Q8_TEXT", "Q8_ANSWER", "medium", "Q8_HINT"},
            {"Q9_TEXT", "Q9_ANSWER", "easy", "Q9_HINT"},
            {"Q10_TEXT", "Q10_ANSWER", "hard", "Q10_HINT"}
        };
        
        // Add MC questions
        for (int i = 0; i < 5; i++) {
            String[] keys = mcQuestionKeys[i];
            String questionText = Language.get(keys[0]);
            String correctAnswer = Language.get(keys[1]);
            
            String[] optionKeys = keys[2].split("\\|");
            String[] options = new String[optionKeys.length];
            for (int j = 0; j < optionKeys.length; j++) {
                options[j] = Language.get(optionKeys[j]);
            }
            
            String diff = difficulties[random.nextInt(difficulties.length)];
            Question question = new MultipleChoiceQuestion(questionText, correctAnswer, options, diff);
            
            // No hint for Multiple Choice questions
            
            // Add timer to ALL questions - 30 seconds for all
            question = new TimedQuestion(question, 30);
            
            quizManager.addQuestion(question);
        }
        
        // Add text input questions
        for (int i = 0; i < 5; i++) {
            String[] keys = textQuestionKeys[i];
            String questionText = Language.get(keys[0]);
            String correctAnswer = Language.get(keys[1]);
            String diff = keys[2];
            
            Question question = new BasicQuestion(questionText, correctAnswer, diff);
            
            // Add hint for text input questions (always)
            String hint = Language.get(keys[3]);
            question = new HintQuestion(question, hint);
            
            // Add timer to ALL questions - 30 seconds for all
            question = new TimedQuestion(question, 30);
            
            quizManager.addQuestion(question);
        }
    }
    
    private void displayQuestion(int index, JTextArea questionArea, JPanel answerPanel, 
                                  JLabel progressLabel, JLabel scoreLabel, JLabel timerLabel) {
        Question question = quizManager.getQuestion(index);
        questionStartTime = System.currentTimeMillis();
        
        // Stop previous timer if running
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
        
        progressLabel.setText(Language.get("QUESTION_PROGRESS") + " " + (index + 1) + " / " + TOTAL_QUESTIONS);
        scoreLabel.setText(Language.get("RESULT_SCORE") + ": " + quizManager.getTotalScore());
        
        answerPanel.removeAll();
        
        QuestionInfo info = extractQuestionInfo(question);
        
        // Setup timer for time-limited questions
        if (info.timeLimit > 0) {
            remainingSeconds = info.timeLimit;
            timerLabel.setText(remainingSeconds + "s");
            timerLabel.setForeground(WARNING_COLOR);
            
            countdownTimer = new Timer(1000, e -> {
                remainingSeconds--;
                timerLabel.setText(remainingSeconds + "s");
                
                if (remainingSeconds <= 5) {
                    timerLabel.setForeground(ERROR_COLOR); // Red when < 5 seconds
                }
                
                if (remainingSeconds <= 0) {
                    countdownTimer.stop();
                    timerLabel.setText(Language.get("TIME_UP"));
                    
                    // Show time's up notification
                    showModernDialog(
                        Language.get("TIME_UP_MESSAGE"),
                        Language.get("TIME_UP"),
                        JOptionPane.WARNING_MESSAGE
                    );
                    
                    // Mark as incorrect and move to next question
                    currentQuestionIndex++;
                    
                    if (currentQuestionIndex >= TOTAL_QUESTIONS) {
                        showResults();
                    } else {
                        displayQuestion(currentQuestionIndex, questionArea, answerPanel, progressLabel, scoreLabel, timerLabel);
                    }
                }
            });
            countdownTimer.start();
        } else {
            timerLabel.setText(""); // Clear timer text if no time limit
        }
        
        StringBuilder questionText = new StringBuilder();
        questionText.append(info.questionText);
        // Removed: Difficulty level no longer shown
        
        if (info.hint != null) {
            questionText.append("\n\n").append(Language.get("HINT_LABEL")).append(" ").append(info.hint);
        }
        // Removed: Time limit no longer shown in question text (only in timer label)
        
        questionArea.setText(questionText.toString());
        
        if (info.isMultipleChoice) {
            // Modern radio buttons
            answerButtonGroup = new ButtonGroup();
            answerButtons = new JRadioButton[info.options.length];
            
            for (int i = 0; i < info.options.length; i++) {
                JPanel optionPanel = new JPanel(new BorderLayout());
                optionPanel.setBackground(CARD_BG);
                optionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
                optionPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
                    BorderFactory.createEmptyBorder(12, 15, 12, 15)
                ));
                
                answerButtons[i] = new JRadioButton((char)('A' + i) + ". " + info.options[i]);
                answerButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
                answerButtons[i].setBackground(CARD_BG);
                answerButtons[i].setForeground(TEXT_PRIMARY);
                answerButtons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
                answerButtons[i].setFocusPainted(false);
                
                final int index_f = i;
                final JPanel panel_f = optionPanel;
                final JRadioButton button_f = answerButtons[i];
                
                // Click on panel to select
                MouseAdapter clickAdapter = new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        button_f.setSelected(true);
                    }
                    public void mouseEntered(MouseEvent e) {
                        if (!button_f.isSelected()) {
                            panel_f.setBackground(new Color(248, 249, 250));
                            panel_f.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                                BorderFactory.createEmptyBorder(12, 15, 12, 15)
                            ));
                        }
                    }
                    public void mouseExited(MouseEvent e) {
                        if (!button_f.isSelected()) {
                            panel_f.setBackground(CARD_BG);
                            panel_f.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
                                BorderFactory.createEmptyBorder(12, 15, 12, 15)
                            ));
                        }
                    }
                };
                
                optionPanel.addMouseListener(clickAdapter);
                answerButtons[i].addMouseListener(clickAdapter);
                
                answerButtons[i].addItemListener(e -> {
                    if (button_f.isSelected()) {
                        panel_f.setBackground(new Color(230, 240, 255));
                        button_f.setBackground(new Color(230, 240, 255));
                        panel_f.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(PRIMARY_COLOR, 3),
                            BorderFactory.createEmptyBorder(11, 15, 11, 15)
                        ));
                    } else {
                        panel_f.setBackground(CARD_BG);
                        button_f.setBackground(CARD_BG);
                        panel_f.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
                            BorderFactory.createEmptyBorder(12, 15, 12, 15)
                        ));
                    }
                });
                
                answerButtonGroup.add(answerButtons[i]);
                optionPanel.add(answerButtons[i], BorderLayout.CENTER);
                
                answerPanel.add(optionPanel);
                answerPanel.add(Box.createVerticalStrut(12));
            }
        } else {
            // Modern text input
            JLabel label = new JLabel(Language.get("YOUR_ANSWER"));
            label.setFont(new Font("Segoe UI", Font.BOLD, 16));
            label.setForeground(TEXT_PRIMARY);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            JTextField textField = new JTextField();
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
            textField.setAlignmentX(Component.LEFT_ALIGNMENT);
            textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            
            answerPanel.add(label);
            answerPanel.add(Box.createVerticalStrut(15));
            answerPanel.add(textField);
            
            textField.addActionListener(e -> {
                JPanel quizPanel = (JPanel) mainPanel.getComponent(2);
                JButton submitButton = (JButton) quizPanel.getClientProperty("submitButton");
                submitButton.doClick();
            });
            
            answerPanel.putClientProperty("textField", textField);
        }
        
        answerPanel.revalidate();
        answerPanel.repaint();
    }
    
    private void handleSubmit(JTextArea questionArea, JPanel answerPanel, 
                              JLabel progressLabel, JLabel scoreLabel, JLabel timerLabel) {
        if (currentQuestionIndex >= TOTAL_QUESTIONS) return;
        
        // Stop timer if running
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
        
        Question question = quizManager.getQuestion(currentQuestionIndex);
        String answer = null;
        
        if (answerButtonGroup != null) {
            for (int i = 0; i < answerButtons.length; i++) {
                if (answerButtons[i].isSelected()) {
                    answer = String.valueOf((char)('A' + i));
                    break;
                }
            }
        } else {
            JTextField textField = (JTextField) answerPanel.getClientProperty("textField");
            if (textField != null) {
                answer = textField.getText();
            }
        }
        
        if (answer == null || answer.trim().isEmpty()) {
            showModernDialog("Please select or enter your answer!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        long timeTaken = (System.currentTimeMillis() - questionStartTime) / 1000;
        boolean isCorrect = question.checkAnswer(answer);
        
        if (isCorrect) {
            int score = quizManager.calculateScore(question.getDifficulty(), timeTaken);
            quizManager.addScore(score);
            showModernDialog(
                "CORRECT!\n\nPoints earned: " + score + "\nTotal score: " + quizManager.getTotalScore(),
                "Great!",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            showModernDialog(
                "INCORRECT!\n\nCorrect answer: " + question.getCorrectAnswer(),
                "Oops!",
                JOptionPane.ERROR_MESSAGE);
        }
        
        currentQuestionIndex++;
        
        if (currentQuestionIndex >= TOTAL_QUESTIONS) {
            showResults();
        } else {
            displayQuestion(currentQuestionIndex, questionArea, answerPanel, progressLabel, scoreLabel, timerLabel);
        }
    }
    
    private QuestionInfo extractQuestionInfo(Question question) {
        QuestionInfo info = new QuestionInfo();
        
        Question current = question;
        
        while (current instanceof QuestionDecorator) {
            if (current instanceof HintQuestion) {
                info.hint = ((HintQuestion) current).getHint();
            } else if (current instanceof TimedQuestion) {
                info.timeLimit = ((TimedQuestion) current).getTimeLimit();
            }
            current = ((QuestionDecorator) current).decoratedQuestion;
        }
        
        if (current instanceof MultipleChoiceQuestion) {
            MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) current;
            info.isMultipleChoice = true;
            info.questionText = mcq.getQuestionText();
            info.options = mcq.getOptions();
            info.difficulty = getDifficultyLabel(mcq.getDifficulty());
        } else if (current instanceof BasicQuestion) {
            BasicQuestion bq = (BasicQuestion) current;
            info.isMultipleChoice = false;
            info.questionText = bq.getQuestionText();
            info.difficulty = getDifficultyLabel(bq.getDifficulty());
        }
        
        return info;
    }
    
    private String getDifficultyLabel(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "easy": return Language.get("DIFFICULTY_EASY");
            case "medium": return Language.get("DIFFICULTY_MEDIUM");
            case "hard": return Language.get("DIFFICULTY_HARD");
            default: return difficulty;
        }
    }
    
    private static class QuestionInfo {
        boolean isMultipleChoice;
        String questionText;
        String[] options;
        String difficulty;
        String hint;
        int timeLimit;
    }
    
    private void showResults() {
        JPanel resultPanel = (JPanel) mainPanel.getComponent(3);
        JLabel nameLabel = (JLabel) resultPanel.getClientProperty("nameLabel");
        JLabel scoreLabel = (JLabel) resultPanel.getClientProperty("scoreLabel");
        JLabel strategyLabel = (JLabel) resultPanel.getClientProperty("strategyLabel");
        JLabel ratingLabel = (JLabel) resultPanel.getClientProperty("ratingLabel");
        
        nameLabel.setText(quizManager.getPlayerName());
        scoreLabel.setText(quizManager.getTotalScore() + " / 100");
        strategyLabel.setText(quizManager.getScoringStrategyName());
        
        int score = quizManager.getTotalScore();
        if (score >= 80) {
            ratingLabel.setText(Language.get("RESULT_RATING_PERFECT"));
            ratingLabel.setForeground(SUCCESS_COLOR);
        } else if (score >= 60) {
            ratingLabel.setText(Language.get("RESULT_RATING_GREAT"));
            ratingLabel.setForeground(new Color(234, 179, 8));
        } else if (score >= 40) {
            ratingLabel.setText(Language.get("RESULT_RATING_GOOD"));
            ratingLabel.setForeground(WARNING_COLOR);
        } else {
            ratingLabel.setText(Language.get("RESULT_RATING_KEEP_TRYING"));
            ratingLabel.setForeground(ERROR_COLOR);
        }
        
        cardLayout.show(mainPanel, "RESULT");
    }
    
    private void resetQuiz() {
        currentQuestionIndex = 0;
        quizManager.resetScore();
        quizManager.resetQuiz();
        answerButtonGroup = null;
        answerButtons = null;
        
        // Stop timer if running
        if (countdownTimer != null && countdownTimer.isRunning()) {
            countdownTimer.stop();
        }
    }
    
    /**
     * Create modern card panel with shadow effect
     */
    private JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Add subtle shadow effect using border
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 3, new Color(0, 0, 0, 10)),
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1)
        ));
        
        return card;
    }
    
    /**
     * Create modern border
     */
    private javax.swing.border.Border createModernBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 2, new Color(0, 0, 0, 10)),
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1)
        );
    }
    
    /**
     * Create modern button with hover effect
     */
    private JButton createModernButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    /**
     * Create modern text field with placeholder
     */
    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(360, 45));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_PRIMARY);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
        
        return field;
    }
    
    /**
     * Create badge label
     */
    private JLabel createBadge(String text, Color color) {
        JLabel badge = new JLabel(text);
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(new Color(70, 70, 100)); // Dark text for light badges
        badge.setOpaque(true);
        badge.setBackground(color);
        badge.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
            BorderFactory.createEmptyBorder(6, 14, 6, 14)
        ));
        return badge;
    }
    
    /**
     * Create modern strategy card
     */
    private JPanel createModernStrategyCard(String title, String description, String details, Color accentColor, String badge) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 3, new Color(0, 0, 0, 10)),
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(280, 350));
        card.setMaximumSize(new Dimension(280, 350));
        
        // Accent bar at top
        JPanel accentBar = new JPanel();
        accentBar.setBackground(accentColor);
        accentBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));
        accentBar.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(accentBar);
        
        // Content with padding
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(CARD_BG);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Badge
        JLabel badgeLabel = new JLabel(badge);
        badgeLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badgeLabel.setForeground(accentColor);
        badgeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(badgeLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        
        // Title
        JLabel titleLabel = new JLabel("<html>" + title + "</html>");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        
        // Description
        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(descLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Details panel
        JPanel detailPanel = new JPanel();
        detailPanel.setBackground(new Color(248, 249, 250));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        detailPanel.setLayout(new GridBagLayout());
        detailPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel detailLabel = new JLabel("<html><center>" + details + "</center></html>");
        detailLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        detailLabel.setForeground(accentColor);
        detailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailPanel.add(detailLabel);
        
        contentPanel.add(detailPanel);
        card.add(contentPanel);
        
        // Hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(248, 249, 250));
                contentPanel.setBackground(new Color(248, 249, 250));
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 4, 4, new Color(0, 0, 0, 15)),
                    BorderFactory.createLineBorder(accentColor, 2)
                ));
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_BG);
                contentPanel.setBackground(CARD_BG);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 3, 3, new Color(0, 0, 0, 10)),
                    BorderFactory.createLineBorder(new Color(230, 230, 230), 1)
                ));
            }
        });
        
        return card;
    }
    
    /**
     * Add click handler to strategy card
     */
    private void addStrategyCardClick(JPanel card, Runnable action) {
        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
        });
    }
    
    /**
     * Show modern dialog
     */
    private void showModernDialog(String message, String title, int messageType) {
        UIManager.put("OptionPane.background", CARD_BG);
        UIManager.put("Panel.background", CARD_BG);
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    
    private JButton createStrategyCard(String title, String description, String details) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout(10, 5));
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(Color.GRAY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel detailLabel = new JLabel(details);
        detailLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        detailLabel.setForeground(SECONDARY_COLOR);
        detailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(descLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(detailLabel);
        
        button.add(contentPanel, BorderLayout.CENTER);
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(243, 244, 246));
                contentPanel.setBackground(new Color(243, 244, 246));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                contentPanel.setBackground(Color.WHITE);
            }
        });
        
        return button;
    }
    
    /**
     * Refresh all panels when language changes
     */
    private void refreshAllPanels() {
        // Remove all panels
        mainPanel.removeAll();
        
        // Recreate all panels with new language
        mainPanel.add(createWelcomePanel(), "WELCOME");
        mainPanel.add(createStrategyPanel(), "STRATEGY");
        mainPanel.add(createQuizPanel(), "QUIZ");
        mainPanel.add(createResultPanel(), "RESULT");
        
        // Refresh the display
        mainPanel.revalidate();
        mainPanel.repaint();
        
        // Show welcome screen
        cardLayout.show(mainPanel, "WELCOME");
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new QuizAppGUI());
    }
}
