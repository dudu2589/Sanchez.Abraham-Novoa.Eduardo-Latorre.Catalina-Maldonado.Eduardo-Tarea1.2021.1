import java.awt.geom.Rectangle2D;
import java.util.ArrayList;


public class Comuna {
    private ArrayList<Individuo> personas = new ArrayList<Individuo>(); // solo contiene a 1 persona ¿Como hacer para tener a 2 personas?
    // opcion 1: usar listas
    private Rectangle2D territory; // Alternatively: double width, length;

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
    }
 }
