
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
        String s="Persona\ttime\tx\ty";
        //+ Comuna.getStateDescription();
        out.println(s);
    }
    
    private void printState(double t){//ipresion de posicion de cada individuo
        //DecimalFormat df = new DecimalFormat("#,##");
        for(int i=0;i<this.comuna.getListSize();i++){
            String s ="Persona " + (i+1) + "\t"+ Math.round(t*100.0)/100.0 + "\t";
            s+= comuna.getState(i);
            out.println(s);
        }
        comuna.update_estado_gente();
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
        comuna.estado_inicial_final();
        printStateDescription();
        //printState(t);
        while (static_time<endTime) {// recorrer durante el tiempo
            for(double nextStop=static_time+samplingTime; t<=nextStop; t+=delta_t) {
                comuna.computeNextState(delta_t); // compute its next state based on current global state
                comuna.updateState();            // update its state
                comuna.distancia_entre_individuos();
                printState(t);
                //if(t==nextStop)break;
            }
            comuna.probabilidad_de_infeccion(distanciamax);
            comuna.clear();
            static_time+=samplingTime;
            //Problema : se le suma 1 delta_t de mas
        }
        comuna.estado_inicial_final();
    }
    public void simulate4 (double delta_t, double endTime, double samplingTime, double distanciamax, double VacTime, Vacunatorio vacunatorio) {  // simulate time passing for stage 2. 3 and 4
        double t=0;
        double static_time=0;
        //comuna.estado_inicial_final();
        printStateDescription();
        //printState(t);
        while (static_time<endTime) {// recorrer durante el tiempo
            for(double nextStop=static_time+samplingTime; t<=nextStop; t+=delta_t) {
                if(comuna.v == 0){
                    comuna.computeNextState(delta_t); // compute its next state based on current global state
                    comuna.updateState();            // update its state
                    comuna.distancia_entre_individuos();
                    printState(t);
                    //if(t==nextStop)break;
                }else if(comuna.v == 1){
                    comuna.computeNextState_vacunas(delta_t , comuna.get_vacunatorio()); // va a conmutar la posicion con la presencia de un vacunatorio
                    comuna.updateState();
                    comuna.distancia_entre_individuos();
                    printState(t);
                }
            }
            comuna.probabilidad_de_infeccion(distanciamax);
            comuna.clear();
            static_time+=samplingTime;
            if(static_time == VacTime){ // verifica en que momento el vacunatorio empieza a abrirse / aparecer en la comuna
                //System.out.println("\nVacunatorio abierto\n");
                comuna.CreacionVacunatorio(vacunatorio);
            }
            //Problema : se le suma 1 delta_t de mas
        }
        //comuna.estado_inicial_final();
    }
}