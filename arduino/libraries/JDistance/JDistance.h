#ifndef JDISTANCE_H
#define JDISTANCE_H

#include <Arduino.h>
#include <NewPing.h>

#define INVALID_PIN         -1
#define MAX_PING_DISTANCE   200 /* Maximum distance we want to ping for (in centimeters). Maximum sensor distance is rated at 400-500cm.*/
#define FRONT_SENSOR_ID     0x1
#define LEFT_SENSOR_ID      0x2
#define RIGHT_SENSOR_ID     0x4




class JDistance
{
protected:
    int frontInPin;
    int frontOutPin;
    int leftInPin;
    int leftOutPin;
    int rightInPin;
    int rightOutPin;
    
    bool frontIsActive;
    bool leftIsActive;
    bool rightIsActive;
    
    int frontDistance;
    int leftDistance;
    int rightDistance;
    
    bool rotateOnScan;
    
    NewPing frontSonar;
    NewPing leftSonar;
    NewPing rightSonar;

public:
    
    JDistance(int fInPin, int fOutPin,
              int lInPin, int lOutPin,
              int rInPin, int rOutPin);
    
    //This turns everything off
    void reset();
    
    //This turns on / off the sensor that are used
    //it is a single int
    //1 = front || 2 = left || 4 = right
    void setActiveSensor(int sensors);
    
    //This returns the sensors that are active
    //it is a single int
    //1 = front || 2 = left || 4 = right    
    int getActiveSensors();
    
    //This pings the active sensors
    //It rotatins if rotation mode is set
    void pingSensor();
    
    //This gets the value of the specified sensor
    //1 = front
    //2 = left
    //4 = right
    int getDistance(int sensor);
    
    //Sets if the sensors are rotated when scanning
    void setRotateMode(bool doRotate);
    
    //This returns the sensor values as a string
    //NOTE: this does not perform a new scan, just returns the values of the last scan
    String sensorValues();
};


#endif