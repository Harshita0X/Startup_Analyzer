@echo off
cd /d c:\Users\Harshita\Desktop\project
if not exist "src\main\resources\images" mkdir "src\main\resources\images"
copy /Y "C:\Users\Harshita\.gemini\antigravity\brain\f8357b82-3a06-42b4-9ca2-3a7fa4b9b0e2\gradient_background_png_1773765782160.png" "src\main\resources\images\bg.png"
copy /Y "C:\Users\Harshita\.gemini\antigravity\brain\f8357b82-3a06-42b4-9ca2-3a7fa4b9b0e2\trading_card_1_png_1773765801991.png" "src\main\resources\images\card1.png"
copy /Y "C:\Users\Harshita\.gemini\antigravity\brain\f8357b82-3a06-42b4-9ca2-3a7fa4b9b0e2\trading_card_2_png_1773765820239.png" "src\main\resources\images\card2.png"
copy /Y "C:\Users\Harshita\.gemini\antigravity\brain\f8357b82-3a06-42b4-9ca2-3a7fa4b9b0e2\trading_card_3_png_1773765844109.png" "src\main\resources\images\card3.png"
copy /Y "C:\Users\Harshita\.gemini\antigravity\brain\f8357b82-3a06-42b4-9ca2-3a7fa4b9b0e2\trading_card_4_png_1773765862014.png" "src\main\resources\images\card4.png"
mvn clean package -DskipTests
java -jar target\idea-analyzer-1.0-SNAPSHOT.jar
