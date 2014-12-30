#ifndef JCOMMAND_CPP
#define JCOMMAND_CPP

#include "JCommand.h"

JCommand::JCommand()
{
    reset();
}


JCommand::JCommand(unsigned int newCommand, unsigned int newPin, unsigned int newValue, unsigned int newOption)
{
    reset();
    command = newCommand;
    pin = newPin;
    value = newValue;
    option = newOption;
}



void JCommand::reset()
{
    header[0] = 'J';
    header[1] = 'C';
    header[2] = 'O';
    header[3] = 'M';
    version = 0;
    commandType = COMMAND_TYPE_REGULAR;
    commandSize = 16;
    
    //Default Values
    command = pin = value = option = 0;
}

#endif