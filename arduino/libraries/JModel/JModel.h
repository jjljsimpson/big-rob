#ifndef JMODEL_H
#define JMODEL_H

#include <QueueList.h>
#include "JPin.h"
#include "JCommand.h"

/* *********************** JModel ************************* */

#define NUMBER_OF_PINS      14

#define MODE_SCAN           0
#define MODE_RUN            1
#define MODE_TEST           10



class JModel
{
protected:
    
public:
    unsigned long       currentTime;    //The time in milliseconds for now
    unsigned long       lastTime;       //The time in milliseconds for last frame
    unsigned long       frameTime;      //How much time passed between last frame and current frame
    
    JPin*               pins;           //Array of JPins. Used to process the pins
    int                 currentMode;    //Mode of this appication
    
    QueueList<JCommand*> commands;       //List of commands
    QueueList<JCommand*> testCommands;   //List of test commands
    
    JCommand*           currentRead;     //Current command that is being read
    int                 readPosition;    //Position of reading command
    JCommand*           currentTest;     //Current test command
    int                 waitTime;
    
    JModel();
    
};



#endif