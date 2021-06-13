
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
/*
    objetivo: aleatoriamente poner mascarillas a la gente dependiendo tambien de un porcentaje de probabilidad
*/

public class Stage4 {
    public static void main(String [] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Stage1Main <configurationFile.txt>");
            System.exit(-1);
        }
        Scanner s=new Scanner(new File(args[0]));
        System.out.println("File: "+args[0]);
        double simulationDuration = s.nextDouble(); 
        System.out.println("Simulation time: "+simulationDuration);
        double cantidadIndividuos = s.nextDouble(); 
        double cantidadInfectados = s.nextDouble(); 
        double tiempo_para_recuperacion = s.nextDouble();// new
        double comunaWidth = s.nextDouble(); 
        double comunaLength = s.nextDouble();
        double speed = s.nextDouble();
        double delta_t = s.nextDouble();
        System.out.println("delta_t: " + delta_t);
        double deltaAngle = s.nextDouble();
        double distanciamax = s.nextDouble();
        double porcentaje_gente_con_mascarilla=s.nextDouble();
        double p0=s.nextDouble();
        double p1=s.nextDouble();
        double p2=s.nextDouble();
        double NumVac=s.nextDouble();
        double VacSize=s.nextDouble();
        double VacTime=s.nextDouble();
        double samplingTime = 1.0;  
        
        
        Comuna comuna = new Comuna(comunaWidth, comunaLength); 
        comuna.probabilidad(p0,p1,p2);
        for(int i=0; i<cantidadIndividuos;i++){
            Individuo person = new Individuo(comuna, speed, deltaAngle); 
            comuna.setPerson(person);
        }
        comuna.setlist();
        comuna.Infectados_random(cantidadInfectados);
        comuna.Mascarilla_random(porcentaje_gente_con_mascarilla);
        
        //vacunatorio
        ArrayList <Double> pos = comuna.posicion_aleatoria_vacunatorio(VacSize);
        Vacunatorio vacunatorio = new Vacunatorio(pos.get(0),pos.get(1),pos.get(2),pos.get(3),tiempo_para_recuperacion);
        System.out.println("MInimo: " + vacunatorio.getminWidth());
        System.out.println("MAximo: " + vacunatorio.getWidth());
        vacunatorio.setVacunas(NumVac);
        //simulacion
        Simulador sim = new Simulador(System.out, comuna); 
        sim.simulate(delta_t, simulationDuration,samplingTime,distanciamax,VacTime,vacunatorio);
    }
}
