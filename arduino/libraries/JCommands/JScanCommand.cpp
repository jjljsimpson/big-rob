#ifndef JSCANCOMMAND_CPP
#define JSCANCOMMAND_CPP

#include <Arduino.h>
#include "BaseCommand.h"
#include "JScanCommand.h"

JScanCommand::JScanCommand()
{
    type = 3;   //JScan == 3
    scanType = 0;
}


String JScanCommand::toString()
{
    String result = BaseCommand::toString();
    
    result.concat(" ");
    result.concat(scanType);
        
    return result;
}


void JScanCommand::fromString(const char* buffer)
{
    //convert char->String->int
    String scanString(buffer);
    scanString.trim();
    scanType = stringToInt(scanString);
    
    //Validate value
    if(scanType < 0 || scanType > 2)
    {
        scanType = 0; //if number is invalid, turn reset
    }    
}


void JScanCommand::fromBuffer(char* buffer, unsigned int bufferSize)
{
    //not implemented
}



void JScanCommand::process(JModel* model)
{
    if(scanType == 0)
    {
       //reset the distance sensor
        model->distanceController.reset();
    }
    else if(scanType == 1)
    {
        model->distanceController.setActiveSensor(7);
        model->distanceController.setRotateMode(true);
    }
    else if(scanType == 2)
    {
        model->distanceController.setActiveSensor(1);
        model->distanceController.setRotateMode(true);        
    }
    
    //Do the actual ping later on.
}

#endif