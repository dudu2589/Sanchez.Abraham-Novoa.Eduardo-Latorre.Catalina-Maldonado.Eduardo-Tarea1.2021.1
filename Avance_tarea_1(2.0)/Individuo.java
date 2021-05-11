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
   
    public Individuo(){//constructor individuo
        x = 0;
        y = 0;
        speed = 0;
        angle = 0;
        deltaAngle = 0;
        porcentaje_infectado = 0;

        estado = 0; //la gente por lo general esta sano
        mascarilla = false ; //la gente no nace con mascarilla
    }
    public Individuo (Comuna comuna, double speed, double deltaAngle){//constructor 2 individuo 
        this.comuna = comuna;
        this.speed = speed;
        this.deltaAngle = deltaAngle;
        this.x = Math.round(Math.random()*(comuna.getWidth())); 
        this.y = Math.round(Math.random()*(comuna.getHeight()));
        this.angle = Math.round(Math.random()*2*Math.PI);
    }
    public int getEstado(){//recibir estado de salud del individuo
        return this.estado;
    }
    public void setEstado(int newEstado){//cambiar estado de salud del individuo
        this.estado=newEstado;
    }
    public boolean getMascarilla(){//recibir si el individuo  tiene o no tiene mascarilla 
        return this.mascarilla;
    }
    public void setMascarilla(Boolean mascarilla){//poner o sacar mascarilla a individuo
        this.mascarilla=mascarilla;
    }
    public double getPorcentaje_infectado(){//recibir porcentaje de infeccion de un individuo
        return this.porcentaje_infectado;
    }
    public void setPorcentaje_infectado(double i){//añadir porcentaje de infeccion a un individuo
        this.porcentaje_infectado+=i;
    }
    
    public String getState() {//imprimir posicion de individuo , su estado de salud y su uso de mascarilla
        return x + "\t" + y ;
    }
    public void computeNextState(double delta_t) { //computar siguiente movimiento aleatorio
    
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
        /*calcular promedio de la distancia en la que se encuentra
        dos individuos en un periodo de tiempo de 1 segundo
        */
        double suma=0;
        for(int i=0;i<lista.size();i++){
            suma+=lista.get(i);
        }
        return suma/lista.size();
    }
    public void listAppend(ArrayList<Double> lista){
        /*
        A cada persona en su lista se le agrega un bloque donde iran
        las distancias entre un individuo y la gente a su alrededor   
        */
        distancia_entre_personas.add(lista);
    } 
    public void list_in_listAppend(double distancia , int i){
        /*
        cada bloque de distancia tiene distancias que se toman a travez de 
        el intervalo de tiempo delta_t
        */
        distancia_entre_personas.get(i).add(distancia);
    }
    public void list_in_listClear(int i){//limpiar distancias evaluadas en 1 segundo para evaluarlas en un segundo proximo
        for(int j=0;j<distancia_entre_personas.get(i).size();j++){
            distancia_entre_personas.get(i).remove(0);
        };
     }
    public void interaccionIndividuos(Individuo individuo_cercano,int i){
        /*
        Calcula la distancia en la que se encuentran 2 personas 
        */
        double distancia;
        distancia = Math.pow((Math.pow((this.x - individuo_cercano.x),2)+Math.pow((this.y - individuo_cercano.y),2)),(0.5));
        distancia_entre_personas.get(i).add(distancia);
        individuo_cercano.list_in_listAppend(distancia, i);
    }

    public void probabilidad_de_infeccion(Individuo individuo_cercano, double distancia_infeccion , int i){
        /*
        entre 2 individuos , sabiendo su promedio de distancias tomadas de los delta_t en 1 segundo
        , el uso de mascarillas entre ambos individuos y el estado de cada uno, 
        y sabiedo la distancia maxima a la que pueden estar 2 individuos , se calcula la probabilidad
        de infeccion 
        */
        double distancia=0;
        distancia = promedio(distancia_entre_personas.get(i));
        if(distancia<=distancia_infeccion){
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
