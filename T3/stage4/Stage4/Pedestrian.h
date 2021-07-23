#ifndef PEDESTRIAN_H
#define PEDESTRIAN_H
#include <string>
#include <QRandomGenerator>
using namespace std;
class Comuna;
class Pedestrian {
private:
    double x, y, speed, angle, deltaAngle;
    double x_tPlusDelta, y_tPlusDelta;
    Comuna * comuna;
    QRandomGenerator myRand; // see https://doc.qt.io/qt-5/qrandomgenerator.html
    double porcentaje_infectado , df , time_rec;
    int estado; //(0=suseptible a infectarse / 1=infectado / 2=recuperado / 3=vacunado)
    bool mascarilla; //(true = usa mascarilla / false = no usa mascarilla)
    vector <vector<double> > distancia_entre_personas;

public:
    Pedestrian(Comuna &com, double speed, double deltaAngle);
    static string getStateDescription() {
        return "x, \ty";
    };
    string getState() const;
    void computeNextState(double delta_t);
    void updateState();
    //////////////////////
    int getEstado();
    void setEstado(int newEstado);
    bool getMascarilla();
    void setMascarilla(bool Mascarilla);
    double getPorcentaje_infectado();
    void setPorcentaje_infectado(double i);
    void vectorAppend(vector<double> vectorsito);
    double promedio(vector<double> vector);
    void vector_in_vectorAppend(double distancia , int i);
    void vector_in_vectorClear(int i);
    void interaccionINdividuos(Pedestrian individuo_cercano , int i);
    void probabilidad_de_infeccion(Pedestrian individuo_cercano, double distancia_infeccion, int i);
    double getdf();

};

#endif // PEDESTRIAN_H
