

@echo off
chcp 65001 > nul
title Sistema de Biblioteca

set "JAVA_HOME=C:\Program Files\Apache NetBeans\jdk"
set "MAVEN_HOME=C:\Program Files\Apache NetBeans\java\maven"

cd /d "C:\Users\Edwin\Documents\NetBeansProjects\Biblioteca"

echo ========================================
echo          SISTEMA DE BIBLIOTECA
echo ========================================
echo.

call "%MAVEN_HOME%\bin\mvn.cmd" exec:java

echo.
echo ========================================
echo          SISTEMA FINALIZADO
echo ========================================
pause
