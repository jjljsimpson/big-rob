
#include "RemoteCommand.h"
#include "CommandType.h"

RemoteCommand::RemoteCommand()
{
	reset();
}

RemoteCommand::RemoteCommand(char com, char pinId, unsigned int val)
{
	reset();
	command = com;
	pin = pinId;
	value = val;
}

void RemoteCommand::reset()
{
	header[0] = 'J';
	header[1] = 'C';
	header[2] = 'O';
	header[3] = 'M';

    command = COMMAND_SET_PIN_TYPE;

    //Default Values
    pin = 0;
    value = 0;
}

RemoteCommand::~RemoteCommand()
{

}

