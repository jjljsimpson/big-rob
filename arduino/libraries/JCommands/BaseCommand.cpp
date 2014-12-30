#ifndef BASECOMMAND_CPP
#define BASECOMAND_CPP

#include "BaseCommand.h"
#include <JModel.h>

BaseCommand::BaseCommand()
{
    isAscii = false;
    type = 0;  //default command, which doesn't do anything
    commandSize = 0;
}


String BaseCommand::toString()
{
    String result = "";
    
    //Start with a or b
    if(isAscii)
        result.concat("a");
    else
        result.concat("b");
    
    //type of command
    result.concat(type);
    
    return result;
}


void BaseCommand::fromString(const char* buffer)
{
    //Not implemented
}


void BaseCommand::fromBuffer(char* buffer, unsigned int bufferSize)
{
    //not implemented
}


int BaseCommand::stringToInt(String str)
{
    return str.toInt();
}


void BaseCommand::process(JModel* model)
{
    //not implemented
}

#endif