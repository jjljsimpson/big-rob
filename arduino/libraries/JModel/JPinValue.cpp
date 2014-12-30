#ifndef JPINVALUE_CPP
#define JPINVALUE_CPP

#include "JPinValue.h"


JPinValue::JPinValue()
{
    
}

void JPinValue::init(JPin pin, long currentTime)
{
    pinId = pin.pinId;
    pinType = pin.pinType;
    pinValue = pin.pinValue;
    timestamp = currentTime;
}

#endif