//import jdk.nashorn.internal.ir.BlockLexicalContext;
import java.util.ArrayList;

public class Individuo {
    private double x, y, speed, angle, deltaAngle;
    private double x_tPlusDelta, y_tPlusDelta;
    private double porcentaje_infectado;
    private Comuna comuna;
    private int estado; //(0=suseptible a infectarse / 1=infectado / 2=recuperado / 3=vacunado)
    private boolean mascarilla; //(true = usa mascarilla / false = no usa mascarilla)
    private ArrayList<ArrayList<Double>> distancia_entre_personas = new ArrayList<ArrayList<Double>>();  
    /*
    El individuo pertenece a x comuna
    posee una velocidad 
    */
    public Individuo(){
        x = 0;
        y = 0;
        speed = 0;
        angle = 0;
        deltaAngle = 0;
        porcentaje_infectado = 0;

        estado = 0; //la gente por lo general esta sano
        mascarilla = false ; //la gente no nace con mascarilla
    }
    public Individuo (Comuna comuna, double speed, double deltaAngle){
        this.comuna = comuna;
        this.speed = speed;
        this.deltaAngle = deltaAngle;
        this.x = Math.round(Math.random()*(comuna.getWidth())); 
        this.y = Math.round(Math.random()*(comuna.getHeight()));
        this.angle = Math.round(Math.random()*2*Math.PI);
    }
    public static String getStateDescription(){
        return "x,\ty";
    }
    public int getEstado(){
        return this.estado;
    }
    public void setEstado(int newEstado){
        this.estado=newEstado;
    }
    public boolean getMascarilla(){
        return this.mascarilla;
    }
    public double getPorcentaje_infectado(){
        return this.porcentaje_infectado;
    }
    public void setPorcentaje_infectado(double i){
        this.porcentaje_infectado+=i;
    }
    public void setMascarilla(Boolean mascarilla){
        this.mascarilla=mascarilla;
    }
    public String getState() {
        return x + "\t" + y + "\nEstado: " + estado + "\nMascarilla: "+ mascarilla + "\n";
    }
    public void computeNextState(double delta_t) { //computar siguiente movimiento aleatorio
        double r=Math.random();

        this.angle+=deltaAngle;
        x_tPlusDelta=x+Math.round(speed*Math.cos(angle));
        y_tPlusDelta=y+Math.round(speed*Math.sin(angle));
        
        if(x_tPlusDelta < 0){   // rebound logic
            x_tPlusDelta=0;
        }
        else if( x_tPlusDelta > this.comuna.getWidth()){
            x_tPlusDelta=this.comuna.getWidth();
        }
        if(y_tPlusDelta < 0){   // rebound logic
            y_tPlusDelta=0;
        }
        else if( y_tPlusDelta > this.comuna.getHeight()){
            y_tPlusDelta=this.comuna.getHeight();
        }
        
    }
    public void updateState(){ //actualizar 
        this.x=x_tPlusDelta;
        this.y=y_tPlusDelta;
    }
    ////////////////////////
    public double promedio(ArrayList<Double> lista){
        double suma=0;
        for(int i=0;i<lista.size();i++){
            suma+=lista.get(i);
        }
        return suma/lista.size();
    }
    public void listAppend(ArrayList<Double> lista){
        distancia_entre_personas.add(lista);
    } 
    public void list_in_listAppend(double distancia , int i){
        distancia_entre_personas.get(i).add(distancia);
    }

    public void interaccionIndividuos(Individuo individuo_cercano,int i){
        double distancia;
        distancia = Math.pow((Math.pow((this.x - individuo_cercano.x),2)+Math.pow((this.y - individuo_cercano.y),2)),(0.5));
        distancia_entre_personas.get(i).add(distancia);
        individuo_cercano.list_in_listAppend(distancia, i);
    }

    public void probabilidad_de_infeccion(Individuo individuo_cercano, double distancia_infeccion , int i){
        double distancia=0;
        distancia = promedio(distancia_entre_personas.get(i));
        if(distancia<=distancia_infeccion){
            System.out.println("LLEGO ACA , TENEMOS PROBABILIDAD DE INFECCION");
            if(this.estado==1 && individuo_cercano.estado==0 ){
                individuo_cercano.setPorcentaje_infectado(comuna.prob_infeccion(this.mascarilla,individuo_cercano));
                if(individuo_cercano.getPorcentaje_infectado()>=1)
                    individuo_cercano.setEstado(1);
            }else if(this.estado==0 && individuo_cercano.estado==1 ){
                porcentaje_infectado+=comuna.prob_infeccion(this.mascarilla,individuo_cercano);
                if(this.porcentaje_infectado>=1)
                    this.estado=1;
            }
        }
    }

}
