@echo off
echo ========================================
echo Quiz Learning App - Desktop GUI
echo Bilingual: English / Vietnamese
echo ========================================
echo.

echo [1/3] Compiling Java files with UTF-8 encoding...
javac -encoding UTF-8 -d bin ^
    src/main/strategy/*.java ^
    src/main/question/*.java ^
    src/main/manager/*.java ^
    src/main/db/*.java ^
    src/main/model/*.java ^
    src/main/repository/*.java ^
    src/main/Language.java ^
    src/main/QuizAppGUI.java

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo [2/3] Checking SQLite JDBC driver...
if not exist "lib" mkdir lib
if not exist "lib\sqlite-jdbc.jar" (
    echo WARNING: SQLite JDBC driver not found at lib\sqlite-jdbc.jar
    echo - Please download sqlite-jdbc from https://github.com/xerial/sqlite-jdbc/releases
    echo - Rename the file to sqlite-jdbc.jar and place it under the ^"lib^" folder.
    echo - Continuing to run may fail if the driver is missing.
)

echo [3/3] Running application...
echo.
java -Dfile.encoding=UTF-8 -cp "bin;lib\sqlite-jdbc.jar" main.QuizAppGUI

pause
