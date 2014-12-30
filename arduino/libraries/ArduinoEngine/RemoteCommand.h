
#ifndef REMOTECOMMAND_H_
#define REMOTECOMMAND_H_

#define HEADER_SIZE 4

class RemoteCommand
{
public:
	char 			header[HEADER_SIZE];	//4 bytes
	char			control;	//1 byte
	char			pin;		//1 byte
	char			command;	//1 byte
	unsigned long	value;		//4 bytes
	char			receipt;	//1 byte should match control

public:
	//Constructor
	RemoteCommand();
	RemoteCommand(char com, char pinId, unsigned int val);

	//Reset properties to default value
	void reset();

	//Default Destructor
	virtual ~RemoteCommand();
};

#endif
