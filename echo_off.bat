@echo off
setlocal


set JAVA_HOME=C:\Program Files\Java\jdk-23
set JAVAFX_SDK=C:\Program Files\Java\jdk-23
set MAIN_JAR=Guess_That_Beast.jar
set MAIN_CLASS=com.example.guess_that_beast.Main  
set INPUT_DIR=E:\Learning-Java\Guess That Beast\Guess That Beast\out\artifacts\Guess_That_Beast_jar\Guess_That_Beast.jar
set OUTPUT_DIR=installer
set APP_NAME=GuessThatBeast
set ICON=E:\Learning-Java\Guess That Beast\Guess That Beast\src\main\resources\img\Interface icons\Icon.ico


"%JAVA_HOME%\bin\jpackage.exe" ^
  --type exe ^
  --name "%APP_NAME%" ^
  --input "%INPUT_DIR%" ^
  --main-jar "%MAIN_JAR%" ^
  --main-class "%MAIN_CLASS%" ^
  --java-options "--module-path %JAVAFX_SDK%\lib --add-modules javafx.controls,javafx.fxml" ^
  --icon "%ICON%" ^
  --dest "%OUTPUT_DIR%" ^
  --app-version 1.0

echo ======================
echo Готово! Інсталятор у папці: %OUTPUT_DIR%
pause
