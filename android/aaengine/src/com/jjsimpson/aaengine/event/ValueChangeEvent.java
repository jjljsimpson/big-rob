package com.jjsimpson.aaengine.event;

import com.jjsimpson.aaengine.controller.BaseController;

public class ValueChangeEvent extends BaseEvent
{
	public ValueChangeEvent(BaseController control, int pin)
	{
		super(ArduinoEventType.ValueChanged, control, pin);
	}
}
