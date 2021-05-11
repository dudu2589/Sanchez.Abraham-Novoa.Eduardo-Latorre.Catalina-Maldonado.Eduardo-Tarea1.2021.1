import java.awt.geom.Rectangle2D;

public class Vacunatorio{
    private Rectangle2D vacunatorio;
    private double vacunas;

    public Vacunatorio(){
        vacunatorio = new Rectangle2D.Double(0,0,1000,1000);
    }
    public Vacunatorio(double width, double length){
        vacunatorio = new Rectangle2D.Double(0,0, width, length);
        this.vacunas=0;
    }
    public double getWidth() {
        return this.vacunatorio.getWidth();
    }
    public double getHeight() {
        return this.vacunatorio.getHeight();
    }
    public void setVacunas(double i){
        this.vacunas=i;
    }
}