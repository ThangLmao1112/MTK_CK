-- Optional demo data for development/testing
BEGIN TRANSACTION;

INSERT INTO results(player_name, strategy, score, total_questions, correct_answers, duration_sec, played_at)
VALUES
 ('Alice', 'FixedScore',     90, 10, 9, 120, datetime('now','-3 days')),
 ('Bob',   'Difficulty',     85, 10, 8, 160, datetime('now','-2 days')),
 ('Carol', 'Speed',          95, 10, 9, 100, datetime('now','-1 days')),
 ('Dave',  'FixedScore',     70, 10, 7, 180, datetime('now','-5 hours')),
 ('Eve',   'Speed',         100, 10,10,  85, datetime('now','-1 hours'));

INSERT OR REPLACE INTO settings(key, value) VALUES
 ('LANG', 'EN'),
 ('LAST_STRATEGY', 'FixedScore');

COMMIT;