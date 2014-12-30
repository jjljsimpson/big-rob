#ifndef JDISTANCE_CPP
#define JDISTANCE_CPP

#include "JDistance.h"


JDistance::JDistance(int fInPin, int fOutPin, int lInPin, int lOutPin, int rInPin, int rOutPin) :
    frontSonar(fOutPin, fInPin, MAX_PING_DISTANCE),
    leftSonar(lOutPin, lInPin, MAX_PING_DISTANCE),
    rightSonar(rOutPin, rInPin, MAX_PING_DISTANCE)
{
    //Keep track of the pin values
    frontInPin = fInPin;    frontOutPin = fOutPin;
    leftInPin = lInPin;     leftOutPin = lOutPin;
    rightInPin = rInPin;    rightOutPin = rOutPin;
    
    //set default values
    reset();
}


void JDistance::reset()
{
    frontIsActive = leftIsActive = rightIsActive = false;
    frontDistance = leftDistance = rightDistance = MAX_PING_DISTANCE;
    rotateOnScan = false;
}


void JDistance::setActiveSensor(int sensors)
{
    frontIsActive = sensors & FRONT_SENSOR_ID;
    leftIsActive = sensors & LEFT_SENSOR_ID;
    rightIsActive = sensors & RIGHT_SENSOR_ID;
}


int JDistance::getActiveSensors()
{
    int result = 0;
    
    if(frontIsActive)
        result = result | FRONT_SENSOR_ID;
    if(leftIsActive)
        result = result | LEFT_SENSOR_ID;
    if(rightIsActive)
        result = result | RIGHT_SENSOR_ID;
    
    return result;
}


void JDistance::pingSensor()
{
    if(frontIsActive)
    {
        delay(50);  // Wait 50ms between pings (about 20 pings/sec). 29ms should be the shortest delay between pings.
        frontDistance = frontSonar.ping_cm();
    }

    if(leftIsActive)
    {
        delay(50);  // Wait 50ms between pings (about 20 pings/sec). 29ms should be the shortest delay between pings.
        leftDistance = leftSonar.ping_cm();
    }
    
    if(rightIsActive)
    {
        delay(50);
        rightDistance = rightSonar.ping_cm();
    }
    
    
    //TODO
    //If rotating, then ping, and update position.
    // continue until finished. Then set active sensor to 0
}

int JDistance::getDistance(int sensor)
{
    if(sensor == 1)
        return frontDistance;
    else if(sensor == 2)
        return leftDistance;
    else if(sensor == 3)
        return rightDistance;
    else
        return 0;
}


void JDistance::setRotateMode(bool doRotate)
{
    rotateOnScan = doRotate;
}


String JDistance::sensorValues()
{
    String result("Distance Sensors: ");
    
    if(frontIsActive)
    {
        result.concat("1=");
        result.concat(frontDistance);
        result.concat(" ");
    }

    if(leftIsActive)
    {
        result.concat("2=");
        result.concat(leftDistance);
        result.concat(" ");
    }
    
    if(rightIsActive)
    {
        result.concat("3=");
        result.concat(rightDistance);
        result.concat(" ");
    }
    
    return result;
}

#endif










