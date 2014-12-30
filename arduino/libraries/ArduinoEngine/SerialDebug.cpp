
#include "SerialDebug.h"
#include "RemoteCommand.h"
#include "ArduinoResponse.h"

SerialDebug::SerialDebug() : debugResponse(RESPONSE_STRING)
{
	serial_debug = false;
}



void SerialDebug::debugCommand(RemoteCommand* comm)
{
  if(serial_debug == true && comm != NULL)
  {
	Serial.println("-");
    debugHeader();

    Serial.print("Control: ");
    Serial.print((int)comm->control);
    Serial.print(", ");
    Serial.print("Pin: ");
    Serial.print((int)comm->pin);
    Serial.print(", ");
    Serial.print("Command: ");
    Serial.print((char)comm->command);
    Serial.print(", ");
    Serial.print("Value: ");
    Serial.print(comm->value);
    Serial.print("Receipt: ");
    Serial.print((int)comm->receipt);

    debugFooter();
  }
}


void SerialDebug::debugPin(ArduinoPin* pin)
{
	if(serial_debug == true)
	{
		debugHeader();

		Serial.print("pinId: ");
		Serial.print((int)pin->pinId);
		Serial.print(" pinType: ");
		Serial.print((unsigned int)pin->pinType);
		Serial.print(" pinValue: ");
		Serial.print((unsigned long)pin->pinValue);

		debugFooter();
	}
}


void SerialDebug::debugArduinoCommand(char* command, int val1, int val2)
{
	if(serial_debug)
	{
		debugHeader();

		Serial.print(command);
		Serial.print("(");
		Serial.print(val1);
		Serial.print(",");
		Serial.print(val2);
		Serial.print(")");

		debugFooter();
	}
}
void SerialDebug::debugArduinoCommand(char* command, int val1, char* val2)
{
	if(serial_debug)
	{
		debugHeader();

		Serial.print(command);
		Serial.print("(");
		Serial.print(val1);
		Serial.print(",");
		Serial.print(val2);
		Serial.print(")");

		debugFooter();
	}
}


void SerialDebug::debugChar(char message)
{
	if(serial_debug)
	{
		debugHeader();
		Serial.print(message);
		debugFooter();
	}
}

void SerialDebug::debugString(char* message)
{
  if(serial_debug)
  {
    debugHeader();
    Serial.print(message);
    debugFooter();
  }
}


void SerialDebug::debugInt(int val)
{
  if(serial_debug)
  {
    debugHeader();
    Serial.print(val);
    debugFooter();
  }
}

void SerialDebug::debugLong(unsigned long val)
{
	if(serial_debug)
	{
	    debugHeader();
	    Serial.print(val);
	    debugFooter();
	}
}



void SerialDebug::debugSerialRead(int val, int pos)
{
 if(serial_debug)
 {
   debugHeader();
   Serial.print("Read: value-");
   Serial.print(val);
   Serial.print(", position-");
   Serial.print(pos);
   debugFooter();
 }
}


void SerialDebug::debugHeader()
{
  if(serial_debug)
  {
      //Header
    Serial.print(debugResponse.header[0]);
    Serial.print(debugResponse.header[1]);
    Serial.print(debugResponse.header[2]);
    Serial.print(debugResponse.header[3]);
    Serial.print(debugResponse.responseType);
  }
}


void SerialDebug::debugKeyValue(char* key, int value)
{
  if(serial_debug)
  {
    debugHeader();
    Serial.print(key);
    Serial.print(value);
    debugFooter();
  }
}

void SerialDebug::debugFooter()
{
  if(serial_debug)
  {
    //Footer
    Serial.print(debugResponse.footer[0]);
    Serial.print(debugResponse.footer[1]);
    Serial.print(debugResponse.footer[2]);

    //Serial.print("\n");  //just to break things up. Not part of response
  }
}


void SerialDebug::turnOnDebug()
{
	serial_debug = true;
}


void SerialDebug::turnOffDebug()
{
	serial_debug = false;
}


