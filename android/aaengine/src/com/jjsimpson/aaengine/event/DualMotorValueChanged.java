package com.jjsimpson.aaengine.event;

import com.jjsimpson.aaengine.controller.BaseController;

public class DualMotorValueChanged extends BaseEvent
{
	public int leftSpeed;
	public int rightSpeed;
	
	public DualMotorValueChanged(int left, int right, BaseController cont, int pin)
	{
		super(ArduinoEventType.DualMotorValueChanged, cont, pin);
		leftSpeed = left;
		rightSpeed = right;
	}
	
}
