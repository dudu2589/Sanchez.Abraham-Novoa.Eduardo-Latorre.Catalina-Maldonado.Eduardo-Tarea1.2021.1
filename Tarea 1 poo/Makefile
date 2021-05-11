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
        Comuna.java \
        Individuo.java \
        Simulador.java 
        
MAIN = Stage1
MAIN2 = Stage2
MAIN3 = Stage3

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
	$(JVM) $(MAIN) $(FILE) > salida.csv 

run2: $(MAIN2).class
	$(JVM) $(MAIN2) $(FILE) > salida2.csv 
        
run3: $(MAIN3).class
	$(JVM) $(MAIN3) $(FILE) > salida3.csv 
	
clean:
	$(RM) *.class
