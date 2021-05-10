import java.io.File;
import java.io.IOException;
import java.util.Scanner;
/*
    objetivo: aleatoriamente poner mascarillas a la gente dependiendo tambien de un porcentaje de probabilidad
*/

public class Stage3 {
    public static void main(String [] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Stage1Main <configurationFile.txt>");
            System.exit(-1);
        }
        Scanner s=new Scanner(new File(args[0]));
        System.out.println("File: "+args[0]);
        double simulationDuration = s.nextDouble(); //_Primer numero ingresado
        System.out.println("Simulation time: "+simulationDuration);
        double cantidadIndividuos = s.nextDouble(); 
        double cantidadInfectados = s.nextDouble(); 
        s.nextLine(); 
        double comunaWidth = s.nextDouble(); 
        double comunaLength = s.nextDouble();
        double speed = s.nextDouble();
        double delta_t = s.nextDouble();
        System.out.println("delta_t: " + delta_t);
        double deltaAngle = s.nextDouble();
        double distanciamax = s.nextDouble();//distancia maxima que deben estar las personas , inferior a eso , contagio 
        double porcentaje_gente_con_mascarilla=s.nextDouble();
        double p0=s.nextDouble();
        double p1=s.nextDouble();
        double p2=s.nextDouble();
        double samplingTime = 1.0;  // 1 [s]
        
        Comuna comuna = new Comuna(comunaWidth, comunaLength); //1
        comuna.probabilidad(p0,p1,p2);
        for(int i=0; i<cantidadIndividuos;i++){
            Individuo person = new Individuo(comuna, speed, deltaAngle); //2
            comuna.setPerson(person);
        }
        comuna.setlist();
        comuna.Infectados_random(cantidadInfectados);
        comuna.Mascarilla_random(porcentaje_gente_con_mascarilla);
        Simulador sim = new Simulador(System.out, comuna); //3
        sim.simulate(delta_t, simulationDuration,samplingTime,distanciamax);
    }
}
