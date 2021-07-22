/********************************************************************************
** Form generated from reading UI file 'stage3.ui'
**
** Created by: Qt User Interface Compiler version 6.1.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_STAGE3_H
#define UI_STAGE3_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_stage3
{
public:
    QWidget *centralwidget;
    QMenuBar *menubar;
    QStatusBar *statusbar;

    void setupUi(QMainWindow *stage3)
    {
        if (stage3->objectName().isEmpty())
            stage3->setObjectName(QString::fromUtf8("stage3"));
        stage3->resize(800, 600);
        centralwidget = new QWidget(stage3);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        stage3->setCentralWidget(centralwidget);
        menubar = new QMenuBar(stage3);
        menubar->setObjectName(QString::fromUtf8("menubar"));
        stage3->setMenuBar(menubar);
        statusbar = new QStatusBar(stage3);
        statusbar->setObjectName(QString::fromUtf8("statusbar"));
        stage3->setStatusBar(statusbar);

        retranslateUi(stage3);

        QMetaObject::connectSlotsByName(stage3);
    } // setupUi

    void retranslateUi(QMainWindow *stage3)
    {
        stage3->setWindowTitle(QCoreApplication::translate("stage3", "stage3", nullptr));
    } // retranslateUi

};

namespace Ui {
    class stage3: public Ui_stage3 {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_STAGE3_H
