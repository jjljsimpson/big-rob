#ifndef BASECOMMAND_CPP
#define BASECOMAND_CPP

#include "Arduino.h"
#include "JPing.h"

JScan::JScan()
{
    isAscii = false;
    type = 2;  //default command, which doesn't do anything
    commandSize = 0;
    sensorIndex = -1;
}


String JPing::toString()
{
    String result = BAseCommand::toString();
    
    result.concat(" ");
    result.concat(scanType);
    
    return result;
}


void JPing::fromString(const char* buffer)
{
    String fullStr(buffer);
    
    //trim spaces
    fullStr.trim();

    //convert to int
    scanType = stringToInt(fullStr);
    
    //Clamp between 0 - 2
    if(scanType < 0)
        sensorIndex = 0;
    else if(sensorIndex > 2)
        sensorIndex = 2;
}


void JPing::fromBuffer(char* buffer, unsigned int bufferSize)
{
    //not implemented
}



#endif