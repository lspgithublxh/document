echo Hello %1,%2,%3
echo yes,yes,yes
goto ff
echo good,good
:ff
@echo off
echo what is this
@echo what is what
call other
choice /C yn /M "ȷ���밴Y,�����밴N"%1
if errorlevel == 1 (echo ��������Y)
if errorlevel == 2 (echo ��������N)
echo %1
mkdir ok
rmdir ok
:off
