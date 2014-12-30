
#ifndef SERIALDEBUG_H_
#define SERIALDEBUG_H_

#define CURRENT_COMMAND_VERSION '0'

#include "arduino.h"
#include "RemoteCommand.h"
#include "ArduinoResponse.h"
#include "ArduinoPin.h"

class SerialDebug
{
protected:
	boolean 			serial_debug;
	ArduinoResponse		debugResponse;

public:
	//Constructor
	SerialDebug();

	//Output arduino command (pinMode or Write)
	void debugArduinoCommand(char* command, int val1, int val2);
	void debugArduinoCommand(char* command, int val1, char* val2);

	//Output a remote command
	void debugCommand(RemoteCommand* comm);

	//Output
	void debugPin(ArduinoPin* pin);

	//Output a char
	void debugChar(char message);

	//Output a string
	void debugString(char* message);

	//Output an int
	void debugInt(int val);

	//Output a long
	void debugLong(unsigned long val);

	//Debug serial read (value and position of read)
	void debugSerialRead(int val, int pos);

	//Debug key value pair. A string and then an int
	void debugKeyValue(char* key, int value);

	//Allow debug messages
	void turnOnDebug();

	//Prevent debug messages
	void turnOffDebug();

protected:

	//Outputs the header
	void debugHeader();

	//Output the footer
	void debugFooter();
};

#endif
