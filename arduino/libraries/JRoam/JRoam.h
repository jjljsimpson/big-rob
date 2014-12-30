#ifndef JROAM_H
#define JROAM_H

#include <Arduino.h>
#include <JDistance.h>
#include <JWheel.h>

#define FULL_SPEED 255
#define STOP_SPEED 0
#define SLOW_SPEED 100
#define SLOW_DISTANCE 100

class JRoam
{
protected:
    int leanDirection;  //direction to lean to when roaming. 0 = don't, 1 = auto (which ever is farthest), 2 = left, 3 = right
    bool roamFlag;      //true if roaming, false, if not roaming
    JDistance*  distanceController;
    JWheel*     wheelController;    
    
public:
    
    JRoam();   //default constructor
    
    /*
     This sets up the controller.
     In order to roam, it needs access to the distance sensors and the wheel controller.
    */
    void setupRoaming(JDistance* distance, JWheel* wheel);

    /*
     This turns off the roamer
    */
    void reset();
    
    /*
     This gets the lean direction
    */
    int getLeanDirection() { return leanDirection; }
    
    /*
     This sets the lean direction
    */
    void setLeanDirection(int dir);
    
    /*
     This gets the flag for if roaming is turned on or not.
     */
    bool isRoaming() { return roamFlag; }
    
    /*
     This sets the flag to turn on roaming or not.
    */
    void setRoaming(bool flag) { roamFlag = flag; }
    
    /*
     This does the logic for controlling movement
    */
    void roam();
};


#endif