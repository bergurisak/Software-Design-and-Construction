@echo off
set "JAVA_HOME=C:\Users\bergu\.jdks\temurin-21.0.6"
set "PATH_TO_FX=C:\Users\bergu\.m2\repository\org\openjfx"

"%JAVA_HOME%\bin\java.exe" ^
--module-path "%PATH_TO_FX%\javafx-controls\21;%PATH_TO_FX%\javafx-fxml\21" ^
--add-modules javafx.controls,javafx.fxml ^
-jar target\Slanga-1.0-SNAPSHOT-jar-with-dependencies.jar

pause
