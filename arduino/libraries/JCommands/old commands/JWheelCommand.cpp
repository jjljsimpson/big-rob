#ifndef JWHEELCOMMAND_CPP
#define JWHEELCOMMAND_CPP

#include "Arduino.h"
#include "JWheelCommand.h"

JWheelCommand::JWheelCommand()
{
    isAscii = false;
    type = 1;   //Command = 1
    commandSize = sizeof(int) * 2;
    
    leftSpeed = rightSpeed = 0;
}


String JWheelCommand::toString()
{
    String result = BaseCommand::toString();
    
    result.concat(" ");
    result.concat(leftSpeed);
    result.concat(",");
    result.concat(rightSpeed);
    
    return result;
}


/*
 String should be in the following format
 " leftSpeed,rightSpeed"
 Need to split on , then trim left string, then convert each to an int
*/
void JWheelCommand::fromString(const char* buffer)
{
    String fullStr(buffer);
    
    //Trim leading spaces
    fullStr.trim();
    
    //Break into two numbers split by ","
    int i = fullStr.indexOf(',');
    if(i >= 0)
    {
        String lspeed = fullStr.substring(0,i);
        String rspeed = fullStr.substring(i);
        
        leftSpeed = stringToInt(lspeed);
        rightSpeed = stringToInt(rspeed);
    }
}


void JWheelCommand::fromBuffer(char* buffer, unsigned int bufferSize)
{
    //not implemented
}



#endif