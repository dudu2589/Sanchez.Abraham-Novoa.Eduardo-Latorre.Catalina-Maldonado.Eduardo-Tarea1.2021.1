#ifndef COMUNA_H
#define COMUNA_H
#include "Pedestrian.h"
#include <QRect>
#include <string>
#include <vector>
#include <QRandomGenerator>

using namespace std;
class Comuna {
private:
    QRandomGenerator myRand;
    vector <Pedestrian> pPersons;
    Pedestrian * pPerson;
    QRect territory; // Alternatively: double width, length;
    double p0 , p1 , p2;
    double inf , rec , sus;
    // but more methods would be needed.

public:
    Comuna();
    Comuna(double width=1000, double length=1000);
    double getWidth() const;
    double getHeight() const;
    void setPerson(Pedestrian &person);
    void setPersons(Pedestrian &person);// stage 2
    void computeNextState (double delta_t);
    void computeNextStates (double delta_t); // stage 2
    void updateState ();
    void updateStates ();//stage 2
    static string getStateDescription();
    string getState() const;
    void probabilidad(double p0, double p1 , double p2); // stage 2 - 4
    void clear(); // stage 2 - 4
    void setVector(); // stage 2 - 4
    double getVectorSize(); // stage 2 - 4
    void infectados_random(double infectado); // stage 2 - 4
    void distancia_entre_individuos(); // stage 2 - 4
    void probabilidad_de_infeccion(double distancia_infeccion);// stage 2 - 4
    double prob_infeccion(bool a, Pedestrian b); // stage 2 - 4
    void update_estado_gente(); // stage 2 - 4
    void estado_inicial_final(); // stage 2 - 4
    void Mascarilla_random(double cant_gente_mascarilla); // stage 2 - 4
 };

#endif // COMUNA_H
