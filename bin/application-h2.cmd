@echo off

@rem require binaries: which, find, taskkill, scoop (wget) and of course java (binaries: java and jps)

rem SET WhereCmd=C:\Windows\System32\where.exe

rem if not exist %ScoopCmd% (
rem   powershell -noexit "iex (new-object net.webclient).downloadstring('https://get.scoop.sh')"
rem )

rem FOR /f %%i IN ('%WhereCmd% scoop') DO SET ScoopCmd=%%i
rem FOR /f %%i IN ('%WhereCmd% wget') DO SET WgetCmd=%%i

SETLOCAL ENABLEEXTENSIONS

SET InfoLogLevel=0
@rem SET DebugLogLevel=0

SET Version=3.0.0
SET FileServerFile=streaming-file-server-%Version%.jar
SET FileServerUrl=https://github.com/daggerok/streaming-file-server/releases/download/%Version%/%FileServerFile%
SET FileServerCommand=java -jar %FileServerFile%
SET FileItemsServiceFile=file-items-rest-service-%Version%.jar
SET FileItemsServiceUrl=https://github.com/daggerok/streaming-file-server/releases/download/%Version%/%FileItemsServiceFile%
SET FileItemsServiceCommand=java -jar %FileItemsServiceFile%

SET Timeout=25

SET Script=%0
SET Command=%1
SET FileStoragePath=%2

:Debug
SETLOCAL
IF DEFINED InfoLogLevel (
  ECHO Script               : "%Script%"
  ECHO Command              : "%Command%"
  ECHO FileStoragePath      : "%FileStoragePath%"
  ECHO All                  : "%*"
)
ENDLOCAL

:Info
SETLOCAL
IF DEFINED DebugLogLevel (
  ECHO Version              : "%Version%"
  ECHO FileServerFile       : "%FileServerFile%"
  ECHO FileItemsServiceFile : "%FileItemsServiceFile%"
  ECHO LogLevel             : "%LogLevel%"
)
ENDLOCAL

:ValidateInputs
SETLOCAL
IF ".%Command%" == "." GOTO :UsageBlock
IF ".%Command%" == ".stop" GOTO :StopBlock
IF ".%FileStoragePath%" == "." GOTO :UsageBlock
IF ".%Command%" == ".start" GOTO :StartBlock
IF ".%Command%" == ".clean" GOTO :CleanBlock
ENDLOCAL

GOTO UsageBlock

:UsageBlock ECHO "require at least one argument."
SETLOCAL
ECHO Usage:
ECHO        start        : %0 start path\to\file-storage
ECHO        stop         : %0 stop
ECHO        cleanup      : %0 clean path\to\file-storage
ENDLOCAL
GOTO :EOF

:GetFileServerFile
SETLOCAL
FOR %%i IN (%FileItemsServiceFile%) DO IF NOT EXIST %%~si\NUL (
  wget %FileItemsServiceUrl%
)

FOR %%i IN (%FileServerFile%) DO IF NOT EXIST %%~si\NUL (
  wget %FileServerUrl%
)
ENDLOCAL
GOTO :EOF

:StartApplication
SETLOCAL
IF NOT EXIST %FileServerFile% (
  CALL :GetFileServerFile
)

START %FileItemsServiceCommand% --spring.profiles.active=db-h2
ECHO waiting %Timeout% FOR %FileItemsServiceFile% bootstrap...
PING -n %Timeout% 127.0.0.1 >nul

FOR %%i IN ("%FileStoragePath%") DO IF NOT EXIST %%~si\NUL (
  CALL DEL /q /f "%FileStoragePath%"
  CALL MKDIR "%FileStoragePath%"
)
%FileServerCommand% --app.upload.path="%FileStoragePath%"
ENDLOCAL
GOTO :EOF

:StartBlock
SETLOCAL
CALL :StartApplication
ENDLOCAL
GOTO :EOF

:StopApplication
SETLOCAL
FOR /f "tokens=1" %%A IN ('jps -lv ^| find "%FileServerFile%"') DO (TASKKILL /F /PID %%A)
FOR /f "tokens=1" %%A IN ('jps -lv ^| find "%FileItemsServiceFile%"') DO (TASKKILL /F /PID %%A)
ENDLOCAL
GOTO :EOF

:StopBlock
SETLOCAL
CALL :StopApplication
ENDLOCAL
GOTO :EOF

:CleanBlock
SETLOCAL
CALL :StopBlock
DEL /q /f %FileServerFile%
DEL /q /f %FileItemsServiceFile%
DEL /f "%FileStoragePath%"
ENDLOCAL
GOTO :EOF

ENDLOCAL
