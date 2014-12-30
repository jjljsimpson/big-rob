#ifndef JROAMCOMMAND_CPP
#define JROAMCOMMAND_CPP

#include <Arduino.h>
#include "BaseCommand.h"
#include "JRoamCommand.h"

JRoamCommand::JRoamCommand()
{
    type = 5;   //JScan == 5
}








void JRoamCommand::process(JModel* model)
{
    //turn on the distance sensors
    model->distanceController.setActiveSensor(7);
    
    //Turn lean mode to auto
    model->roamController.setLeanDirection(1);
    
    //Stop wheels (will be moved during roam logic)
    model->wheelController.setSpeed(0, 0);
    
    //Turn on roaming
    model->roamController.setRoaming(true);
    
    //Do actual movement later on
}

#endif