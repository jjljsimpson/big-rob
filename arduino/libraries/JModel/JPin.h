#ifndef JPIN_H
#define JPIN_H


/* *********************** JPin *************************


****************************************************** */


class JPin
{
protected:
    long        lastUpdate;     //When pin was last updated (4 bytes)
    
public:
    byte        pinId;          //ID of this pin (that is pin number) (1 byte)
    byte        pinType;        //The way this pin is used (see PIN_TYPE in commands) (1 byte)
    byte        pinValue;       //The value to set this pin (1 byte)
    long        pinUpdate;      //How often to update the value 0 - never, 1 - always. Or how often to read value   (4 bytes)
    long        pinTimePassed;  //how much time passed since last update (4 bytes)
    
    JPin();
    
    //Update the value
    void update();
    
    //Read the value
    byte getValue();
};



#endif