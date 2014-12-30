#ifndef JCOMMCOMMAND_CPP
#define JCOMMCOMMAND_CPP

#include <Arduino.h>
#include "BaseCommand.h"
#include "JCommCommand.h"

JCommCommand::JCommCommand()
{
    type = 1;   //JComm == 1
    commType = 0;
}


String JCommCommand::toString()
{
    String result = BaseCommand::toString();
    
    result.concat(" ");
    result.concat(commType);
        
    return result;
}


void JCommCommand::fromString(const char* buffer)
{
    //convert char->String->int
    String comString(buffer);
    comString.trim();
    commType = stringToInt(comString);
    
    //Validate value
    if(commType < 0 || commType > 3)
    {
        commType = 0;
    }    
}


void JCommCommand::fromBuffer(char* buffer, unsigned int bufferSize)
{
    //not implemented
}



void JCommCommand::process(JModel* model)
{
    //Don't do anything for 0
    
    //for 1, reset the state
    if(commType == 1)
    {
        //reset everything
        model->resetState();
    }
    else if(commType == 2)
    {
        //Send back the distance values
        Serial.println(model->wheelController.sensorValues());
        Serial.println(model->distanceController.sensorValues());
    }
    else if(commType == 3)
    {
        //just ping back to let it know we are still here
        Serial.write(0);
    }
    
}

#endif