#ifndef JLEANCOMMAND_CPP
#define JLEANCOMMAND_CPP

#include <Arduino.h>
#include "BaseCommand.h"
#include "JLeanCommand.h"
#include <JRoam.h>

JLeanCommand::JLeanCommand()
{
    type = 4;   //JScan == 4
    leanType = 0;
}


String JLeanCommand::toString()
{
    String result = BaseCommand::toString();
    
    result.concat(" ");
    result.concat(leanType);
        
    return result;
}


void JLeanCommand::fromString(const char* buffer)
{
    //convert char->String->int
    String scanString(buffer);
    scanString.trim();
    leanType = stringToInt(scanString);
    
    //Validate value
    if(leanType < 0 || leanType > 3)
    {
        leanType = 0; //if number is invalid, turn off
    }    
}


void JLeanCommand::fromBuffer(char* buffer, unsigned int bufferSize)
{
    //not implemented
}



void JLeanCommand::process(JModel* model)
{
    model->roamController.setLeanDirection(leanType);
    
    //Do the actual ping later during roam
}

#endif