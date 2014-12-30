#ifndef JLEANCOMMAND_H
#define JLEANCOMMAND_H

#include <Arduino.h>
#include "BaseCommand.h"
#include <JModel.h>

/* *********************** JComm ************************* */
/* Every command has a 3 byte header
 byte 1 = 'a' or 'b' (ascii or binary)
 byte 2,3 = '00' - '99' number identifiying the type of command
 
 04 JLean [0,1,2,3] : when roaming or driving with sensors, it determins which way to turn.
    0 = off, don't do any distance detection
    1 = auto (ping, and go which ever way is farthest from object)
    2 = left (go left if something is in the way)
    3 = right (go right if something is in the way)

 
 */
class JLeanCommand : public BaseCommand
{
protected:    
    int leanType;
    
public:
    
    //base constructor
    JLeanCommand();
    
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