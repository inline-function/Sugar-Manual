@echo off
set GRADLE_USER_HOME=F:\.gradle
set JAVA_HOME=F:\jdk-17.0.13.11-hotspot
set ANDROID_HOME=F:\AndroidSDK
set USERPROFILE=C:\Users\ZhuanZ
"F:\SublimationProject\Sugar-Manual\android-app\gradlew.bat" -p F:\SublimationProject\Sugar-Manual\android-app :app:assembleDebug --offline
echo EXIT=%ERRORLEVEL%