#ifndef JSPEEDCOMMAND_H
#define JSPEEDCOMMAND_H

#include <Arduino.h>
#include "BaseCommand.h"
#include <JModel.h>

/* *********************** JComm ************************* */
/* Every command has a 3 byte header
 byte 1 = 'a' or 'b' (ascii or binary)
 byte 2,3 = '00' - '99' number identifiying the type of command
 
Commands
 07 JSpeed [-255 - 255, -255 - 255] : sets the speed of each wheel. This gives complete controll to each wheel
    -255 - 255 = left wheel speed
    -255 - 255 = right wheel speed
 
 */
class JSpeedCommand : public BaseCommand
{
protected:    
    int leftSpeed;
    int rightSpeed;
    
public:
    
    //base constructor
    JSpeedCommand();
    
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