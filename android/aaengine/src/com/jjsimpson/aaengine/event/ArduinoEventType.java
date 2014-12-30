package com.jjsimpson.aaengine.event;

public enum ArduinoEventType
{
	Unknown,			//Some event we don't know about
	ValueChanged,		//Value of something changed
	DualMotorValueChanged	//One or both values on a dual motor controller changed
}
