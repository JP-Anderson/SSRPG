cd src
del /s *.class

cd ..

dir /s /B *.java | findstr /v Test.java > sources.txt
javac @sources.txt

cd src

java Game

del /s *.class