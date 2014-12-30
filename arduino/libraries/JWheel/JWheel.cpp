#ifndef JWHEEL_CPP
#define JWHEEL_CPP

#include "JWheel.h"


JWheel::JWheel()
{
    //Set all the pins to -1 which is probably dangerous
    leftMotorPin = leftBreakPin = leftSpeedPin = leftCurrentPin = -1;
    rightMotorPin = rightBreakPin = rightSpeedPin = rightCurrentPin = -1;
    
    //set speeds (and last speeds) to 0
    leftSpeed = 0;
    rightSpeed = 0;
}


void JWheel::setupMotors(int lmotorPin, int lbreakPin, int lspeedPin, int lcurrentPin,
                 int rmotorPin, int rbreakPin, int rspeedPin, int rcurrentPin)
{
    //First save the pins
    leftMotorPin = lmotorPin;
    leftBreakPin = lbreakPin;
    leftSpeedPin = lspeedPin;
    leftCurrentPin = lcurrentPin;
    
    rightMotorPin = rmotorPin;
    rightBreakPin = rbreakPin;
    rightSpeedPin = rspeedPin;
    rightCurrentPin = rcurrentPin;
    
    //Setup the left motor
    pinMode(leftMotorPin, OUTPUT);  //initialies motor channel pin
    pinMode(leftBreakPin, OUTPUT);  //Initiailezes break channel pin
    
    //set left motor to forward, break on, speed 0
    digitalWrite(leftBreakPin, HIGH);   //turn break on
    digitalWrite(leftMotorPin, HIGH);   //sets to forward
    analogWrite(leftSpeedPin, leftSpeed);           //sets speed to 0
    
    //Setup the right motor
    pinMode(rightMotorPin, OUTPUT); //initialize motor channel pin
    pinMode(rightBreakPin, OUTPUT); //initialize break channel pin
    
    //set right motor to forward, break on, speed 0
    digitalWrite(rightBreakPin, HIGH);  //turn break on
    digitalWrite(rightMotorPin, HIGH);  //set to forward
    analogWrite(rightSpeedPin, rightSpeed);      //set speed to 0    
}



void JWheel::setSpeed(int lSpeed, int rSpeed)
{
    //Set the left motor
    if(lSpeed != leftSpeed)
    {
        leftSpeed = lSpeed; //save the current speed
        
        if(leftSpeed > 0)
        {
            digitalWrite(leftMotorPin, HIGH);   //forward
            digitalWrite(leftBreakPin, LOW);    //break off
            analogWrite(leftSpeedPin, leftSpeed);    //speed
        }
        else if(leftSpeed < 0)
        {
            digitalWrite(leftMotorPin, LOW);    //backward
            digitalWrite(leftBreakPin, LOW);    //break off
            analogWrite(leftSpeedPin, leftSpeed * -1);   //speed
        }
        else
        {
            digitalWrite(leftBreakPin, HIGH);   //turns on the break
            analogWrite(leftSpeedPin, leftSpeed);   //Speed = 0            
        }
        
    }
    
    
    //Set the right motor
    if(rSpeed != rightSpeed)
    {
        rightSpeed = rSpeed;    //save the current speed

        if(rightSpeed > 0)
        {
            digitalWrite(rightMotorPin, HIGH);  //forward
            digitalWrite(rightBreakPin, LOW);   //break off
            analogWrite(rightSpeedPin, rightSpeed);
        }
        else if(rightSpeed < 0)
        {
            digitalWrite(rightMotorPin, LOW);   //backward
            digitalWrite(rightBreakPin, LOW);   //break off
            analogWrite(rightSpeedPin, rightSpeed * -1);
        }
        else
        {
            digitalWrite(rightBreakPin, HIGH);  //turns on the break
            analogWrite(rightSpeedPin, rightSpeed); //speed = 0
        }
    }
    
    
}


int JWheel::getLeftSpeed()
{
    return leftSpeed;
}


int JWheel::getRightSpeed()
{
    return rightSpeed;
}


String JWheel::sensorValues()
{
    String result("TODO");
    
    return result;
}


#endif










