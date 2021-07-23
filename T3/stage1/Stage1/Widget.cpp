#include "Widget.h"
#include "ui_Widget.h"

Widget::Widget(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::Widget)
{
    ui->setupUi(this);
}

Widget::~Widget()
{
    delete ui;
}

void Widget::on_ejecutarPushButton_clicked()
{
    system (ui->procesoLineEdit->text().toStdString().c_str());
}


void Widget::on_quitarPushButton_clicked()
{
    close();
}

