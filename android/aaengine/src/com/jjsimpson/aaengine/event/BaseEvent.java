package com.jjsimpson.aaengine.event;

import com.jjsimpson.aaengine.controller.BaseController;

public class BaseEvent
{
	//They type of event
	public ArduinoEventType eventType;
	
	//Which controller fired the event
	public BaseController	controller;
	
	//Which pin originated the event
	public int pinId;
	
	
	//Base constructor
	public BaseEvent(ArduinoEventType type, BaseController cont, int pin)
	{
		eventType = type;
		controller = cont;
		pinId = pin;
	}
	
	
}
