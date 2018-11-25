@echo off
echo.
echo [信息] 打包工程，生成jar包文件。
echo.
pause
echo.

cd %~dp0
cd..

call mvn clean package -Dmaven.test.skip=true

pause