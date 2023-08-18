@echo off

"C:\Program Files (x86)\WinSCP\WinSCP.com" ^
  /log="D:\Temp\WinSCP.log" /ini=nul ^
  /command ^
    "open ftp://adp:1@10.54.4.40/" ^
    "get /bap-server/assembly/repository/maven/* D:\maven40\" ^
    "exit"

set WINSCP_RESULT=%ERRORLEVEL%
if %WINSCP_RESULT% equ 0 (
  echo Success
) else (
  echo Error
)

exit /b %WINSCP_RESULT%