#include "ArduinoPin.h"
#include "ArduinoPinType.h"

ArduinoPin::ArduinoPin()
{
	pinId = 0;
	pinType = PIN_TYPE_UNINITIALIZED;
	pinValue = 0;
	pointer = 0;
}

ArduinoPin::ArduinoPin(int id, unsigned int type, unsigned int value)
{
	pinId = id;
	pinType = type;
	pinValue = value;
	pointer = 0;
}


ArduinoPin::~ArduinoPin()
{
	pointer = 0;	//This causes a memory leak. Need to do delete too!
}

