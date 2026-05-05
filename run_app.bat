@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-18.0.2.1
set PATH=%JAVA_HOME%\bin;C:\maven\apache-maven-3.9.6\bin;%PATH%
cd e:\Monitoring_Dashboard\OMS-DAILY-MONITORING-DASHBOARD-
mvn spring-boot:run -X
pause