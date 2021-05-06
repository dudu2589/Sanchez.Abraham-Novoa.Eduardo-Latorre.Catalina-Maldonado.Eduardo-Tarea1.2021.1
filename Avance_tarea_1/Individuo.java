public class Individuo {
    private double x, y, speed, angle, deltaAngle;
    private double x_tPlusDelta, y_tPlusDelta;
    private Comuna comuna;
    private int estado; //(0=suseptible a infectarse / 1=infectado / 2=recuperado / 3=vacunado)
    private boolean mascarila; //(true = usa mascarilla / false = no usa mascarilla)
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

        estado = 0; //la gente por lo general esta sano
        mascarila = false ; //la gente no nace con mascarilla
    }
    public Individuo (Comuna comuna, double speed, double deltaAngle){
        this.comuna = comuna;
        this.speed = speed;
        this.deltaAngle = deltaAngle;
        this.x = Math.round(Math.random()*(comuna.getWidth())); 
        this.y = Math.round(Math.random()*(comuna.getHeight()));
        angle = Math.round(Math.random()*2*Math.PI);
    }
    public static String getStateDescription(){
        return "x,\ty";
    }
    public String getState() {
        return x + "\t" + y;
    }
    public void computeNextState(double delta_t) { //computar siguiente movimiento aleatorio
        double r=Math.random();
        this.angle+=delta_t;
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
}
