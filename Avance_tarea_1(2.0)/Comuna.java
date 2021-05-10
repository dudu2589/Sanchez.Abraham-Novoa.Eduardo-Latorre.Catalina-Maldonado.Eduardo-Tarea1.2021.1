import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

//import jdk.javadoc.internal.doclets.toolkit.resources.doclets;


public class Comuna {
    private ArrayList<Individuo> personas = new ArrayList<Individuo>(); // solo contiene a 1 persona ¿Como hacer para tener a 2 personas?
    // opcion 1: usar listas
    private Rectangle2D territory; // Alternatively: double width, length;
    private double p0 , p1 , p2;// 0 ninguo usa mascarilla , 1 solo uno usa mascarilla , 2 ambos usan mascarilla
    private double inf , rec , sus;//infectados , recuperados , suseptibles a infectarse

    public Comuna(){
        territory = new Rectangle2D.Double(0, 0, 1000, 1000); // 1000x1000 m²;
    }
    public Comuna(double width, double length){
        territory = new Rectangle2D.Double(0,0, width, length);
        //person=null;
    }
    public double getWidth() {
        return this.territory.getWidth();
    }
    public double getHeight() {
        return this.territory.getHeight();
    }
    public int getEstadoPerson(int i){
        return this.personas.get(i).getEstado();
    }
    public void setPerson(Individuo person){
        personas.add(person);
    }
    public void computeNextState (double delta_t) {
        for(int i=0;i<personas.size();i++)
            personas.get(i).computeNextState(delta_t);
    }
    public void updateState () {
        for(int i=0;i<personas.size();i++)
            personas.get(i).updateState();
    }
    // include others methods as necessary
    /*
    public  String getStateDescription () {
        String s = person.getStateDescription();
        return s;
    }
    */
    public void setlist(){
        for(int i=0;i<personas.size();i++)
            for(int j=1;j<personas.size();j++){
                ArrayList<Double> distancia_entre_persona = new ArrayList<Double>();
                personas.get(i).listAppend(distancia_entre_persona);
            }
    }
    public void probabilidad(double p0, double p1, double p2){
        this.p0=p0;
        this.p1=p1;
        this.p2=p2;
    }
    public double getListSize(){
        return this.personas.size();
    }
    public String getState(int i){
        return this.personas.get(i).getState();
    }
    public void Infectados_random(double infectado){
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
        for(int i=0;i<personas.size();i++){
            for(int j=(i+1);j<personas.size();j++){
                personas.get(i).interaccionIndividuos(personas.get(j),i);
            }
        }
    }
    public void probabilidad_de_infeccion(double distancia_infeccion){
        for(int i=0;i<personas.size();i++){
            for(int j=(i+1);j<personas.size();j++){
                personas.get(i).probabilidad_de_infeccion(personas.get(j),distancia_infeccion,i);
            }
        }
    }
    public double prob_infeccion(Boolean a, Individuo b){
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
        System.out.println("| infectadas: "+this.inf+" | suseptibles a infectarse: "+this.sus+" | Recuperados: "+this.rec+"\n");
    }
    public void Mascarilla_random(double cant_gente_mascarilla){
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

