#ifndef ARDUINOPIN_H_
#define ARDUINOPIN_H_

class ArduinoPin
{

public:

    int	        	pinId;          //ID of this pin (that is pin number) (1 byte)
    unsigned int  	pinType;        //The way this pin is used (see PIN_TYPE in commands) (1 byte)
    unsigned long	pinValue;       //The value to set this pin (4 bytes)
    void*			pointer;		//Hold some object that this pin uses. Will need casting to use

	ArduinoPin();
	ArduinoPin(int id, unsigned int type, unsigned int value);

	virtual ~ArduinoPin();

};

#endif
