import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Stage2 {
    public static void main(String [] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Stage1Main <configurationFile.txt>");
            System.exit(-1);
        }
        Scanner s=new Scanner(new File(args[0]));
        double simulationDuration = s.nextDouble(); 
        double cantidadIndividuos = s.nextDouble(); //Cantidad de individuos en una comuna
        double cantidadInfectados = s.nextDouble(); //Cantidad de infectados en una comuna 
        s.nextLine(); 
        double comunaWidth = s.nextDouble(); 
        double comunaLength = s.nextDouble();
        double speed = s.nextDouble();
        double delta_t = s.nextDouble();
        double deltaAngle = s.nextDouble();
        double distanciamax = s.nextDouble();//distancia maxima que deben estar las personas , inferior a eso , contagio 
        s.next();
        double p0=s.nextDouble(); // probabilidad de infeccion si nadie usa mascarilla
        double samplingTime = 1.0;  // 1 [s]
        
        Comuna comuna = new Comuna(comunaWidth, comunaLength); //1
        comuna.probabilidad(p0,0,0);//en comuna se asigna la probabilidad de infeccion
        for(int i=0; i<cantidadIndividuos;i++){//se agregan individuos a la comuna
            Individuo person = new Individuo(comuna, speed, deltaAngle); //2
            comuna.setPerson(person);
        }
        comuna.setlist();//Creacion de sublistas en una lista de cada individuo
        comuna.Infectados_random(cantidadInfectados);//infectar de manera aleatoria a la gente
        Simulador sim = new Simulador(System.out, comuna); //3
        sim.simulate(delta_t, simulationDuration,samplingTime,distanciamax);//comienso de simulacion con mas personas
    }
}
