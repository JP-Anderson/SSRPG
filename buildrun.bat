del *.class
if not exist ".\bin" mkdir .\bin
javac *.java -d bin
cd bin
java Game
