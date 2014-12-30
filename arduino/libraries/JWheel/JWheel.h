#ifndef JWHEEL_H
#define JWHEEL_H

#include <Arduino.h>


class JWheel
{
protected:
    int leftMotorPin;
    int leftBreakPin;
    int leftSpeedPin;
    int leftCurrentPin;
    
    int rightMotorPin;
    int rightBreakPin;
    int rightSpeedPin;
    int rightCurrentPin;
    
    int leftSpeed;    
    int rightSpeed;

public:
    
    JWheel();   //default constructor
    
    void setupMotors(int lmotorPin, int lbreakPin, int lspeedPin, int lcurrentPin,
                     int rmotorPin, int rbreakPin, int rspeedPin, int rcurrentPin);
    
    
    void setSpeed(int lSpeed, int rSpeed);
    
    int getLeftSpeed();
    
    int getRightSpeed();

    //This returns the speed of the left and right wheel as a string
    String sensorValues();
};


#endif