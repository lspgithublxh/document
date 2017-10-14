echo other batch file>output.txt
set /a n=20
set /a m=30
if %n% equ %m% echo %n% 等于 %m%
if %n% lss %m% echo %n% 小于 %m%
pause>nul
pause>output.txt
start C:\Windows\System32\calc
echo. |time |find "current" >> log