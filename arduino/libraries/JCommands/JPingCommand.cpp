#ifndef JPINGCOMMAND_CPP
#define JPINGCOMMAND_CPP

#include <Arduino.h>
#include "BaseCommand.h"
#include "JPingCommand.h"

JPingCommand::JPingCommand()
{
    type = 2;   //JPing == 2
    sensor = 0;
}


String JPingCommand::toString()
{
    String result = BaseCommand::toString();
    
    result.concat(" ");
    result.concat(sensor);
        
    return result;
}


void JPingCommand::fromString(const char* buffer)
{
    //convert char->String->int
    String sensorString(buffer);
    sensorString.trim();
    sensor = stringToInt(sensorString);
    
    //Validate value
    if(sensor < 0 || sensor > 7)
    {
        sensor = 0; //if number is invalid, turn it off
    }    
}


void JPingCommand::fromBuffer(char* buffer, unsigned int bufferSize)
{
    //not implemented
}



void JPingCommand::process(JModel* model)
{
    //Set the sensor value and turn rotation off
    model->distanceController.setActiveSensor(sensor);
    model->distanceController.setRotateMode(false);
    //Do the actual ping later on.    
}

#endif