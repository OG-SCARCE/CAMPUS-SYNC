@echo off
title Campus Sync - Tomcat Launcher
color 0d

echo Starting Apache Tomcat...
echo.

REM Go to the folder where THIS file is located
cd /d "%~dp0"

REM Navigate to tomcat startup file using RELATIVE PATH
cd "CampusSync_Files\CampusSync_Tomcat\apache-tomcat-9.0.112\bin"

REM Run the startup script
call startup.bat

echo.
echo Tomcat Started Successfully!
pause
