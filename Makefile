all:
	javac *.java
	rmic HRImpl -nowarn
	$(info >> Run 'java HRServer' on new terminal)
	$(info >> Run 'java HRClient' on new terminal)
	rmiregistry
clean:
	rm *.class
