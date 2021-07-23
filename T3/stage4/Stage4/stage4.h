#ifndef STAGE4_H
#define STAGE4_H

#include <QMainWindow>

QT_BEGIN_NAMESPACE
namespace Ui { class stage4; }
QT_END_NAMESPACE

class stage4 : public QMainWindow
{
    Q_OBJECT

public:
    stage4(QWidget *parent = nullptr);
    ~stage4();

private:
    Ui::stage4 *ui;
};
#endif // STAGE4_H
