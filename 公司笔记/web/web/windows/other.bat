echo other batch file>output.txt
set /a n=20
set /a m=30
if %n% equ %m% echo %n% ���� %m%
if %n% lss %m% echo %n% С�� %m%
pause>nul
pause>output.txt
start C:\Windows\System32\calc
echo. |time |find "current" >> log