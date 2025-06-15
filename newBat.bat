@echo off
setlocal

set "JAVA_HOME=C:\Program Files\Java\jdk-23"
set "JAVAFX_SDK=E:\Learning-Java\Guess That Beast\javafx-sdk-24.0.1"
set "MAIN_JAR=E:\Learning-Java\Guess That Beast\Guess That Beast\out\artifacts\Guess_That_Beast_jar\Guess_That_Beast.jar"
set "MAIN_CLASS=E:\Learning-Java\Guess That Beast\Guess That Beast\src\main\java\com\example\guess_that_beast\OpenApp.java"
set "INPUT_DIR=E:\Learning-Java\Guess That Beast\Guess That Beast\target"
set "OUTPUT_DIR=installer"
set "APP_NAME=GuessThatBeast"

"%JAVA_HOME%\bin\jpackage.exe" ^
  --type exe ^
  --name "%APP_NAME%" ^
  --input "%INPUT_DIR%" ^
  --main-jar "%MAIN_JAR%" ^
  --main-class "%MAIN_CLASS%" ^
  --java-options "--module-path \"%JAVAFX_SDK%\lib\" --add-modules javafx.controls,javafx.fxml" ^
  --dest "%OUTPUT_DIR%" ^
  --app-version 1.0

echo ======================
echo Gotowe! Instalator znajduje sie w folderze: %OUTPUT_DIR%
pause
