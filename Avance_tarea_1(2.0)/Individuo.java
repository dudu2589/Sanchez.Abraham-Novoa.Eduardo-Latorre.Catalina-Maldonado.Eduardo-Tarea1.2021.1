//import jdk.nashorn.internal.ir.BlockLexicalContext;
import java.util.ArrayList;

public class Individuo {
    private double x, y, speed, angle, deltaAngle;
    private double x_tPlusDelta, y_tPlusDelta;
    private double porcentaje_infectado;
    private double df; //persona dentro = 1 , persona fuera = 0
    private double time_rec;
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
        df = 0;
        time_rec = 0;
        estado = 0; //la gente por lo general esta sano
        mascarilla = false ; //la gente no nace con mascarilla
    }
    public Individuo (Comuna comuna, double speed, double deltaAngle){//constructor 2 individuo 
        this.comuna = comuna;
        this.speed = speed;
        this.deltaAngle = deltaAngle;
        this.x = Math.round(Math.random()*(comuna.getWidth())); 
        this.y = Math.round(Math.random()*(comuna.getHeight()));
        this.angle = Math.random()*2*Math.PI;
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
        
        double angulo_aleatorio = Math.random()*((angle + deltaAngle) - (angle - deltaAngle) + 1) + (angle - deltaAngle);
        x_tPlusDelta=x+Math.round(speed*Math.cos(angulo_aleatorio));
        y_tPlusDelta=y+Math.round(speed*Math.sin(angulo_aleatorio));
        
        if(x_tPlusDelta < 0){   // rebound logic
            x_tPlusDelta=0;
            this.angle = Math.random()*2*Math.PI;
        }
        else if( x_tPlusDelta > this.comuna.getWidth()){
            x_tPlusDelta=this.comuna.getWidth();
            this.angle = Math.random()*2*Math.PI;
        }
        if(y_tPlusDelta < 0){   // rebound logic
            y_tPlusDelta=0;
            this.angle = Math.random()*2*Math.PI;
        }
        else if( y_tPlusDelta > this.comuna.getHeight()){
            y_tPlusDelta=this.comuna.getHeight();
            this.angle = Math.random()*2*Math.PI;
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
    //Funciones Stage 4 vacunatorio
    public double x_recursivo(Double x_pos , Double x_min , Double x_max){
        /*
            Aleja a un infectado o a un recuperado del vacunatorio en el eje x
        */
        double x_pos_return = 0;
        if (x_pos>x_min && x_pos<x_max){
            if(Math.abs(x_pos-x_min) > Math.abs(x_pos-x_max)){
                x_pos_return = x_recursivo(x_pos+1, x_min, x_max);
            }else{
                x_pos_return = x_recursivo(x_pos-1, x_min, x_max);
            }
        }else{
            x_pos_return = x_pos;
        }
        return x_pos_return;
    }
    public double y_recursivo(Double y_pos , Double y_min , Double y_max){
        /*
            Aleja a un infectado o a un recuperado del vacunatorio en el eje y
        */
        double y_pos_return = 0;
        if (y_pos>y_min && y_pos<y_max){
            if(Math.abs(y_pos-y_min) > Math.abs(y_pos-y_max)){
                y_pos_return = y_recursivo(y_pos+1, y_min, y_max);
            }else{
                y_pos_return = y_recursivo(y_pos-1, y_min, y_max);
            }
        }else{
            y_pos_return = y_pos;
        }
        return y_pos_return;
    }
    public double getdf(){ //df = dentro o fuera , retorna si esta dentro o fuera del vacunatorio
        return this.df;
    }
    public void computeNextState_vacunas(Double delta_t, Vacunatorio vacunatorio){
        /*
              Ya posicionado el vacunatorio en la comuna, 
            las personas deberan moverse en la comuna con ciertas condiciones:
            - si la persona aun no esta vacunada, debe dirigirse directamente al vacunatorio
            - si la persona esta contagiada, no puede entrar al vacunatorio
            - si la persona esta recuperada, esta no podra entrar al vacunatorio ni contagiarse
            - solo una cierta cantidad de personas podran entrar al vacunatorio dependiendo de las vacunas disponibles. 
        
        */
        double velocidad = 2.0;
        if(this.estado==1 || this.estado==2 || (this.estado == 0 && vacunatorio.getcapacidad()==vacunatorio.getVacunas())){
            double angulo_aleatorio = Math.random()*((angle + deltaAngle) - (angle - deltaAngle) + 1) + (angle - deltaAngle);
            x_tPlusDelta=x+Math.round(speed*Math.cos(angulo_aleatorio));
            x_tPlusDelta=x_recursivo(x_tPlusDelta,vacunatorio.getminWidth(),vacunatorio.getWidth());
            y_tPlusDelta=y+Math.round(speed*Math.sin(angulo_aleatorio));
            y_tPlusDelta=y_recursivo(y_tPlusDelta,vacunatorio.getminHeight(),vacunatorio.getHeight());
        }else if(this.estado == 0 && vacunatorio.getcapacidad()!=vacunatorio.getVacunas()){
            if(x>vacunatorio.getminWidth() && x<vacunatorio.getWidth()){
                x_tPlusDelta = x;
                if(y>vacunatorio.getminHeight() && y<vacunatorio.getHeight()){
                    if(this.df == 0){
                        this.df =1;
                        vacunatorio.addpersona_capacidad();
                    }
                    y_tPlusDelta = y;
                    this.time_rec+=delta_t;
                    //System.out.println("a: " + this.time_rec);
                    //System.out.println("b: " + vacunatorio.getTime_rec());
                    if(this.time_rec == vacunatorio.getTime_rec()){
                        this.estado = 2;
                    }    
                }else{
                    if(Math.abs(y - vacunatorio.getminHeight()) > Math.abs(y - vacunatorio.getHeight())){
                        y_tPlusDelta = y - velocidad;
                    }else{
                        y_tPlusDelta = y + velocidad;
                    }
                }
            }else{
                if(Math.abs(x - vacunatorio.getminWidth()) > Math.abs(x - vacunatorio.getWidth())){
                    x_tPlusDelta = x - velocidad;
                }else{
                    x_tPlusDelta = x + velocidad;
                }
                if(y>vacunatorio.getminHeight() && y<vacunatorio.getHeight()){
                    y_tPlusDelta = y;
                }else{
                    if(Math.abs(y - vacunatorio.getminHeight()) > Math.abs(y - vacunatorio.getHeight())){
                        y_tPlusDelta = y - velocidad;
                    }else{
                        y_tPlusDelta = y + velocidad;
                    }
                }
            }
        }

        ///////////////////////////////////////////////////////////////////////
        if(x_tPlusDelta < 0){   // rebound logic
            x_tPlusDelta=0;
            this.angle = Math.random()*2*Math.PI;
        }
        else if( x_tPlusDelta > this.comuna.getWidth()){
            x_tPlusDelta=this.comuna.getWidth();
            this.angle = Math.random()*2*Math.PI;
        }
        if(y_tPlusDelta < 0){   // rebound logic
            y_tPlusDelta=0;
            this.angle = Math.random()*2*Math.PI;
        }
        else if( y_tPlusDelta > this.comuna.getHeight()){
            y_tPlusDelta=this.comuna.getHeight();
            this.angle = Math.random()*2*Math.PI;
        }
        
    }
}
