/* ********************************************************* 
Includes go here
********************************************************* */
#include<Wire.h>
#include <ArduinoPin.h>
#include <ArduinoPinType.h>
#include <ArduinoResponse.h>
#include <CommandType.h>
#include <RemoteCommand.h>
#include <QueueList.h>
#include <SerialDebug.h>
#include <CommandReader.h>
#include <Servo.h>
#include <NewPing.h>
#include <Adafruit_LSM303.h>



/* ********************************************************* 
Global Properties
********************************************************* */
#define BLUETOOTH_RX     0
#define BLUETOOTH_TX     1
#define BLUETOOTH_SPEED  115200
#define NUMBER_OF_PINS   14
#define MAX_CM_PING      200

ArduinoPin                allPins[NUMBER_OF_PINS];  //Track all the pins on board
QueueList<RemoteCommand*> commands;
CommandReader             reader;

unsigned long             currentTime;
unsigned long             lastTime;
unsigned long             elapsedTime;
unsigned long             waitTime;
SerialDebug               serialDebug;



/* ********************************************************* 
Setup
********************************************************* */
void setup()
{
  currentTime = millis();
  elapsedTime = waitTime = 0;
  serialDebug.turnOnDebug();
//  serialDebug.turnOffDebug();
  
  //Set the queue for the reader
  reader.setQueue(&commands);
  reader.setDebug(&serialDebug);
    
  //Setup bluetooth
  allPins[BLUETOOTH_RX].pinType = PIN_TYPE_BLUETOOTH;
  allPins[BLUETOOTH_TX].pinType = PIN_TYPE_BLUETOOTH;
  Serial.begin(BLUETOOTH_SPEED);
  Serial.println("Finished with setup");  
}  



/* ********************************************************* 
Main Loop
********************************************************* */
void loop()
{
  lastTime = currentTime;
  currentTime = millis();
  elapsedTime = elapsedTime + (currentTime - lastTime);
  
  //Read commands. Note that this only reads 1 command at a time
  // If the command is read it sends back a "receipt" so that the remote can send another command.
  // if there is an error it sends a receipt of 0 so the remote can resend the command
  // Commands are placed in the commands queue (so they can be processed)
  reader.readSerial();
    
  //Only process if done waiting
  if(elapsedTime > waitTime)
  {
    elapsedTime = waitTime = 0;  //don't wait anymore
    
    //Process all the commands created from read commands
    processCommands();
  }
  
  //Make sure we are finished writing
  Serial.flush();
}




/* ********************************************************* 
Helper methods
********************************************************* */





//This acts on the commands
void processCommands()
{

  RemoteCommand* comm;
  boolean returnAfter = false;
  
  //Process all the commands
   while(!commands.isEmpty())
   {
     //Get next command
     comm = commands.pop(); 
          
     if(comm != NULL)
     {
       //Print the command
       serialDebug.debugString("processing commands ");
       serialDebug.debugCommand(comm);
       
       switch(comm->command)
       {
        case COMMAND_SET_PIN_TYPE:
        case COMMAND_SET_COMPASS:
        case COMMAND_SET_PING:
        case COMMAND_SET_SERVO:
          setPinType(comm);
          break;
        case COMMAND_SET_PIN_VALUE:
          setPinValue(comm);
          break;
        case COMMAND_WAIT:
          doWait(comm);
          returnAfter = true;
          break;
        case COMMAND_GET_PIN_VALUE:
          getPinValue(comm);
          break;
       }
       
       //Clear memory
       delete comm;
       comm = NULL;
       
       if(returnAfter)
         return;
     }
     
   }

}



void setPinType(RemoteCommand* command)
{
  ArduinoPin* pin = &allPins[command->pin];  
  pin->pinType = command->value;
  pin->pinId = command->pin;
  
  //If pin 0 or 1, then stop debug messages (need the pins for something else)
  if(pin->pinType != PIN_TYPE_UNINITIALIZED && (pin->pinId == 0 || pin->pinId == 1) )
  {    
    serialDebug.debugString("Closing serial because pin is needed");
    serialDebug.debugInt(pin->pinType);
    serialDebug.debugInt(pin->pinId);
    serialDebug.turnOffDebug();
    Serial.end();
  }
 
 
  //Just manually setting the pin
  if(command->command == COMMAND_SET_PIN_TYPE)
  {
    switch(pin->pinType)
    {
      case PIN_TYPE_UNINITIALIZED:
        break;
      case PIN_TYPE_D_INPUT:
      case PIN_TYPE_A_INPUT:
        pinMode(pin->pinId, INPUT);
        break;
      case PIN_TYPE_D_INPUT_PULLUP:
      case PIN_TYPE_A_INPUT_PULLUP:
        pinMode(pin->pinId, INPUT_PULLUP);
        break;
      case PIN_TYPE_D_OUTPUT:
      case PIN_TYPE_A_OUTPUT:
        pinMode(pin->pinId, OUTPUT);    
        break;
      case PIN_TYPE_BLUETOOTH:
        break;
    }
  }
  else if(command->command == COMMAND_SET_COMPASS)
  {
    pin->pinType = PIN_TYPE_COMPASS;
    Adafruit_LSM303* cptr = new Adafruit_LSM303();
    cptr->begin();
    pin->pointer = cptr;
    
/*
TODO JLS
  lsm.read();
  Serial.print("Accel X: "); Serial.print((int)lsm.accelData.x); Serial.print(" ");
  Serial.print("Y: "); Serial.print((int)lsm.accelData.y);       Serial.print(" ");
  Serial.print("Z: "); Serial.println((int)lsm.accelData.z);     Serial.print(" ");
  Serial.print("Mag X: "); Serial.print((int)lsm.magData.x);     Serial.print(" ");
  Serial.print("Y: "); Serial.print((int)lsm.magData.y);         Serial.print(" ");
  Serial.print("Z: "); Serial.println((int)lsm.magData.z);       Serial.print(" ");
*/  
  }
  else if(command->command == COMMAND_SET_PING)
  {
    pin->pinType = PIN_TYPE_PING;
    NewPing* pptr = new NewPing(command->value, command->pin, MAX_CM_PING);
    pin->pointer = pptr;  
  }
  else if(command->command == COMMAND_SET_SERVO)
  {
    pin->pinType = PIN_TYPE_SERVO;  
    Servo *sptr = new Servo();
    sptr->attach(command->pin);
    pin->pointer = sptr;   
  }

  
}


void setPinValue(RemoteCommand* command)
{
  ArduinoPin *pin = &allPins[command->pin];  
  pin->pinValue = command->value;
    
  if(pin->pinType == PIN_TYPE_D_OUTPUT)
  {
    if(pin->pinValue == 0)
    {
     digitalWrite(pin->pinId, LOW); 
    }
    else
    {
     digitalWrite(pin->pinId, HIGH); 
    }
  }
  else if(pin->pinType == PIN_TYPE_A_OUTPUT)
  {
     analogWrite(pin->pinId, command->value); 
  }
  
//  serialDebug.debugPin(pin);  
}


void getPinValue(RemoteCommand* command)
{
  ArduinoPin pin = allPins[command->pin];
//  writePinResponse(pin);
}


void doWait(RemoteCommand* command)
{
  elapsedTime = 0;
  waitTime = command->value;
}






