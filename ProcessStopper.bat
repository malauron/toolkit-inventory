@echo off
title Process Stopper.
set /p portNo="Enter the port number: "
netstat -ano | find "%portNo%"
set /p processId="Enter the process ID you wish to terminate: "
Taskkill /F /IM %processId%
pause