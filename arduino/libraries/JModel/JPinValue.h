#ifndef JPINVALUE_H
#define JPINVALUE_H

#include "JPin.h"

/* *********************** JPinValue *************************


****************************************************** */


class JPinValue
{
protected:
    
public:
    byte        pinId;          //ID of this pin (that is pin number) (1 byte)
    byte        pinType;        //The way this pin is used (see PIN_TYPE in commands) (1 byte)
    byte        pinValue;       //The value to set this pin (1 byte)
    
    JPinValue();
    void        init(JPin pin);
};



#endif