@echo off
cd /d c:\Users\Harshita\Desktop\project
"C:\Program Files\Git\cmd\git.exe" init
"C:\Program Files\Git\cmd\git.exe" add .
"C:\Program Files\Git\cmd\git.exe" commit -m "Initial commit for AI Startup Strategizer"
"C:\Program Files\Git\cmd\git.exe" branch -M main
"C:\Program Files\Git\cmd\git.exe" remote add origin https://github.com/Harshita0X/Startup_Analyzer.git
"C:\Program Files\Git\cmd\git.exe" push -u origin main
