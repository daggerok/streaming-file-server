@echo off

@rem require binaries: which, find, taskkill, scoop (wget) and of course java: (java, jps)

rem SET WhereCmd=C:\Windows\System32\where.exe

rem if not exist %ScoopCmd% (
rem   powershell -noexit "iex (new-object net.webclient).downloadstring('https://get.scoop.sh')"
rem )

rem for /f %%i in ('%WhereCmd% scoop') do SET ScoopCmd=%%i
rem for /f %%i in ('%WhereCmd% wget') do SET WgetCmd=%%i

SETLOCAL ENABLEEXTENSIONS

SET InfoLogLevel=0
@rem SET DebugLogLevel=0

SET Version=2.1.2
SET ApplicationFile=streaming-file-server-%Version%.jar
SET ApplicationUrl=https://github.com/daggerok/streaming-file-server/releases/download/%Version%/%ApplicationFile%
SET ApplicationCommand=java -jar %ApplicationFile% --spring.profiles.active=db-h2

SET Script=%0
SET Command=%1
SET FileStoragePath=%2


:Debug
setlocal
if DEFINED InfoLogLevel (
  echo Script          : "%Script%"
  echo Command         : "%Command%"
  echo FileStoragePath : "%FileStoragePath%"
  echo All             : "%*"
)
endlocal

:Info
setlocal
if DEFINED DebugLogLevel (
  echo Version           : "%Version%"
  echo ApplicationFile   : "%ApplicationFile%"
  echo LogLevel          : "%LogLevel%"
)
endlocal

:ValidateInputs
setlocal
if ".%Command%" == "." goto :UsageBlock
if ".%Command%" == ".stop" goto :StopBlock
if ".%FileStoragePath%" == "." goto :UsageBlock
if ".%Command%" == ".start" goto :StartBlock
if ".%Command%" == ".clean" goto :CleanBlock
endlocal

goto UsageBlock

:UsageBlock echo "require at least one argument."
setlocal
echo Usage:
echo        start      : %0 start path\to\file-storage
echo        stop       : %0 stop
echo        cleanup    : %0 clean path\to\file-storage
endlocal
goto :eof

:GetApplicationFile
setlocal
FOR %%i IN (%ApplicationFile%) DO IF NOT EXIST %%~si\NUL (
  wget %ApplicationUrl%
)
endlocal
goto :eof

:StartApplication
setlocal
if NOT EXIST %ApplicationFile% (
  call :GetApplicationFile
)
FOR %%i IN ("%FileStoragePath%") DO IF NOT EXIST %%~si\NUL (
  CALL DEL /q /f "%FileStoragePath%"
  CALL MKDIR "%FileStoragePath%"
)
%ApplicationCommand% --app.upload.path="%FileStoragePath%"
endlocal
goto :eof

:StartBlock
setlocal
call :StartApplication
endlocal
goto :eof

:StopApplication
setlocal
for /f "tokens=1" %%A in ('jps -lv ^| find "%ApplicationFile%"') do (taskkill /F /PID %%A)
endlocal
goto :eof

:StopBlock
setlocal
call :StopApplication
endlocal
goto :eof

:CleanBlock
setlocal
call :StopBlock
del /q /f %ApplicationFile%
del /f "%FileStoragePath%"
endlocal
goto :eof

ENDLOCAL
