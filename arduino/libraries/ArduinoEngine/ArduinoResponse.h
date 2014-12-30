#ifndef ARDUINORESPONSE_H_
#define ARDUINORESPONSE_H_

#define RESPONSE_STRING     's'
#define RESPONSE_PIN_VALUE  'p'
#define RESPONSE_RECEIPT	'r'


class ArduinoResponse
{

public:
    char            header[4];      //String JRES
    char            footer[3];      //String <--

    char            responseType;    //Type as as string

    ArduinoResponse(char type);
	virtual ~ArduinoResponse();
};

#endif
