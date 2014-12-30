#include "ArduinoResponse.h"

ArduinoResponse::ArduinoResponse(char type)
{
	header[0] = 'J';
	header[1] = 'R';
	header[2] = 'E';
	header[3] = 'S';

	footer[0] = '<';
	footer[1] = '-';
	footer[2] = '-';

	responseType = type;
}


ArduinoResponse::~ArduinoResponse()
{

}

