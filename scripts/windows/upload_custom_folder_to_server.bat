@echo off

"C:\Program Files (x86)\WinSCP\WinSCP.com" ^
  /log="D:\Temp\WinSCP.log" /ini=nul ^
  /command ^
    "open ftp://adp:1@10.54.4.21/" ^
    "synchronize remote C:\Users\ludunyue\Documents\GitHub\sesecd\service\src\main\custom /bap-server/bap-workspace/generate/SESECD_1.0.0/service/src/main/custom" ^
    "exit"

set WINSCP_RESULT=%ERRORLEVEL%
if %WINSCP_RESULT% equ 0 (
  echo Success
) else (
  echo Error
)

exit /b %WINSCP_RESULT%