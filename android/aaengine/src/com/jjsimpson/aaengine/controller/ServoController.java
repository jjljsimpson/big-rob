package com.jjsimpson.aaengine.controller;

import com.jjsimpson.aaengine.arduino.Pin;
import com.jjsimpson.aaengine.arduino.PinType;
import com.jjsimpson.aaengine.communication.ArduinoResponse;
import com.jjsimpson.aaengine.communication.RemoteCommand;

public class ServoController extends BaseController
{
	protected Pin servoPin;
	
	public ServoController(int pinId)
	{
		servoPin = new Pin(pinId, PinType.PIN_TYPE_SERVO, 0, this);
		pins.add(servoPin);
	}
	
	
	public void setValue(int val)
	{
		commandWriter.sendRemoteCommand(RemoteCommand.setPinValue(servoPin.pinId, servoPin.pinValue));
	}
	
	
	@Override
	public void handleReponse(ArduinoResponse response)
	{
		super.handleReponse(response);
	}
	
	
}
