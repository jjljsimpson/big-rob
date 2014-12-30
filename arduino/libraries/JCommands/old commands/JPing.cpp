#ifndef JPING_H
#define JPING_H

#include "Arduino.h"
#include "JPing.h"

JPing::JPing()
{
    isAscii = false;
    type = 1;  //default command, which doesn't do anything
    commandSize = 0;
    sensorIndex = -1;
}


String JPing::toString()
{
    String result = BAseCommand::toString();
    
    result.concat(" ");
    result.concat(sensorIndex);
    
    return result;
}


void JPing::fromString(const char* buffer)
{
    String fullStr(buffer);
    
    //trim spaces
    fullStr.trim();

    //convert to int
    sensorIndex = stringToInt(fullStr);
    
    //Clamp between -1 - 2
    if(sensorIndex < -1)
        sensorIndex = -1;
    else if(sensorIndex > 2)
        sensorIndex = 2;
}


void JPing::fromBuffer(char* buffer, unsigned int bufferSize)
{
    //not implemented
}



#endif