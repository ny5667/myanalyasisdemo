@echo off

"C:\Program Files (x86)\WinSCP\WinSCP.com" ^
  /log="D:\Temp\WinSCP.log" /ini=nul ^
  /command ^
    "open ftp://adp:1@10.54.4.40/" ^
    "get /bap-server/assembly/repository/maven/com/supcon/greendill/SESWssER d:\maven40\com\supcon\greendill\SESWssER\" ^
    "get /bap-server/assembly/repository/maven/com/supcon/greendill/SESWssEP d:\maven40\com\supcon\greendill\SESWssEP\" ^
    "get /bap-server/assembly/repository/maven/com/supcon/greendill/SESECD d:\maven40\com\supcon\greendill\SESECD\" ^
    "get /bap-server/assembly/repository/maven/com/supcon/greendill/SESEAB d:\maven40\com\supcon\greendill\SESEAB\" ^
    "get /bap-server/assembly/repository/maven/com/supcon/greendill/SESGISConfig d:\maven40\com\supcon\greendill\SESGISConfig\" ^
    "get /bap-server/assembly/repository/maven/com/supcon/greendill/alarm d:\maven40\com\supcon\greendill\alarm\" ^
    "get /bap-server/assembly/repository/maven/com/supcon/greendill/VideoPlayWeb d:\maven40\com\supcon\greendill\VideoPlayWeb\" ^
    "get /bap-server/assembly/repository/maven/com/supcon/greendill/SESED d:\maven40\com\supcon\greendill\SESED\" ^
    "exit"

set WINSCP_RESULT=%ERRORLEVEL%
if %WINSCP_RESULT% equ 0 (
  echo Success
) else (
  echo Error
)

exit /b %WINSCP_RESULT%