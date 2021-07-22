#include <QtMath> // for M_PI and functions, see https://doc.qt.io/qt-5/qtmath.html
#include <string>
#include "Comuna.h"
#include "Pedestrian.h"
#include <iostream>
#include <math.h>
using namespace std;

Pedestrian::Pedestrian (Comuna &com, double speed, double deltaAngle){
    porcentaje_infectado = 0;
    df = 0;
    time_rec = 0;
    estado = 0; //la gente por lo general esta sano
    mascarilla = false ; //la gente no nace con mascarilla

    myRand = QRandomGenerator::securelySeeded();
    this->speed = speed*(0.9+0.2*myRand.generateDouble());
    this->comuna = &com;
    this->deltaAngle = deltaAngle;
    this->angle = (2*M_PI)*myRand.generateDouble();
    this->x = comuna->getWidth()*myRand.generateDouble();
    this->y = comuna->getHeight()*myRand.generateDouble();
}
string Pedestrian::getState() const {
    string s=to_string(x) + ",\t";
    s+= to_string(y);
    return s;
}
void Pedestrian::computeNextState(double delta_t) {
    double r = myRand.generateDouble();
    //angle += deltaAngle*(1-2*r);
    x_tPlusDelta = x+speed*qCos(angle)*delta_t;
    y_tPlusDelta = y+speed*qSin(angle)*delta_t;

    if(x_tPlusDelta <= 0){   // rebound logic
        x_tPlusDelta=0;
        //this->angle = myRand.generateDouble()*2*M_PI;
        //angle+=M_PI/2;
        angle += deltaAngle*(1-2*r);
    }else if( x_tPlusDelta >= this->comuna->getWidth()){
        x_tPlusDelta=this->comuna->getWidth();
        //this->angle = myRand.generateDouble()*2*M_PI;
        //angle-=M_PI/2;
        angle += deltaAngle*(1-2*r);
    }
    if(y_tPlusDelta <= 0){   // rebound logic
        y_tPlusDelta=0;
        //this->angle = myRand.generateDouble()*2*M_PI;
        //angle+=M_PI/2;
        angle += deltaAngle*(1-2*r);
    }else if( y_tPlusDelta >= this->comuna->getHeight()){
        y_tPlusDelta=this->comuna->getHeight();
        //this->angle = myRand.generateDouble()*2*M_PI;
        //angle-=M_PI/2;
        angle += deltaAngle*(1-2*r);
    }

}
void Pedestrian::updateState(){
    x=x_tPlusDelta;
    y=y_tPlusDelta;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////
int Pedestrian::getEstado(){//recibir estado de salud del individuo
    return this->estado;
}
void Pedestrian::setEstado(int newEstado){//cambiar estado de salud del individuo
    this->estado=newEstado;
}
bool Pedestrian::getMascarilla(){//recibir si el individuo  tiene o no tiene mascarilla
    return this->mascarilla;
}
void Pedestrian::setMascarilla(bool Mascarilla){//poner o sacar mascarilla a individuo
    this->mascarilla=Mascarilla;
}
double Pedestrian::getPorcentaje_infectado(){//recibir porcentaje de infeccion de un individuo
    return this->porcentaje_infectado;
}
void Pedestrian::setPorcentaje_infectado(double i){//añadir porcentaje de infeccion a un individuo
   this->porcentaje_infectado+=i;
}

//////////////////////////////////////////////////////////////////////////////////////////////////7
double Pedestrian::promedio(vector<double> vectorsito){
        /*calcular promedio de la distancia en la que se encuentra
        dos individuos en un periodo de tiempo de 1 segundo
        */
        double suma=0;
        for(int i=0;i<(int)vectorsito.size();i++){
            suma+=vectorsito[i];
        }
        return suma/(int)vectorsito.size();
    }
void Pedestrian::vectorAppend(vector<double> lista){
        /*
        A cada persona en su lista se le agrega un bloque donde iran
        las distancias entre un individuo y la gente a su alrededor
        */
        distancia_entre_personas.push_back(lista);
    }
void Pedestrian::vector_in_vectorAppend(double distancia, int i){
        /*
        cada bloque de distancia tiene distancias que se toman a travez de
        el intervalo de tiempo delta_t
        */
        distancia_entre_personas[i].push_back(distancia);
    }
void Pedestrian::vector_in_vectorClear(int i){//limpiar distancias evaluadas en 1 segundo para evaluarlas en un segundo proximo
        for(int j=0;j<(int)distancia_entre_personas[i].size();j++){
            distancia_entre_personas[i].pop_back();
        };
     }
void Pedestrian::interaccionINdividuos(Pedestrian individuo_cercano, int i){
        /*
        Calcula la distancia en la que se encuentran 2 personas
        */
        double distancia;
        distancia = pow((pow((this->x - individuo_cercano.x),2)+pow((this->y - individuo_cercano.y),2)),(0.5));
        this->distancia_entre_personas[i].push_back(distancia);
        individuo_cercano.vector_in_vectorAppend(distancia, i);
    }

void Pedestrian::probabilidad_de_infeccion(Pedestrian individuo_cercano, double distancia_infeccion, int i){
        /*
        entre 2 individuos , sabiendo su promedio de distancias tomadas de los delta_t en 1 segundo
        , el uso de mascarillas entre ambos individuos y el estado de cada uno,
        y sabiedo la distancia maxima a la que pueden estar 2 individuos , se calcula la probabilidad
        de infeccion
        */
        double distancia=0;
        distancia = promedio(distancia_entre_personas[i]);
        if(distancia<=distancia_infeccion){
            if(this->estado==1 && individuo_cercano.estado==0 ){
                individuo_cercano.setPorcentaje_infectado(comuna->prob_infeccion(this->mascarilla,individuo_cercano));
                if(individuo_cercano.getPorcentaje_infectado()>=1)
                    individuo_cercano.setEstado(1);
            }else if(this->estado==0 && individuo_cercano.estado==1 ){
                porcentaje_infectado+=comuna->prob_infeccion(this->mascarilla,individuo_cercano);
                if(this->porcentaje_infectado>=1)
                    this->estado=1;
            }
        }
}

double Pedestrian::getdf(){
    return this->df;
}
