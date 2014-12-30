#ifndef JRESPONSE_H
#define JRESPONSE_H


/* *********************** JResponse *************************
Every response starts with the header JRES

Every response ends with the footer <--

****************************************************** */

#define RESPONSE_STRING     's'
#define RESPONSE_PIN_VALUE  'p'


class JResponse
{
protected:

    
public:
    byte            header[4];      //String JRES
    byte            footer[3];      //String <--

    byte            responseType;    //Type as as string
    
    JResponse(byte resType);
    JResponse();
    
};



#endif