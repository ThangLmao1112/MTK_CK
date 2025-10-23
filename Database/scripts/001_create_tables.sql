-- Quiz Learning App - SQLite schema
-- This script creates the minimal schema used by the application.
-- Run order: 001_create_tables.sql -> 002_seed_demo.sql (optional)

BEGIN TRANSACTION;

-- Results: store each finished quiz attempt (for leaderboard/statistics)
CREATE TABLE IF NOT EXISTS results (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    player_name      TEXT    NOT NULL,
    strategy         TEXT    NOT NULL,  -- FixedScore | Difficulty | Speed
    score            INTEGER NOT NULL,
    total_questions  INTEGER NOT NULL,
    correct_answers  INTEGER NOT NULL,
    duration_sec     INTEGER NOT NULL,  -- total duration of the run
    played_at        TEXT    NOT NULL DEFAULT (datetime('now')) -- ISO8601
);

-- Settings: simple key-value store (optional)
CREATE TABLE IF NOT EXISTS settings (
    key   TEXT PRIMARY KEY,
    value TEXT
);

-- Helpful indexes
CREATE INDEX IF NOT EXISTS idx_results_score_played_at
ON results(score DESC, played_at DESC);

CREATE INDEX IF NOT EXISTS idx_results_player_played_at
ON results(player_name, played_at DESC);

COMMIT;