#ifndef JSCAN_H
#define JSCAN_H

#include "Arduino.h"
#include "BaseCommand.h"

/* *********************** BaseCommand ************************* */
/* Every command has a 3 byte header
 byte 1 = 'a' or 'b' (ascii or binary)
 byte 2,3 = '00' - '99' number identifiying the type of command
 
Commands
02 JScan [0,1,2] : ping and rotate, send back data. 1 = regular scan. 2 = quick scan, 0 = cancel / reset scan
 
Example
a02 0 = cancel / reset scan
a02 1 = regular scan
a02 2 = quick scan (only scan with front ping)
 */
class JScan : public BaseCommand
{
protected:
    
public:
    
    //type of scan to do
    int scanType;
    
    //base constructor
    JScan();
    
    //This prints the command out in a string for debugging
    String toString();
    
    //This takes an ascii command and converts it into this command
    void fromString(const char* buffer);
    
    //This initializes the command from a buffer
    //DON"T change the buffer, only read it to initialize values
    void fromBuffer(char* buffer, unsigned int bufferSize);        
};



#endif