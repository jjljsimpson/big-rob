#ifndef JPINGCOMMAND_H
#define JPINGCOMMAND_H

#include <Arduino.h>
#include "BaseCommand.h"
#include <JModel.h>

/* *********************** JComm ************************* */
/* Every command has a 3 byte header
 byte 1 = 'a' or 'b' (ascii or binary)
 byte 2,3 = '00' - '99' number identifiying the type of command
 
Commands
02 JPing [sensor index] : Sensor pings and sends back data (also updates internal value).
        0 = none;
        1 = front;
        2 = left;
        4 = right;
        7 = all;
        This essentially turns on the sensors.
 */
class JPingCommand : public BaseCommand
{
protected:    
    int sensor;
    
public:
    
    //base constructor
    JPingCommand();
    
    //This prints the command out in a string for debugging
    String toString();
    
    //This takes an ascii command and converts it into this command
    void fromString(const char* buffer);
    
    //This initializes the command from a buffer
    //DON"T change the buffer, only read it to initialize values
    void fromBuffer(char* buffer, unsigned int bufferSize);
            
    //This processes the command on the data objects
    //Mostly this sets properties, which effect the behavior
    // during the run loop
    void process(JModel* model);
    
};



#endif