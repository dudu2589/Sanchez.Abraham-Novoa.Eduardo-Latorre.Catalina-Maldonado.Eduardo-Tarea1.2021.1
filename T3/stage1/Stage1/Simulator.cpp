#include "Simulator.h"
Simulator::Simulator(ostream &output, Comuna &com,double delta, double st, int stage, double d_max){ //
    d_maxim = d_max;
    Stage = stage;
    t=0.0;
    comuna = &com;
    out = &output;
    delta_t=delta;
    samplingTime=st;
    timer = new QTimer(this);
    connect(timer, SIGNAL(timeout()),
            this, SLOT(simulateSlot()));
}
void Simulator::printStateDescription() const {
    string s="t,\t" + Comuna::getStateDescription();
    *out << s << endl;
}
void Simulator::printState(double t) const{
    string s = to_string(t) + ",\t";
    s += this->comuna->getState();
    *out << s << endl;
}
void Simulator::startSimulation(){
    printStateDescription();
    t=0.0;
    if(Stage == 1){
        printState(t);
    }else if(Stage == 2){
        comuna->estado_inicial_final();
    }
    timer->start(5);
}
void Simulator::simulateSlot(){
    double nextStop=t+samplingTime;
    if(Stage == 1){
        *out <<"T : "<< t << endl;
        for(t;t<nextStop;t+=delta_t){ //while(t<nextStop){
           comuna->computeNextState(delta_t); // compute its next state based on current global state
           comuna->updateState();  // update its state
        }
        printState(t);
    }else if(Stage == 2 || Stage == 3){
        *out <<"T : "<< t << endl;
        for(t;t<nextStop;t+=delta_t){ //while(t<nextStop){
           comuna->computeNextStates(delta_t); // compute its next state based on current global state
           comuna->updateStates();  // update its state
           comuna->distancia_entre_individuos();
        }
        comuna->probabilidad_de_infeccion(this->d_maxim);
        comuna->clear();
        comuna->estado_inicial_final();
    }
}
