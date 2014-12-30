#ifndef JSERIALREADER_CPP
#define JSERIALREADER_CPP

#include "JSerialReader.h"
#include <JCommCommand.h>
#include <JPingCommand.h>
#include <JScanCommand.h>
#include <JLeanCommand.h>
#include <JRoamCommand.h>
#include <JSpeedCommand.h>

JSerialReader::JSerialReader()
{
    bufferPosition = 0;
    currentCommand = NULL;
    headerNumber[0] = headerNumber[1] = headerNumber[2] = 0;  //end the string with null terminator
}


BaseCommand* JSerialReader::readSerial()
{
    BaseCommand* result = NULL;
    
    //See if there is anything to read
    int readSize = Serial.available();
    if(readSize > 0)
    {
        //First check if we already have a command
        if(currentCommand == NULL)
        {
            //Need at least 3 bytes to start initializing
            if(readSize >= 3)
            {
                asciiHeader = Serial.read();
                headerNumber[0] = Serial.read();
                headerNumber[1] = Serial.read();
                
                //make sure buffer position is set to 0 so we can read
                bufferPosition = 0;
                
                //Now create a buffer
                int ctype = convertCommandType();
                switch(ctype)
                {
                    case 1:
                        //JComm command
                        currentCommand = new JCommCommand();
                        break;
                        
                    case 2:
                        //JPing command (runs the ultrasonic sensors)
                        currentCommand = new JPingCommand();
                        break;
                        
                    case 3:
                        //JScan command
                        currentCommand = new JScanCommand();
                        break;
                        
                    case 4:
                        //JLean command
                        currentCommand = new JLeanCommand();
                        break;
                    
                    case 5:
                        //JRoam command
                        currentCommand = new JRoamCommand();
                        break;
                        
                    case 6:
                        //JSpeed command
                        currentCommand = new JSpeedCommand();
                        break;
                        
                    default:
                        currentCommand = NULL;
                }
                bufferPosition = 0;  //reset the buffer
            }
        }
        
        if(currentCommand != NULL)
        {
            //Read ascii command
            if(asciiHeader == 'a')
            {
                while(Serial.available() > 0 && bufferPosition < SERIAL_BUFFER_SIZE - 1)
                {
                    buffer[bufferPosition] = Serial.read();
                    if(buffer[bufferPosition] == '\n')
                    {
                        //null terminate the string
                        buffer[bufferPosition] = 0;
                        currentCommand->isAscii = true; //Let the command know that this is an ascii command
                        currentCommand->fromString(&buffer[0]);
                        
                        //return the command, but also reset to null for next read
                        result = currentCommand;
                        currentCommand = NULL;
                    }
                    else
                    {
                        bufferPosition++;  //move to the next character
                    }
                }
            }
            else if(asciiHeader == 'b')
            {
                //JOHN TODO implement binary read
            }
        }
        
        
    }
    
    return result;
}


int JSerialReader::convertCommandType()
{
    int result = 0;
    int val; 
    
    //Get first character
    val = headerNumber[0] - '0';
    if(val < 0 || val > 9)
    {
        val = -1; 
    }
    
    if(val >= 0)
    {
        result = (result * 10) + val; 
    }
    
    //Get second character
    val = headerNumber[1] - '0';
    if(val < 0 || val > 9)
    {
        val = -1; 
    }
    
    if(val >= 0)
    {
        result = (result * 10) + val;
    }
    
    return result;
    
}





#endif