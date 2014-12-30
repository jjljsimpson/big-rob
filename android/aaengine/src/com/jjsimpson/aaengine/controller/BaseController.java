package com.jjsimpson.aaengine.controller;

import java.util.ArrayList;

import com.jjsimpson.aaengine.arduino.ArduinoCommandWriter;
import com.jjsimpson.aaengine.arduino.Pin;
import com.jjsimpson.aaengine.communication.ArduinoResponse;
import com.jjsimpson.aaengine.communication.RemoteCommand;
import com.jjsimpson.aaengine.event.ArduinoEventListener;
import com.jjsimpson.aaengine.event.ArduinoEventType;
import com.jjsimpson.aaengine.event.EventListenerManager;



public class BaseController
{
	protected ArrayList<Pin> 			pins;
	protected ArduinoCommandWriter		commandWriter;
	protected EventListenerManager		eventManager;
	
	
	public BaseController()
	{
		pins = new ArrayList<Pin>();
		eventManager = new EventListenerManager();
	}
	
	//Get list of pins
	public ArrayList<Pin> getPins() { return pins; }
	
	//Set command Writer
	public void setCommandWriter(ArduinoCommandWriter writer) { commandWriter = writer; }
	
	
	public void initialize()
	{
		//Loop through pins and create commands to initialize them
		Pin pinValue;
		for(int i=0; i<pins.size(); i++)
		{
			pinValue = pins.get(i);
			
			if(commandWriter != null && pinValue != null)
			{
				//Set the pin type
				commandWriter.sendRemoteCommand(RemoteCommand.setPinType(pinValue.pinId, pinValue.pinType));
				
				//Set the initial value
				commandWriter.sendRemoteCommand(RemoteCommand.setPinValue(pinValue.pinId, pinValue.pinValue));
			}
		}
	}
	
	
	public void addEventListener(ArduinoEventType type, ArduinoEventListener listener)
	{
		eventManager.addEventHandler(type, listener);
	}
	
	public void removeEventListener(ArduinoEventType type, ArduinoEventListener listener)
	{
		eventManager.removeHandler(type, listener);
	}
	
	public void clearAllEventListeners()
	{
		eventManager.clearAllEventListeners();
	}
	
	
	//These are response that have been forwarded to this controller
	// The controller should handle the response and fire an event (if needed).
	public void handleReponse(ArduinoResponse response)
	{
		//Override here to fire events
		
//		//Skip if not a pin response
//		if(response.responseType != ArduinoResponse.RESPONSE_PIN || response.responsePin == null)
//			return;
//		
//		//Loop through and find the pin
//		Pin tempPin = null;
//		for(int i=0; i<pins.size(); i++)
//		{
//			tempPin = pins.get(i);
//			if(tempPin.pinId == response.responsePin.pinId)
//			{
//				tempPin.pinValue = response.responsePin.pinValue;
//			}
//		}
	}
	

}
