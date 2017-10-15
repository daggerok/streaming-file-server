@ECHO OFF

@REM require binaries: which, find, taskkill, scoop (wget), docker (docker-compose) and of course java: (java, jps)

REM SET WhereCmd=C:\Windows\System32\where.exe
REM FOR /f %%i IN ('%WhereCmd% docker-compose') DO SET ComposeCmd=%%i

REM IF NOT EXIST %ScoopCmd% (
REM   powershell -noexit "iex (new-object net.webclient).downloadstring('https://get.scoop.sh')"
REM )

REM FOR /f %%i IN ('%WhereCmd% scoop') DO SET ScoopCmd=%%i
REM FOR /f %%i IN ('%WhereCmd% wget') DO SET WgetCmd=%%i

SETLOCAL ENABLEEXTENSIONS

SET InfoLogLevel=0
@REM SET DebugLogLevel=0

SET Version=3.0.0
SET DockerComposeFile=docker-compose.yml
SET DockerComposeUrl=https://github.com/daggerok/streaming-file-server/releases/download/%Version%/%dockerComposeFile%
SET DockerComposeCommand=docker-compose -f %DockerComposeFile%
SET FileServerFile=file-server-%Version%.jar
SET FileServerUrl=https://github.com/daggerok/streaming-file-server/releases/download/%Version%/%FileServerFile%
SET FileServerCommand=java -jar %FileServerFile%
SET FileItemsServiceFile=file-items-service-%Version%.jar
SET FileItemsServiceUrl=https://github.com/daggerok/streaming-file-server/releases/download/%Version%/%FileItemsServiceFile%
SET FileItemsServiceCommand=java -jar %FileItemsServiceFile%

SET Timeout=25

SET Script=%0
SET Command=%1
SET FileStoragePath=%2

:Debug
SETLOCAL
IF DEFINED InfoLogLevel (
  ECHO Script            : "%Script%"
  ECHO Command           : "%Command%"
  ECHO FileStoragePath   : "%FileStoragePath%"
  ECHO All               : "%*"
)
ENDLOCAL

:Info
SETLOCAL
IF DEFINED DebugLogLevel (
  ECHO Version              : "%Version%"
  ECHO DockerComposeFile    : "%DockerComposeFile%"
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

:GetDockerComposeFile
SETLOCAL
FOR %%i IN (%DockerComposeFile%) DO IF NOT EXIST %%~si\NUL (
  wget %DockerComposeUrl%
)
ENDLOCAL
GOTO :EOF

:StartDockerCompose
SETLOCAL
IF NOT EXIST %DockerComposeFile% (
  CALL :GetDockerComposeFile
)
%DockerComposeCommand% up -d --build
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

START %FileItemsServiceCommand%
ECHO waiting %Timeout% for %FileItemsServiceFile% bootstrap...
ping -n %Timeout% 127.0.0.1 >nul

FOR %%i IN ("%FileStoragePath%") DO IF NOT EXIST %%~si\NUL (
  CALL DEL /q /f "%FileStoragePath%"
  CALL MKDIR "%FileStoragePath%"
)
%FileServerCommand% --app.upload.path="%FileStoragePath%"
ENDLOCAL
GOTO :EOF

:StartBlock
SETLOCAL
CALL :StartDockerCompose
CALL :StartApplication
ENDLOCAL
GOTO :EOF

:StopDockerCompose
SETLOCAL
IF NOT EXIST %DockerComposeFile (
  CALL :GetDockerComposeFile
)
%DockerComposeCommand% down -v
ENDLOCAL
GOTO :EOF

:StopApplication
SETLOCAL
FOR /f "tokens=1" %%A IN ('jps -lv ^| find "%FileServerFile%"') DO (taskkill /F /PID %%A)
FOR /f "tokens=1" %%A IN ('jps -lv ^| find "%FileItemsServiceFile%"') DO (taskkill /F /PID %%A)
ENDLOCAL
GOTO :EOF

:StopBlock
SETLOCAL
CALL :StopApplication
CALL :StopDockerCompose
ENDLOCAL
GOTO :EOF

:CleanBlock
SETLOCAL
CALL :StopBlock
DEL /q /f %DockerComposeFile%
DEL /q /f %FileServerFile%
DEL /q /f %FileItemsServiceFile%
DEL /f "%FileStoragePath%"
ENDLOCAL
GOTO :EOF

ENDLOCAL
