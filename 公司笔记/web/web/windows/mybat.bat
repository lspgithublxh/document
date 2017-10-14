echo Hello %1,%2,%3
echo yes,yes,yes
goto ff
echo good,good
:ff
@echo off
echo what is this
@echo what is what
call other
choice /C yn /M "确认请按Y,否则请按N"%1
if errorlevel == 1 (echo 你输入了Y)
if errorlevel == 2 (echo 你输入了N)
echo %1
mkdir ok
rmdir ok
:off
