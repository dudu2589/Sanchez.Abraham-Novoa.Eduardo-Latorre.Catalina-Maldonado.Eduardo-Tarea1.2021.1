/********************************************************************************
** Form generated from reading UI file 'stage2.ui'
**
** Created by: Qt User Interface Compiler version 6.1.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_STAGE2_H
#define UI_STAGE2_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_stage2
{
public:
    QWidget *centralwidget;
    QMenuBar *menubar;
    QStatusBar *statusbar;

    void setupUi(QMainWindow *stage2)
    {
        if (stage2->objectName().isEmpty())
            stage2->setObjectName(QString::fromUtf8("stage2"));
        stage2->resize(800, 600);
        centralwidget = new QWidget(stage2);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        stage2->setCentralWidget(centralwidget);
        menubar = new QMenuBar(stage2);
        menubar->setObjectName(QString::fromUtf8("menubar"));
        stage2->setMenuBar(menubar);
        statusbar = new QStatusBar(stage2);
        statusbar->setObjectName(QString::fromUtf8("statusbar"));
        stage2->setStatusBar(statusbar);

        retranslateUi(stage2);

        QMetaObject::connectSlotsByName(stage2);
    } // setupUi

    void retranslateUi(QMainWindow *stage2)
    {
        stage2->setWindowTitle(QCoreApplication::translate("stage2", "stage2", nullptr));
    } // retranslateUi

};

namespace Ui {
    class stage2: public Ui_stage2 {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_STAGE2_H
