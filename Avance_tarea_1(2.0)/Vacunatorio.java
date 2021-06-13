import java.awt.geom.Rectangle2D;

public class Vacunatorio{
    private Rectangle2D vacunatorio;
    private double vacunas; // vacunas totales
    private double capacidad; // personas en el vacunatorio
    private double time_rec; // tiempo que se demoran en recuperarse

    public Vacunatorio(){ //constructor de vacunatorio
        vacunatorio = new Rectangle2D.Double(0,0,1000,1000);
    }
    public Vacunatorio(double m_width, double m_length,double width, double length, double time_rec){
        vacunatorio = new Rectangle2D.Double(m_width,m_length, width, length);
        this.vacunas=0;
        this.capacidad=0;
        this.time_rec = time_rec; 
    }
    public double getminWidth() { //obtener punto minimo en x del vacunatorio
        return this.vacunatorio.getMinX();
    }
    public double getminHeight() { //obtener punto maximo en x del vacunatori
        return this.vacunatorio.getMinY();
    }
    public double getWidth() { //obtener punto minimo en y del vacunatori
        return this.vacunatorio.getWidth();
    }
    public double getHeight() { //obtener punto maximo en y del vacunatori
        return this.vacunatorio.getHeight();
    }
    public void setVacunas(double i){// entrega de vacunas al vacunatorio
        this.vacunas=i;
    }
    public double getcapacidad(){//entrega informacion de la capacidad del vacunatorio, esta depende de la cantidad de vacunas
        return this.capacidad;
    }
    public double getVacunas(){// entrega la informacion de la cantidad de las vacunas
        return this.vacunas;
    }
    public double getTime_rec(){// entrega la informacion del tiempo de recuperacion de la persona
        return this.time_rec;
    }
    public void addpersona_capacidad(){// cuando una persona llega al vacunatorio , se registra en este mismo. 
        this.capacidad+=1;
    }
}