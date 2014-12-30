#ifndef JMODEL_CPP
#define JMODEL_CPP

#include "JModel.h"


JModel::JModel()
{
    pins = new JPin[NUMBER_OF_PINS];
    currentMode = MODE_SCAN;
    currentTime = lastTime = frameTime = 0;
    currentRead = 0;
    readPosition = 0;
    waitTime = 0;
}

#endif