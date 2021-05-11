
import java.io.PrintStream;
import java.text.DecimalFormat;

public class Simulador {
    private Comuna comuna;
    private PrintStream out;

    private Simulador(){ }// constructor simulador 
    public Simulador (PrintStream output, Comuna comuna){
        out=output;
        this.comuna = comuna;
    }
    
    private void printStateDescription(){//impresion de descripcion de estado
        for(int i=0; i<this.comuna.getListSize(); i++){
            out.println("Persona " + (i+1) + " estado = " + this.comuna.getEstadoPerson(i)+"\n");
        }
        String s="time\tx\ty";
        //+ Comuna.getStateDescription();
        out.println(s);
    }
    
    private void printState(double t){//ipresion de posicion de cada individuo
        DecimalFormat df = new DecimalFormat("#.##");
        out.println("----------------------------------------------------\n");
        for(int i=0;i<this.comuna.getListSize();i++){
            String s = df.format(t) + "\t";
            out.println("Persona " + (i+1) + "\n");
            s+= comuna.getState(i);
            out.println(s);
        }
        comuna.update_estado_gente();
        out.println("----------------------------------------------------\n");
    }

    /**
     * @param delta_t time step
     * @param endTime simulation time
     * @param samplingTime  time between printing states to not use delta_t that would generate too many lines.
     */

    public void simulate_stage_1(double delta_t, double endTime, double samplingTime) {  // simulate time passing for stage_1
        double t=0;
        double static_time=0;
        printStateDescription();
        //printState(t);
        while (static_time<endTime) {// recorrer durante el tiempo
            for(double nextStop=static_time+samplingTime; t<=nextStop; t+=delta_t) {
                comuna.computeNextState(delta_t); // compute its next state based on current global state
                comuna.updateState();            // update its state
                printState(t);
            }
            static_time+=samplingTime;
            //Problema : se le suma 1 delta_t de mas
        }
    }

    public void simulate (double delta_t, double endTime, double samplingTime, double distanciamax) {  // simulate time passing for stage 2. 3 and 4
        double t=0;
        double static_time=0;
        printStateDescription();
        //printState(t);
        while (static_time<endTime) {// recorrer durante el tiempo
            for(double nextStop=static_time+samplingTime; t<=nextStop; t+=delta_t) {
                out.println("valor de nextStop: " + nextStop);
                comuna.computeNextState(delta_t); // compute its next state based on current global state
                comuna.updateState();            // update its state
                comuna.distancia_entre_individuos();
                printState(t);
                //if(t==nextStop)break;
            }
            comuna.probabilidad_de_infeccion(distanciamax);
            comuna.clear();
            out.println("STOP\n");
            static_time+=samplingTime;
            //Problema : se le suma 1 delta_t de mas
        }
    }
}