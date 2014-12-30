#ifndef JSPEEDCOMMAND_CPP
#define JSPEEDCOMMAND_CPP

#include <Arduino.h>
#include "BaseCommand.h"
#include "JSpeedCommand.h"

JSpeedCommand::JSpeedCommand()
{
    type = 6;   //JScan == 6
    leftSpeed = 0;
    rightSpeed = 0;
}


String JSpeedCommand::toString()
{
    String result = BaseCommand::toString();
    
    result.concat(" ");
    result.concat(leftSpeed);
    result.concat(", ");
    result.concat(rightSpeed);
        
    return result;
}


void JSpeedCommand::fromString(const char* buffer)
{
    String fullString(buffer);
    
    int index = fullString.indexOf(',');
    if(index > 0)
    {
        String lspeed = fullString.substring(0, index);
        String rspeed = fullString.substring(index+1);
        
        lspeed.trim();
        rspeed.trim();
        
        leftSpeed = stringToInt(lspeed);
        rightSpeed = stringToInt(rspeed);
    }
    else
    {
        //Don't have a valid string, just set to 0
        leftSpeed = rightSpeed = 0;
    }
    
    
}


void JSpeedCommand::fromBuffer(char* buffer, unsigned int bufferSize)
{
    //not implemented
}



void JSpeedCommand::process(JModel* model)
{
    //Set the speed
    model->wheelController.setSpeed(leftSpeed, rightSpeed);
}

#endif