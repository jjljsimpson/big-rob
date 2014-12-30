
#ifndef COMMANDREADER_H_
#define COMMANDREADER_H_


#include "arduino.h"
#include "RemoteCommand.h"
#include <QueueList.h>
#include "SerialDebug.h"
#include "ArduinoResponse.h"

#define		MISSED_CYCLES	10	/*If we go this number of cycles with an unfinished command, then send error back */
#define		COMMAND_TRACK_SIZE	5

class CommandReader
{
protected:
	QueueList<RemoteCommand*>	*commands;
	RemoteCommand				*currentCommand;
	int							missedCommands;
	int							readPosition;
	SerialDebug*				debug;
	ArduinoResponse				receiptResponse;
	int							tracking[COMMAND_TRACK_SIZE];
	int							trackPosition;

public:
	//Constructor
	CommandReader();

	void setQueue(QueueList<RemoteCommand*> *queue);

	void setDebug(SerialDebug* sdebug);

	void readSerial();

protected:

	void sendReceipt(char val);

	void clearCommand();

	void readValue(byte readVal, int pos, RemoteCommand* command);

	void validateCommand(RemoteCommand* command);

};

#endif
