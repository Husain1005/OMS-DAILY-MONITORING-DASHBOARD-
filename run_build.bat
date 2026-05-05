@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-18.0.2.1
set PATH=%JAVA_HOME%\bin;C:\maven\apache-maven-3.9.6\bin;%PATH%
cd /d e:\Monitoring_Dashboard\OMS-DAILY-MONITORING-DASHBOARD-
mvn clean compile
echo Build completed with exit code %ERRORLEVEL%