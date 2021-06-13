JFLAGS = -g
JC = javac
JVM= java 
FILE="Archivo de entrada.txt"


.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java
        
CLASSES = \
        Stage1.java \
        Stage2.java \
        Stage3.java \
        Stage4.java \
        Comuna.java \
        Individuo.java \
        Simulador.java \
        Vacunatorio.java
        
MAIN = Stage1
MAIN2 = Stage2
MAIN3 = Stage3
MAIN4 = Stage4

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) $(FILE) > salida.csv 

run2: $(MAIN2).class
	$(JVM) $(MAIN2) $(FILE) > salida2.csv 
        
run3: $(MAIN3).class
	$(JVM) $(MAIN3) $(FILE) > salida3.csv

run4: $(MAIN4).class
	$(JVM) $(MAIN4) $(FILE) > salida4.csv
	
clean:
	$(RM) *.class
