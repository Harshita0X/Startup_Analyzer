@echo off
cd /d c:\Users\Harshita\Desktop\project
echo [DEBUG] Starting build process...
mvn clean package -DskipTests > build_debug.log 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Build failed. Check build_debug.log
    exit /b %ERRORLEVEL%
)
echo [DEBUG] Build successful. Launching application...
java -jar target\idea-analyzer-1.0-SNAPSHOT.jar > launch_debug.log 2>&1
echo [DEBUG] Application process ended.
