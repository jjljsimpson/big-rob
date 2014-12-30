#ifndef JPING_H
#define JPING_H

#include "Arduino.h"
#include "BaseCommand.h"

/* *********************** BaseCommand ************************* */
/* Every command has a 3 byte header
 byte 1 = 'a' or 'b' (ascii or binary)
 byte 2,3 = '00' - '99' number identifiying the type of command
 
Commands
01 JPing [sensor index] : Sensor pings and sends back data (also updates internal value). -1 = ping all sensors
 
Example
a01 -1 = ping all sensors
a01 0 = ping front sensor
a01 1 = ping left sensor
a01 2 = ping right sensor
 */
class JPing : public BaseCommand
{
protected:
    
public:
    
    //index of sensor to use
    int sensorIndex;
    
    //base constructor
    JPing();
    
    //This prints the command out in a string for debugging
    String toString();
    
    //This takes an ascii command and converts it into this command
    void fromString(const char* buffer);
    
    //This initializes the command from a buffer
    //DON"T change the buffer, only read it to initialize values
    void fromBuffer(char* buffer, unsigned int bufferSize);        
};



#endif