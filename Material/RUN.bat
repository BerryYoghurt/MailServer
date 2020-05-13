@ECHO OFF
java -jar MailServer.jar
if %errorlevel% neq 0 (pause)
@ECHO ON