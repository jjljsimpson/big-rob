package com.jjsimpson.aaengine.controller;

import java.util.ArrayList;

import com.jjsimpson.aaengine.arduino.ArduinoCommandWriter;
import com.jjsimpson.aaengine.arduino.Pin;
import com.jjsimpson.aaengine.communication.ArduinoResponse;

public class DualMotorController extends BaseController
{
	protected static final int LEFT_DIRECTION = 12;
	protected static final int LEFT_SPEED = 3;
	protected static final int LEFT_BREAK = 9;
	protected static final int LEFT_ANALOG_SENSE = 0;
	
	protected static final int RIGHT_DIRECTION = 13;
	protected static final int RIGHT_SPEED = 11;
	protected static final int RIGHT_BREAK = 8;
	protected static final int RIGHT_ANALOG_SENSE = 1;
	
	protected SingleMotorController left;
	protected SingleMotorController right;
	
	
	public DualMotorController()
	{
		super();
		
		left = new SingleMotorController(LEFT_DIRECTION, LEFT_SPEED, LEFT_BREAK);
		right = new SingleMotorController(RIGHT_DIRECTION, RIGHT_SPEED, RIGHT_BREAK);
	}
	
	
	public void handleReponse(ArduinoResponse response)
	{
//		//Update the pin values
//		super.handleReponse(response);
//		
//		if(response.responseType == ArduinoResponse.RESPONSE_PIN && response.responsePin != null)
//		{
//			//Create dual motor value changed event
//			DualMotorValueChanged evt = new DualMotorValueChanged(getLeftSpeed(), getRightSpeed(), this, response.responsePin.pinId);
//			
//			//Fire event
//			eventManager.fireEvent(evt);
//		}
	}
	
	@Override
	public void initialize()
	{
		left.initialize();
		right.initialize();
	}
	
	@Override
	public void setCommandWriter(ArduinoCommandWriter writer)
	{
		left.setCommandWriter(writer);
		right.setCommandWriter(writer);
	}
	
	@Override
	public ArrayList<Pin> getPins()
	{
		ArrayList<Pin> result = left.getPins();
		result.addAll(right.getPins());
		
		return result;
	}
	
	
	public void stopLeft()
	{
		left.stop();
	}
	
	
	public void stopRight()
	{
		right.stop();
	}
	
	
	public void moveLeft(int speed)
	{		
		left.move(speed);
	}
	
	
	public void moveRight(int speed)
	{
		right.move(speed);
	}
	
	
	public int getLeftSpeed()
	{
		return left.getSpeed();
	}
	
	
	public int getRightSpeed()
	{
		return right.getSpeed();
	}
	
	
}
