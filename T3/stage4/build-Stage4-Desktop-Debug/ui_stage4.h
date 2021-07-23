/********************************************************************************
** Form generated from reading UI file 'stage4.ui'
**
** Created by: Qt User Interface Compiler version 6.1.2
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_STAGE4_H
#define UI_STAGE4_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_stage4
{
public:
    QWidget *centralwidget;
    QMenuBar *menubar;
    QStatusBar *statusbar;

    void setupUi(QMainWindow *stage4)
    {
        if (stage4->objectName().isEmpty())
            stage4->setObjectName(QString::fromUtf8("stage4"));
        stage4->resize(800, 600);
        centralwidget = new QWidget(stage4);
        centralwidget->setObjectName(QString::fromUtf8("centralwidget"));
        stage4->setCentralWidget(centralwidget);
        menubar = new QMenuBar(stage4);
        menubar->setObjectName(QString::fromUtf8("menubar"));
        stage4->setMenuBar(menubar);
        statusbar = new QStatusBar(stage4);
        statusbar->setObjectName(QString::fromUtf8("statusbar"));
        stage4->setStatusBar(statusbar);

        retranslateUi(stage4);

        QMetaObject::connectSlotsByName(stage4);
    } // setupUi

    void retranslateUi(QMainWindow *stage4)
    {
        stage4->setWindowTitle(QCoreApplication::translate("stage4", "stage4", nullptr));
    } // retranslateUi

};

namespace Ui {
    class stage4: public Ui_stage4 {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_STAGE4_H
