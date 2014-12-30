#ifndef JSCANCOMMAND_H
#define JSCANCOMMAND_H

#include <Arduino.h>
#include "BaseCommand.h"
#include <JModel.h>

/* *********************** JComm ************************* */
/* Every command has a 3 byte header
 byte 1 = 'a' or 'b' (ascii or binary)
 byte 2,3 = '00' - '99' number identifiying the type of command
 
Commands
 03 JScan [0,1,2] : ping and rotate, send back data.
 1 = regular scan. (all sensors)
 2 = quick scan,   (just the front sensor)
 0 = cancel / reset scan
 
 */
class JScanCommand : public BaseCommand
{
protected:    
    int scanType;
    
public:
    
    //base constructor
    JScanCommand();
    
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