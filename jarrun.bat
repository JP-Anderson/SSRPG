del /s *.class
del ssrpg.jar
del /s *.class

dir /s /B *.java > sources.txt
javac @sources.txt

jar cfe ssrpg.jar Game *.class characters goods map ship util
del /s *.class

java -jar ssrpg.jar
