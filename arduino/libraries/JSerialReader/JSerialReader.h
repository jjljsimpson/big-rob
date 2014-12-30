#ifndef JSERIALREADER_H
#define JSERIALREADER_H

#include <Arduino.h>
#include <BaseCommand.h>

class JSerialReader
{
protected:
    static const int SERIAL_BUFFER_SIZE = 256;  //size of the buffer
    char buffer[SERIAL_BUFFER_SIZE];
    int bufferPosition;
    char asciiHeader;  //header portion which says if ascii or binary
    char headerNumber[3];
    BaseCommand* currentCommand;
    
    
    //Converts the command type to an integer
    int convertCommandType();
    
public:
    //default constructor
    JSerialReader();
    
    //Reads the serial for a command
    // if a command is avialble, it is returned.
    // you need to clean up the command if it isn't NULL
    BaseCommand* readSerial();
};



#endif