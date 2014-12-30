#ifndef JPIN_CPP
#define JPIN_CPP

#include "JPin.h"
#include "JCommand.h"

JPin::JPin()
{
    pinId = 0;
    pinType = PIN_TYPE_UNINITIALIZED;
    pinValue = 0;
    lastUpdate = pinUpdate = 0;
}


void JPin::update()
{
    
}


int JPin::getValue()
{
    
}


#endif