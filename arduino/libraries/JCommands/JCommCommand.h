#ifndef JCOMMCOMMAND_H
#define JCOMMCOMMAND_H

#include <Arduino.h>
#include "BaseCommand.h"
#include <JModel.h>

/* *********************** JComm ************************* */
/* Every command has a 3 byte header
 byte 1 = 'a' or 'b' (ascii or binary)
 byte 2,3 = '00' - '99' number identifiying the type of command
 
Commands
01 JComm [0, 1, 2, 3] : Checks communication.
        0 = don't do anything, just confirming that it is still connected
        1 = Reset everything
        2 = Send back all sensor info
        3 = Send simple ping back to verify communication
 */
class JCommCommand : public BaseCommand
{
protected:    
    int commType;
    
public:
    
    //base constructor
    JCommCommand();
    
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