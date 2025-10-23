package main.repository;

import main.db.Database;
import main.model.Result;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultRepository {

    public boolean save(Result r) {
        String sql = "INSERT INTO results(player_name, strategy, score, total_questions, correct_answers, duration_sec) " +
                     "VALUES(?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getPlayerName());
            ps.setString(2, r.getStrategy());
            ps.setInt(3, r.getScore());
            ps.setInt(4, r.getTotalQuestions());
            ps.setInt(5, r.getCorrectAnswers());
            ps.setLong(6, r.getDurationSec());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            // log minimal; avoid crashing GUI
            System.err.println("[DB] save result failed: " + e.getMessage());
            return false;
        }
    }

    public List<Result> listTop(int limit) {
        String sql = "SELECT id, player_name, strategy, score, total_questions, correct_answers, duration_sec, played_at " +
                     "FROM results ORDER BY score DESC, played_at DESC LIMIT ?";
        List<Result> list = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Result r = new Result(
                        rs.getString("player_name"),
                        rs.getString("strategy"),
                        rs.getInt("score"),
                        rs.getInt("total_questions"),
                        rs.getInt("correct_answers"),
                        rs.getLong("duration_sec")
                    );
                    r.setId(rs.getInt("id"));
                    r.setPlayedAt(rs.getString("played_at"));
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            System.err.println("[DB] listTop failed: " + e.getMessage());
        }
        return list;
    }
    
    public boolean deleteById(int id) {
        String sql = "DELETE FROM results WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            System.err.println("[DB] deleteById failed: " + e.getMessage());
            return false;
        }
    }
}
