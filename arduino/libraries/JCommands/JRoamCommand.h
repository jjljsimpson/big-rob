#ifndef JROAMCOMMAND_H
#define JROAMCOMMAND_H

#include <Arduino.h>
#include "BaseCommand.h"
#include <JModel.h>

/* *********************** JComm ************************* */
/* Every command has a 3 byte header
 byte 1 = 'a' or 'b' (ascii or binary)
 byte 2,3 = '00' - '99' number identifiying the type of command
 
 05 JRoam : drive around and don't hit things. No real destination, just wandering
    (Turns distance sensors to 7, turns lean mode to 1, turns wheel speeds to 0. Then updates per frame)
    If you want to change the lean, call it AFTER this command. (Same with ping sensors).
    The speed is automatic based on the sensor and lean values
 
 */
class JRoamCommand : public BaseCommand
{
protected:    
    
public:
    
    //base constructor
    JRoamCommand();
    
    //This prints the command out in a string for debugging
    //String toString();    //Just use default
    
    //This takes an ascii command and converts it into this command
    //void fromString(const char* buffer);  //just use default
    
    //This initializes the command from a buffer
    //DON"T change the buffer, only read it to initialize values
    //void fromBuffer(char* buffer, unsigned int bufferSize);   //Just use default
            
    //This processes the command on the data objects
    //Mostly this sets properties, which effect the behavior
    // during the run loop
    void process(JModel* model);
    
};



#endif