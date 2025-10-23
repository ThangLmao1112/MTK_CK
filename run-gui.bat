@echo off
echo ========================================
echo Quiz Learning App - Desktop GUI
echo Bilingual: English / Vietnamese
echo ========================================
echo.

echo [1/2] Compiling Java files with UTF-8 encoding...
javac -encoding UTF-8 -d bin src/main/strategy/*.java src/main/question/*.java src/main/manager/*.java src/main/Language.java src/main/QuizAppGUI.java

if %errorlevel% neq 0 (
    echo.
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo [2/2] Running application...
echo.
java -Dfile.encoding=UTF-8 -cp bin main.QuizAppGUI

pause
