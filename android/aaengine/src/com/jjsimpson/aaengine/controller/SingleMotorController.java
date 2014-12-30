package com.jjsimpson.aaengine.controller;

import com.jjsimpson.aaengine.arduino.Pin;
import com.jjsimpson.aaengine.arduino.PinType;
import com.jjsimpson.aaengine.communication.ArduinoResponse;
import com.jjsimpson.aaengine.communication.RemoteCommand;

public class SingleMotorController extends BaseController
{
	protected Pin direction;
	protected Pin speed;
	protected Pin brake;
	
	
	public SingleMotorController(int dirPin, int speedPin, int brakePin)
	{
		direction = new Pin(dirPin, PinType.PIN_TYPE_D_OUTPUT, 0, this);
		speed = new Pin(speedPin, PinType.PIN_TYPE_A_OUTPUT, 0, this);
		brake = new Pin(brakePin, PinType.PIN_TYPE_D_OUTPUT, 0, this);
		
		//Add pins
		pins.add(direction);
		pins.add(speed);
		pins.add(brake);		
	}
	
	
	@Override
	public void handleReponse(ArduinoResponse response)
	{
		super.handleReponse(response);
	}
	
		
	
	public void move(int spd)
	{
		int forward = (spd > 0) ? spd : spd * -1;
		int dir = (spd > 0) ? 1 : 0;

		direction.pinValue = dir;
		brake.pinValue = 0;
		speed.pinValue = forward;
		
		if(commandWriter != null)
		{
			commandWriter.sendRemoteCommand(RemoteCommand.setPinValue(direction.pinId, direction.pinValue));
			commandWriter.sendRemoteCommand(RemoteCommand.setPinValue(brake.pinId, brake.pinValue));
			commandWriter.sendRemoteCommand(RemoteCommand.setPinValue(speed.pinId, speed.pinValue));
		}
	}
		
	
	public void stop()
	{
		speed.pinValue = 0;
		brake.pinValue = 1;
		
		if(commandWriter != null)
		{
			commandWriter.sendRemoteCommand(RemoteCommand.setPinValue(brake.pinId, brake.pinValue));
			commandWriter.sendRemoteCommand(RemoteCommand.setPinValue(speed.pinId, speed.pinValue));
		}				
	}
	
	
	public int getSpeed()
	{
		return (direction.pinValue != 0) ? speed.pinValue : speed.pinValue * -1;		
	}
	
	
	
}
