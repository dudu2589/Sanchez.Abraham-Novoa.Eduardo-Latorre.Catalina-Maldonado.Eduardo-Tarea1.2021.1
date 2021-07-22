#ifndef STAGE2_H
#define STAGE2_H

#include <QMainWindow>

QT_BEGIN_NAMESPACE
namespace Ui { class stage2; }
QT_END_NAMESPACE

class stage2 : public QMainWindow
{
    Q_OBJECT

public:
    stage2(QWidget *parent = nullptr);
    ~stage2();

private:
    Ui::stage2 *ui;
};
#endif // STAGE2_H
