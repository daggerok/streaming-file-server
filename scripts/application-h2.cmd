@echo off

@rem require binaries: which, find, taskkill, scoop (wget) and of course java (binaries: java and jps)

rem SET WhereCmd=C:\Windows\System32\where.exe

rem if not exist %ScoopCmd% (
rem   powershell -noexit "iex (new-object net.webclient).downloadstring('https://get.scoop.sh')"
rem )

rem FOR /f %%i IN ('%WhereCmd% scoop') DO SET ScoopCmd=%%i
rem FOR /f %%i IN ('%WhereCmd% wget') DO SET WgetCmd=%%i

SETLOCAL ENABLEEXTENSIONS

SET ApplicationPath="app"

SET Version=4.3.2
SET FileServerFile=file-server-%Version%.jar
SET FileServerUrl=https://github.com/daggerok/streaming-file-server/releases/download/%Version%/%FileServerFile%
SET FileServerCommand=java -jar %ApplicationPath%\%FileServerFile%
SET FileItemsServiceFile=file-items-service-%Version%.jar
SET FileItemsServiceUrl=https://github.com/daggerok/streaming-file-server/releases/download/%Version%/%FileItemsServiceFile%
SET FileItemsServiceCommand=java -jar %ApplicationPath%\%FileItemsServiceFile%

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
  IF ".%Command%"         == "."      GOTO :UsageBlock
  IF ".%Command%"         == ".stop"  GOTO :StopBlock
  IF ".%FileStoragePath%" == "."      GOTO :UsageBlock
  IF ".%Command%"         == ".start" GOTO :StartBlock
  IF ".%Command%"         == ".clean" GOTO :CleanBlock
ENDLOCAL

GOTO :UsageBlock

:UsageBlock ECHO "require at least one argument."
SETLOCAL
  ECHO Usage:
  ECHO        start            : %0 start path\to\file-storage
  ECHO        stop             : %0 stop
  ECHO        stop and cleanup : %0 clean path\to\file-storage
ENDLOCAL
GOTO :EOF

:GetApplicationFiles
SETLOCAL
  IF NOT EXIST "%ApplicationPath%" (
    CALL MKDIR %ApplicationPath%
  )

  IF NOT EXIST "%ApplicationPath%\%FileItemsServiceFile%" (
    wget -P %ApplicationPath% %FileItemsServiceUrl%
  )

  IF NOT EXIST "%ApplicationPath%\%FileServerFile%" (
    wget -P %ApplicationPath% %FileServerUrl%
  )
ENDLOCAL
GOTO :EOF

:StopFileServerIfRunning
SETLOCAL
  FOR /f "tokens=1" %%A IN ('jps -lv ^| find "%FileServerFile%"') DO (taskkill /F /PID %%A)
ENDLOCAL
GOTO :EOF

:StopFileItemsServiceIfRunning
SETLOCAL
  FOR /f "tokens=1" %%A IN ('jps -lv ^| find "%FileItemsServiceFile%"') DO (taskkill /F /PID %%A)
ENDLOCAL
GOTO :EOF

:StartApplication
SETLOCAL
  CALL :GetApplicationFiles

  CALL :StopFileItemsServiceIfRunning
  START /MIN CMD /C %FileItemsServiceCommand% --spring.profiles.active=db-h2
  ECHO %FileItemsServiceFile% not yet ready, waiting for bootstrap...

  :BeginOfWaiting
  SETLOCAL
    wget -q --spider http://localhost:8001/actuator/health
    if ".%ERRORLEVEL%" == ".0" (
      ECHO %FileItemsServiceFile% is ready!
      GOTO :EndOfWaiting
    )

    TIMEOUT /NOBREAK /t 1
    GOTO :BeginOfWaiting
  ENDLOCAL

  :EndOfWaiting
    CALL :StopFileServerIfRunning

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
  CALL :FileServerIfRunning
  CALL :StopFileItemsServiceIfRunning
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
  DEL /f "%FileStoragePath%"
  DEL /q /f "%ApplicationPath%
ENDLOCAL
GOTO :EOF

ENDLOCAL
