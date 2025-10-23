package main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Simple SQLite connection helper.
 * - DB file path: Database/data/quiz.db (relative to project root)
 * - Auto-creates schema if not exists
 */
public final class Database {
    private static final String DB_URL = "jdbc:sqlite:Database\\data\\quiz.db";

    private Database() {}

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        // initialize schema once per process (idempotent)
        initialize(conn);
        return conn;
    }

    private static void initialize(Connection conn) {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS results (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "player_name TEXT NOT NULL, " +
                    "strategy TEXT NOT NULL, " +
                    "score INTEGER NOT NULL, " +
                    "total_questions INTEGER NOT NULL, " +
                    "correct_answers INTEGER NOT NULL, " +
                    "duration_sec INTEGER NOT NULL, " +
                    "played_at TEXT NOT NULL DEFAULT (datetime('now'))" +
                    ")");

            st.executeUpdate("CREATE TABLE IF NOT EXISTS settings (" +
                    "key TEXT PRIMARY KEY, " +
                    "value TEXT" +
                    ")");

            st.executeUpdate("CREATE INDEX IF NOT EXISTS idx_results_score_played_at ON results(score DESC, played_at DESC)");
            st.executeUpdate("CREATE INDEX IF NOT EXISTS idx_results_player_played_at ON results(player_name, played_at DESC)");
        } catch (SQLException ignored) {
            // schema init best-effort; repositories should still work if tables exist
        }
    }
}
