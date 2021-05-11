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
        double simulationDuration = s.nextDouble(); // duracion de simulacion 
        s.nextLine(); // salto a la siguiente linea
        double comunaWidth = s.nextDouble(); // ancho de la comuna
        double comunaLength = s.nextDouble(); // largo de la comuna 
        double speed = s.nextDouble(); //velocidad de los individuos
        double delta_t = s.nextDouble(); // intervalos de tiempo en los que se evaluan la situacion del individuo
        double deltaAngle = s.nextDouble(); // Angulo base de movimiento de cada individuo
        double samplingTime = 1.0;  // 1 [s]
        
        Comuna comuna = new Comuna(comunaWidth, comunaLength); // Creacion de comuna "comuna"

        Individuo person = new Individuo(comuna, speed, deltaAngle); // Creacion de un individuo
        comuna.setPerson(person);// ubicar a la persona en la comuna
        
        Simulador sim = new Simulador(System.out, comuna); //Creacion de la simulacion
        sim.simulate_stage_1(delta_t, simulationDuration,samplingTime); // comienzo de la simulacion de pandemia
    }
}
