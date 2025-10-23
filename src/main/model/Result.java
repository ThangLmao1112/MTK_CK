package main.model;

public class Result {
    private int id;
    private String playerName;
    private String strategy;
    private int score;
    private int totalQuestions;
    private int correctAnswers;
    private long durationSec;
    private String playedAt; // ISO8601 string (optional when reading)

    public Result(String playerName, String strategy, int score,
                  int totalQuestions, int correctAnswers, long durationSec) {
        this.playerName = playerName;
        this.strategy = strategy;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.durationSec = durationSec;
    }

    public int getId() { return id; }
    public String getPlayerName() { return playerName; }
    public String getStrategy() { return strategy; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCorrectAnswers() { return correctAnswers; }
    public long getDurationSec() { return durationSec; }
    public String getPlayedAt() { return playedAt; }

    public void setId(int id) { this.id = id; }
    public void setPlayedAt(String playedAt) { this.playedAt = playedAt; }
}
