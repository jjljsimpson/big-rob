#ifndef JCOMMAND_H
#define JCOMMAND_H

#include <Arduino.h>
#include "JPinType.h"
#include "JCommandType.h"

/* *********************** JCommand *************************
This is a command that we pass to the device. It tells Arduino
 to perform a specific action

****************************************************** */






class JCommand
{
protected:
    void            reset();
    
public:
    char            header[4];      //Identifier for this command 'JCOM'. It allows us to add more commands with different versions
    byte            version;        //Version for this command (0)
    byte            commandType;    //If regular command or test command
    unsigned int    commandSize;    //size of this structure. Right now fixed, but different versions might be different sizes
    unsigned int    command;        //The action we are going to follow
    unsigned int    pin;            //Pin we are performing the action on
    unsigned int    value;          //The value of the action (depends on the command)
    unsigned int    option;         //depends on the command

    //Size of this command structure is 16 bytes
    
    JCommand();
    
    JCommand(unsigned int newCommand, unsigned int newPin, unsigned int newValue, unsigned int newOption);
};



#endif