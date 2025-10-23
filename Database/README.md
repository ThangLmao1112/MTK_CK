# Database folder

This folder contains database-related artifacts for the Quiz Learning App.

## Structure

```
Database/
├─ scripts/
│  ├─ 001_create_tables.sql   # SQLite DDL (results, settings, indexes)
│  └─ 002_seed_demo.sql       # Optional demo data (for development)
└─ data/
   └─ (created at runtime)    # quiz.db will appear here when the app runs
```

## Engine choice: SQLite (embedded)
- Single-file database, no server required, ideal for desktop Swing apps
- Widely supported and easy to inspect with GUI tools (DB Browser for SQLite)

## Default DB file path
The application should use a relative path like:
- `jdbc:sqlite:Database/data/quiz.db`

Java string example:
```java
private static final String DB_URL = "jdbc:sqlite:Database\\data\\quiz.db";
```

## Create schema
The app may auto-create the schema at startup. Alternatively, you can run the scripts manually with any SQLite client:
1) Run `scripts/001_create_tables.sql`
2) (Optional) Run `scripts/002_seed_demo.sql`

## Jar dependency
Use the SQLite JDBC driver (e.g., `sqlite-jdbc-3.45.1.0.jar`) and add it to your classpath.

Windows batch example snippets:
- Compile:
  `javac -encoding UTF-8 -cp "lib\\sqlite-jdbc-<ver>.jar" -d bin ...`
- Run:
  `java -Dfile.encoding=UTF-8 -cp "bin;lib\\sqlite-jdbc-<ver>.jar" main.QuizAppGUI`

## Tables
- results(id, player_name, strategy, score, total_questions, correct_answers, duration_sec, played_at)
- settings(key, value)

## Notes
- The DB file will be created automatically on first connection if it doesn't exist.
- Keep SQL scripts under `scripts/` for reproducibility and classroom submission.
- If you prefer H2, you can mirror the same schema with minor syntax tweaks.
