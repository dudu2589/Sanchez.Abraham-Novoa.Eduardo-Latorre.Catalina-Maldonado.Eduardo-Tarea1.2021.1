#include "Comuna.h"
#include <math.h>
#include <iostream>

Comuna::Comuna(double width, double length): territory(0,0,width,length){
    pPerson=NULL;
}
double Comuna::getWidth() const {
    return this->territory.width();
}
double Comuna::getHeight() const {
    return this->territory.height();
}
void Comuna::setPerson(Pedestrian & person){
   this->pPerson = &person;
}
void Comuna::computeNextState (double delta_t) {
   pPerson->computeNextState(delta_t);
}
void Comuna::updateState () {
   pPerson->updateState();
}
string Comuna::getStateDescription() {
   return Pedestrian::getStateDescription();
}
string Comuna::getState() const{
    return pPerson->getState();
}

/*           STAGE 2         */
void Comuna::setPersons(Pedestrian &person){
    pPersons.push_back(person);
}
void Comuna::computeNextStates(double delta_t){
    for(auto i = pPersons.begin(); i != pPersons.end(); i++){
        i->computeNextState(delta_t);
    }
}
void Comuna::updateStates(){
    for(auto i = pPersons.begin(); i != pPersons.end(); i++){
        i->updateState();
    }
}

void Comuna::clear(){//eliminar distancias usadas en 1 segundo
    for(int i=0;i < (int)pPersons.size();i++){
        for(int j=0;j<(int)(pPersons.size()-1);j++){
            pPersons[i].vector_in_vectorClear(j);        }
    }
}
void Comuna::setVector(){
/* cada individuo tiene una lista con las posiciones de cada persona con la que se crusa, por lo que
    se crean sublistas dentro de una lista de individuo , registradas las distancia entre una persona
    y las que lo rodean .
*/
    for(int i=0;i<(int)pPersons.size();i++)
        for(int j=1;j<(int)pPersons.size();j++){
            vector<double> distancia_entre_persona;
            pPersons[i].vectorAppend(distancia_entre_persona);
        }
}
void Comuna::probabilidad(double p0, double p1, double p2){
    /*asigna una probabilidad de contagio correspondiente a si :
    p0 = ninuno usa mascarilla
    p1 = al menos uno usa mascarilla
    p2 = ambos usan mascarilla
    */
    this->p0=p0;
    this->p1=p1;
    this->p2=p2;
}
double Comuna::getVectorSize(){// recibir largo de la lista
    return this->pPersons.size();
}
void Comuna::infectados_random(double infectado){//infecta aleatoreamente a una cantidad de n personas
    for(int i=0;i<infectado;i++){
        bool verificador = true;
        while(verificador==true){
            int index = (int) ( myRand.generateDouble() * pPersons.size());
            if(this->pPersons[index].getEstado()!=1){
                this->pPersons[index].setEstado(1);
                verificador=false;
            }
        }
    }
    this->inf=infectado;
    this->sus=this->pPersons.size() - infectado;
}
void Comuna::distancia_entre_individuos(){
    /*para todos los individuos de la comuna se calcula la distancia que tiene
    cada uno con las demas personas de la comuna .
    */
    for(int i=0;i < (int)pPersons.size();i++){
        for(int j=(i+1);j<(int)(pPersons.size());j++){
            pPersons[i].interaccionINdividuos(pPersons[j],i);
        }
    }
}
void Comuna::probabilidad_de_infeccion(double distancia_infeccion){
    /*
    dependiendo de la distancia a que se encuentren , se calculara
    las probabildades de que un individuo se contagie
    */
    for(int i=0;i < (int)pPersons.size();i++){
        for(int j=(i+1);j<(int)(pPersons.size());j++){
            if(pPersons[i].getdf()==0)
            pPersons[i].probabilidad_de_infeccion(pPersons[j],distancia_infeccion,i);
        }
    }
}
double Comuna::prob_infeccion(bool a, Pedestrian b){
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
void Comuna::update_estado_gente(){
    /*
    actualizacion de los infectados, los suseptibles a infectarse y los recuperados
    */
    this->inf=0;
    this->rec=0;
    this->sus=0;
    for(int i=0;i<(int)pPersons.size();i++){
        if(pPersons[i].getEstado()==0){
            this->sus+=1;
        }else if(pPersons[i].getEstado()==1){
            this->inf+=1;
        }else if(pPersons[i].getEstado()==2){
            this->rec+=1;
        }
    }
}
void Comuna::estado_inicial_final(){
    cout << "| infectadas: "<<this->inf<<" | suseptibles a infectarse: "<<this->sus<<" | Recuperados: "<<this->rec << endl;
}
void Comuna::Mascarilla_random(double cant_gente_mascarilla){
    /*
    Dependiendo de un cierto porcentaje de "gente que usa mascarilla",
    se le ponen mascarillas de manera aleatoria a una cierta cantidad de gente
    entre ellos los infectados y aparte los suseptibles a infectarse
    */
    for(int i=0;i<(inf*cant_gente_mascarilla);i++){
        bool verificador = true;
        while(verificador==true){
            int index = (int) ( myRand.generateDouble() * pPersons.size());
            if(this->pPersons[index].getMascarilla()==false && this->pPersons[index].getEstado()==1 ){
                this->pPersons[index].setMascarilla(true);
                verificador=false;
            }
        }
    }
    for(int i=0;i<(sus*cant_gente_mascarilla);i++){
        bool verificador = true;
        while(verificador==true){
            int index = (int) ( myRand.generateDouble() * pPersons.size());
            if(this->pPersons[index].getMascarilla()==false && this->pPersons[index].getEstado()==0){
                this->pPersons[index].setMascarilla(true);
                verificador=false;
            }
        }
    }
}
