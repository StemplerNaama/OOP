# 312118599 311318661
# yanayet stempln
compile: bin
	find src -name "*.java" > sources.txt
	javac -d bin -cp biuoop-1.4.jar @sources.txt
run: jar
	java -jar ass6game.jar
jar: 
	jar cfm ass6game.jar manifest.mf -C bin . -C resources .
bin:
	mkdir bin