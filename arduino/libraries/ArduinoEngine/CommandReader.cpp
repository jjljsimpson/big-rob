
#include "CommandReader.h"

#include <QueueList.h>

CommandReader::CommandReader() :receiptResponse(RESPONSE_RECEIPT)
{
	commands = NULL;
	currentCommand = NULL;
	missedCommands = 0;
	readPosition = 0;
	debug = NULL;

	for(int i=0; i<COMMAND_TRACK_SIZE; i++)
		tracking[i] = 0;
	trackPosition = 0;
}



void CommandReader::setQueue(QueueList<RemoteCommand*> *queue)
{
	commands = queue;
}

void CommandReader::setDebug(SerialDebug* sdebug)
{
	debug = sdebug;
}


//When reading there is an error in 2 conditions
// Read successfully but receipt doesn't match
// Read halfway, but don't get anymore bytes (after a few cycles, bytes were missed)

void CommandReader::readSerial()
{
	//Get bytes available
	int bytesAvailable = Serial.available();

	//Check if we didn't finish last command
	if(bytesAvailable <= 0)
	{
		if(currentCommand != NULL)
		{
			missedCommands++;
			if(missedCommands >= MISSED_CYCLES)
			{
				sendReceipt(0);	//This means we didn't finish the command because of missed bytes
				delete currentCommand;
				clearCommand();
			}
		}
	}
	else
	{
		missedCommands = 0;	//we started normally, so clear missed commands

		//loop through and read while we can (stop after one command)
		byte readVal;
		while(bytesAvailable)
		{
			//Create a new remote command
			if(currentCommand == NULL)
			{
				currentCommand = new RemoteCommand();
				readPosition = 0;
			}

			//Read 1 byte at a time
			readVal = Serial.read();
			switch(readPosition)
			{
				//First read the header
				case 0:
				case 1:
				case 2:
				case 3:
					//Check the header, if it doesn't match, don't throw error yet. wait for miss (don't count \n)
					readPosition = (readVal == currentCommand->header[readPosition]) ? readPosition + 1 : 0;
					break;
				case 4:
					//read control byte. This is a value (1-255) sent. It matches the receipt value and is incremented for each command.
					//	this is so it can resend this specific command if it fails.
					currentCommand->control = readVal;
					readPosition++;
					break;
				case 5:
					//This is the pin the command relates to
					currentCommand->pin = readVal;
					readPosition++;
					break;
				case 6:
					//Get the command
					currentCommand->command = readVal;
					readPosition++;
					break;
				case 7:
				case 8:
				case 9:
				case 10:
					//Read the value as a 32 bit int
					readValue(readVal, readPosition++ - 7, currentCommand);
					break;
				case 11:
					//Read the receipt
					currentCommand->receipt = readVal;

					//Done reading. If valid then add to queue for processing
					//otherwise send error to resent last command
					if(currentCommand->control != currentCommand->receipt)
					{
						sendReceipt(0);
						delete currentCommand;
					}
					else
					{
						validateCommand(currentCommand);	//make sure we didn't already process this command
						sendReceipt(currentCommand->control);
					}

					clearCommand();
					return;	//only read 1 command, stop here for the next cycle
			};

			//See how many bytes available now
			bytesAvailable = Serial.available();
		}
	}

}



void CommandReader::sendReceipt(char val)
{
	//Send actual receipt
    Serial.print(receiptResponse.header[0]);
    Serial.print(receiptResponse.header[1]);
    Serial.print(receiptResponse.header[2]);
    Serial.print(receiptResponse.header[3]);
    Serial.print(RESPONSE_RECEIPT);

    Serial.print(val);

    Serial.print(receiptResponse.footer[0]);
    Serial.print(receiptResponse.footer[1]);
    Serial.print(receiptResponse.footer[2]);
    Serial.print("\n");
}


void CommandReader::clearCommand()
{
	currentCommand = NULL;
	readPosition = 0;
	missedCommands = 0;
}


//Helper function for reading 4 bytes into a long
void CommandReader::readValue(byte readVal, int pos, RemoteCommand* command)
{
	unsigned long result = 0;

  //Now shift
  if(pos > 0)
	  result = command->value << 8;

  //Or Value
  command->value = result | readVal;
}


void CommandReader::validateCommand(RemoteCommand* command)
{
//	debug->debugString("Validating command");
//	debug->debugCommand(command);

	//First make sure we didn't already get this command
	boolean alreadyDone = false;
	for(int i=0; i<COMMAND_TRACK_SIZE; i++)
	{
		if(tracking[i] == currentCommand->control)
		{
			alreadyDone = true;
			break;
		}
	}

	if(alreadyDone == false)
	{
//		debug->debugCommand(currentCommand);
		commands->push(currentCommand);
		trackPosition = (trackPosition + 1) % COMMAND_TRACK_SIZE;
		tracking[trackPosition] = currentCommand->control;
	}
	else
	{
		delete currentCommand;
	}

//	debug->debugKeyValue("Number of commands: ", commands->count());
}


