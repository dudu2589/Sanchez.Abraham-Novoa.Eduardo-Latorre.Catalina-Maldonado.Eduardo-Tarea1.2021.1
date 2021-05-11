import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

//import jdk.javadoc.internal.doclets.toolkit.resources.doclets;


public class Comuna {
    private ArrayList<Individuo> personas = new ArrayList<Individuo>(); // lista que contendra a los individuos de la comuna
    private Rectangle2D territory; // Alternatively: double width, length;
    private double p0 , p1 , p2;// 0 ninguo usa mascarilla , 1 solo uno usa mascarilla , 2 ambos usan mascarilla
    private double inf , rec , sus;//infectados , recuperados , suseptibles a infectarse

    public Comuna(){ //constructor de la comuna 
        territory = new Rectangle2D.Double(0, 0, 1000, 1000); // 1000x1000 m²;
    }
    public Comuna(double width, double length){ //asignar ancho y largo a la comuna
        territory = new Rectangle2D.Double(0,0, width, length);
    }
    public double getWidth() { //recibir ancho de comuna
        return this.territory.getWidth();
    }
    public double getHeight() {// recibir largo de comuna
        return this.territory.getHeight();
    }
    public int getEstadoPerson(int i){ // recibir estado de salud de la persona 
        return this.personas.get(i).getEstado();
    }
    public void setPerson(Individuo person){// ubicar persona en comuna
        personas.add(person);
    }
    public void computeNextState (double delta_t) {// calcular la siguiente ubicacion de la persona en la comuna
        for(int i=0;i<personas.size();i++)
            personas.get(i).computeNextState(delta_t);
    }
    public void updateState () { // Actualizar la posicion del individuo ; (x,y) -> (x_tplusdelta,y_tplusdelta)
        for(int i=0;i<personas.size();i++)
            personas.get(i).updateState();
    }
    // include others methods as necessary
    /*
    */

    public void clear(){//eliminar distancias usadas en 1 segundo
        for(int i=0;i<personas.size();i++){
            for(int j=0;j<(personas.size()-1);j++){
                personas.get(i).list_in_listClear(j);
            }
        }
    }
    public void setlist(){
    /* cada individuo tiene una lista con las posiciones de cada persona con la que se crusa, por lo que
        se crean sublistas dentro de una lista de individuo , registradas las distancia entre una persona
        y las que lo rodean .      
    */
        for(int i=0;i<personas.size();i++)
            for(int j=1;j<personas.size();j++){
                ArrayList<Double> distancia_entre_persona = new ArrayList<Double>();
                personas.get(i).listAppend(distancia_entre_persona);
            }
    }
    public void probabilidad(double p0, double p1, double p2){
        /*asigna una probabilidad de contagio correspondiente a si :
        p0 = ninuno usa mascarilla
        p1 = al menos uno usa mascarilla
        p2 = ambos usan mascarilla        
        */
        this.p0=p0;
        this.p1=p1;
        this.p2=p2;
    }
    public double getListSize(){// recibir largo de la lista
        return this.personas.size();
    }
    public String getState(int i){// recibir posicion actual de la persona
        return this.personas.get(i).getState();
    }
    public void Infectados_random(double infectado){//infecta aleatoreamente a una cantidad de n personas
        for(int i=0;i<infectado;i++){
            boolean verificador = true;
            while(verificador==true){
                int index = (int) (Math.random() * personas.size());
                if(this.personas.get(index).getEstado()!=1){
                    this.personas.get(index).setEstado(1);
                    verificador=false;
                }
            }
        }
        this.inf=infectado;
        this.sus=this.personas.size() - infectado;
    }
    public void distancia_entre_individuos(){
        /*para todos los individuos de la comuna se calcula la distancia que tiene
        cada uno con las demas personas de la comuna .
        */
        for(int i=0;i<personas.size();i++){
            for(int j=(i+1);j<personas.size();j++){
                personas.get(i).interaccionIndividuos(personas.get(j),i);
            }
        }
    }
    public void probabilidad_de_infeccion(double distancia_infeccion){
        /*
        dependiendo de la distancia a que se encuentren , se calculara 
        las probabildades de que un individuo se contagie
        */
        for(int i=0;i<personas.size();i++){
            for(int j=(i+1);j<personas.size();j++){
                personas.get(i).probabilidad_de_infeccion(personas.get(j),distancia_infeccion,i);
            }
        }
    }
    public double prob_infeccion(Boolean a, Individuo b){
        /*
        Probabilidad de contagio dependiendo de el uso de mascarilla
        */
        if(a==false && b.getMascarilla()==false){
            return p0;
        }else if(a==true && b.getMascarilla()==false){
            return p1;
        }else if(a==false && b.getMascarilla()==true){
            return p1;
        }else{
            return p2;
        }
    }
    public void update_estado_gente(){
        /*
        actualizacion de los infectados, los suseptibles a infectarse y los recuperados
        */
        this.inf=0;
        this.rec=0;
        this.sus=0;
        for(int i=0;i<personas.size();i++){
            if(personas.get(i).getEstado()==0){
                this.sus+=1;
            }else if(personas.get(i).getEstado()==1){
                this.inf+=1;
            }else if(personas.get(i).getEstado()==2){
                this.rec+=1;
            }
        }
    }
    public void estado_inicial_final(){
        System.out.println("| infectadas: "+this.inf+" | suseptibles a infectarse: "+this.sus+" | Recuperados: "+this.rec+"\n");
    }
    public void Mascarilla_random(double cant_gente_mascarilla){
        /*
        Dependiendo de un cierto porcentaje de "gente que usa mascarilla", 
        se le ponen mascarillas de manera aleatoria a una cierta cantidad de gente
        entre ellos los infectados y aparte los suseptibles a infectarse 
        */
        for(int i=0;i<(inf*cant_gente_mascarilla);i++){
            boolean verificador = true;
            while(verificador==true){
                int index = (int) (Math.random() * personas.size());
                if(this.personas.get(index).getMascarilla()==false && this.personas.get(index).getEstado()==1 ){
                    this.personas.get(index).setMascarilla(true);
                    verificador=false;
                }
            }
        }
        for(int i=0;i<(sus*cant_gente_mascarilla);i++){
            boolean verificador = true;
            while(verificador==true){
                int index = (int) (Math.random() * personas.size());
                if(this.personas.get(index).getMascarilla()==false && this.personas.get(index).getEstado()==0){
                    this.personas.get(index).setMascarilla(true);
                    verificador=false;
                }
            }
        }
    }
    ///////////////////////////////////////77
    //Vacunatorio (Stage 4)
}

