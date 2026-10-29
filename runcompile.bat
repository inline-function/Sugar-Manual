@echo off
"F:\jdk-17.0.13.11-hotspot\bin\java.exe" -jar "F:\t61-pull\sub.jar" compile app -o app/out.js -p "F:\KotlinProject\Sublimation\annotation processer\compose.subs" -A composePackage=functor.sublimation.android.gen -A composeOutDir=F:\SublimationProject\Sugar-Manual\out
echo EXIT=%ERRORLEVEL%