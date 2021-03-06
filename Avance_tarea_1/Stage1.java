import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Stage1 {
    public static void main(String [] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java Stage1Main <configurationFile.txt>");
            System.exit(-1);
        }
        Scanner s=new Scanner(new File(args[0]));
        System.out.print("\n----------------------------------------------------\n");
        System.out.println("File: "+args[0]);
        double simulationDuration = s.nextDouble(); //_Primer numero ingresado
        System.out.println("Simulation time: "+simulationDuration);
        double cantidadIndividuos = s.nextDouble(); // new
        double cantidadInfectados = s.nextDouble(); // new
        s.nextLine(); // se salta 2 , 3 y 4
        double comunaWidth = s.nextDouble(); //_5
        double comunaLength = s.nextDouble();
        double speed = s.nextDouble();
        double delta_t = s.nextDouble();
        System.out.println("delta_t: " + delta_t);
        System.out.print("----------------------------------------------------\n");
        double deltaAngle = s.nextDouble();
        double samplingTime = 1.0;  // 1 [s]
        
        Comuna comuna = new Comuna(comunaWidth, comunaLength); //1

        for(int i=0; i<cantidadIndividuos;i++){
            Individuo person = new Individuo(comuna, speed, deltaAngle); //2
            comuna.setPerson(person);
        }
        comuna.Infectados_random(cantidadInfectados);
        Simulador sim = new Simulador(System.out, comuna); //3
        sim.simulate(delta_t, simulationDuration,samplingTime);
    }
}
