#ifndef BASECOMMAND_H
#define BASECOMMAND_H

#include <Arduino.h>
#include <JModel.h>

/* *********************** BaseCommand ************************* */
/* Every command has a 3 byte header
 byte 1 = 'a' or 'b' (ascii or binary)
 byte 2,3 = '00' - '99' number identifiying the type of command
 
Commands
01 JComm [0, 1, 2, 3] : Checks communication.
        0 = don't do anything, just confirming that it is still connected
        1 = Reset everything
        2 = Send back all sensor info
        3 = Send simple ping back to verify communication
02 JPing [sensor index] : Sensor pings and sends back data (also updates internal value).
        0 = none;
        1 = front;
        2 = left;
        4 = right;
        7 = all
03 JScan [0,1,2] : ping and rotate, send back data.
        1 = regular scan. (all sensors)
        2 = quick scan,   (just the front sensor)
        0 = cancel / reset scan
04 JLean [0,1,2,3] : when roaming or driving with sensors, it determins which way to turn.
        0 = off, don't do any distance detection
        1 = auto (ping, and go which ever way is farthest from object)
        2 = left (go left if something is in the way)
        3 = right (go right if something is in the way)
05 JRoam : drive around and don't hit things. No real destination, just wandering
        (Turns distance sensors to 7, turns lean mode to 1, turns wheel speeds to 0. Then updates per frame)
        If you want to change the lean, call it AFTER this command. (Same with ping sensors).
        The speed is automatic based on the sensor and lean values
06 JSpeed [-255 - 255, -255 - 255] : sets the speed of each wheel. This gives complete controll to each wheel
        -255 - 255 = left wheel speed
        -255 - 255 = right wheel speed
*/
class BaseCommand
{
protected:
    unsigned int type;  //type of command
    unsigned int commandSize;  //doesn't include the header
    
public:    
    bool isAscii;
    
    //base constructor
    BaseCommand();
    
    //This prints the command out in a string for debugging
    String toString();
    
    //This takes an ascii command and converts it into this command
    void fromString(const char* buffer);
    
    //This initializes the command from a buffer
    //DON"T change the buffer, only read it to initialize values
    void fromBuffer(char* buffer, unsigned int bufferSize);
    
    //This returns the command type
    unsigned int commandType() { return type; }
    
    unsigned int getSize() { return commandSize; }
    
    //Convert string to int
    //There may be a string.toInt() method, but this is just in case there isn't
    int stringToInt(String str);
    
    //This processes the command on the data objects
    //Mostly this sets properties, which effect the behavior
    // during the run loop
    void process(JModel* model);
    
};



#endif