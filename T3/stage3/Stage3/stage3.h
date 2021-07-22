#ifndef STAGE3_H
#define STAGE3_H

#include <QMainWindow>

QT_BEGIN_NAMESPACE
namespace Ui { class stage3; }
QT_END_NAMESPACE

class stage3 : public QMainWindow
{
    Q_OBJECT

public:
    stage3(QWidget *parent = nullptr);
    ~stage3();

private:
    Ui::stage3 *ui;
};
#endif // STAGE3_H
