#include "Comuna.h"
#include "Simulator.h"
#include "Pedestrian.h"
#include <QCoreApplication>
#include <iostream>
#include <fstream>

using namespace std;
int main(int argc, char *argv[])
{
    QCoreApplication a(argc, argv);
    if (argc != 2){
        cout << "Usage: stage3 <configurationFile.txt>" << endl;
        exit(-1);
    }
    ifstream fin(argv[1]);
    double N, I, I_time, comunaWidth,comunaLength,speed, delta_t,
            deltaAngle, d , M , p0, p1, p2, numVac, VacSize, VacTime;
    // this is not really needed, just to check data read.
    //cout << "File: " << argv[1] << endl;
    fin >> N >> I >> I_time;
    fin >> comunaWidth >> comunaLength;
    fin >> speed >> delta_t >> deltaAngle;
    fin >> d >> M >> p0 >> p1 >> p2 ;
    fin >> numVac >> VacSize >> VacTime;
    cout << N << "," << I << "," << I_time << endl;
    cout << comunaWidth <<"," << comunaLength << endl;
    cout << speed <<","<< delta_t <<"," << deltaAngle<< endl;
    cout << d <<","<< M <<"," << p0 <<"," << p1 << ","<< p2 <<  endl;
    cout << numVac << "," <<  VacSize << "," << VacTime << endl;
    double samplingTime = 1.0;
    Comuna comuna(comunaWidth, comunaLength);
    comuna.probabilidad(p0,p1,p2);
    for(int i = 0; i<N; i++){
        Pedestrian person(comuna, speed, deltaAngle);
        comuna.setPersons(person);
    }
    comuna.setVector();
    comuna.infectados_random(I);
    comuna.Mascarilla_random(M);
    Simulator sim(cout, comuna, delta_t, samplingTime , 3 ,d); // 3 = stage
    sim.startSimulation();
    return a.exec();
}
