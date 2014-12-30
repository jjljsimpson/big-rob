#ifndef JWHEELCOMMAND_H
#define JWHEELCOMMAND_H

#include "Arduino.h"
#include "BaseCommand.h"

/* *********************** BaseCommand ************************* */
/* Every command has a 3 byte header
 byte 1 = 'a' or 'b' (ascii or binary)
 byte 2,3 = '00' - '99' number identifiying the type of command
 */
class JWheelCommand : public BaseCommand
{
protected:
        
public:
    
    int leftSpeed;
    int rightSpeed;
    
    //base constructor
    JWheelCommand();
    
    //This prints the command out in a string for debugging
    // the char* needs to be deleted
    String toString();
    
    //This takes an ascii command and converts it into this command
    void fromString(const char* buffer);
    
    //This initializes the command from a buffer
    //DON"T change the buffer, only read it to initialize values
    void fromBuffer(char* buffer, unsigned int bufferSize);
    
};


#endif