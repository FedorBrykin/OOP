javadoc -d build/docs/javadoc -sourcepath src/main/java -subpackages ru.nsu.brykin

javac src/main/java/ru/nsu/brykin/Main.java -d ./build

java -cp ./build ru.nsu.brykin.Main