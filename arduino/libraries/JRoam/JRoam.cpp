#ifndef JROAM_CPP
#define JROAM_CPP

#include "JRoam.h"


JRoam::JRoam()
{
    wheelController = NULL;
    distanceController = NULL;
    
    reset();
}

void JRoam::reset()
{
    leanDirection = 0;
    roamFlag = false;
}


void JRoam::setupRoaming(JDistance* distance, JWheel* wheel)
{
    distanceController = distance;
    wheelController = wheel;
}



void JRoam::setLeanDirection(int dir)
{
    if(dir < 0 || dir > 3)
    {
        dir = 1;
    }
    leanDirection = dir;
}



void JRoam::roam()
{
    //Don't do anything if not roaming
    if(roamFlag == false)
        return;
    
    //Check the distance to the front
    int dist = distanceController.getDistance(1);
    
    if(dist > SLOW_DISTANCE)
    {
        //Nothing in it's path, so go full speed
        wheelController.setSpeed(FULL_SPEED, FULL_SPEED);
    }
    else
    {
        //TODO
        //Need to slow down and turn
    }
    
}


#endif










