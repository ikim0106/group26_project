comp:
	javac Main.java dbtools/*.java interfaces/*.java
run: 
	java -cp ./mysql-jdbc.jar:. Main

clear:
	rm *.class
	rm dbtools/*.class
	rm interfaces/*.class

all:
	make clear
	make comp
	make run