#include "stage3.h"

#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    stage3 w;
    w.show();
    return a.exec();
}
