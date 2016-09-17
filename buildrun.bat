del /s *.class
dir /s /B *.java > sources.txt
javac @sources.txt
java Game
