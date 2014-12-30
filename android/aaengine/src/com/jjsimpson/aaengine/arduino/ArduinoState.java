package com.jjsimpson.aaengine.arduino;

import java.util.ArrayList;

import com.jjsimpson.aaengine.communication.RemoteCommand;
import com.jjsimpson.aaengine.controller.BaseController;

public class ArduinoState
{
	protected int						maxPins;
	protected ArrayList<Pin>			pinHolder;
	protected ArduinoCommandWriter		commandWriter;
	protected ArrayList<BaseController>	controllerList;
	
	
	public ArduinoState(int pinNumber, ArduinoCommandWriter writer)
	{
		//number of pins
		maxPins = pinNumber;
		
		//Create array with empty pins
		pinHolder = new ArrayList<Pin>();
		for(int i=0; i<maxPins; i++)
			pinHolder.add(null);
		
		commandWriter = writer;
		controllerList = new ArrayList<BaseController>();
	}
	
	
	public void addController(BaseController controller)
	{
		if(controller != null)
		{
			//Get the list of pins
			ArrayList<Pin> pins = controller.getPins();
			Pin singlePin;
			for(int i=0; i<pins.size(); i++)
			{
				singlePin = pins.get(i);
				pinHolder.set(singlePin.pinId, singlePin);
			}
			
			//Add to this list
			controllerList.add(controller);
			
			//set writer
			controller.setCommandWriter(commandWriter);
		}
	}
		
	
	//Initialize Arduino with start values (and set pin types)
	public void initStates()
	{
		//Loop through all controllers
		for(int i=0; i<controllerList.size(); i++)
		{
			controllerList.get(i).initialize();
		}
	}
	
	
	public void sendRemoteCommand(RemoteCommand com, boolean isDebug)
	{
		if(commandWriter != null)
		{
			commandWriter.sendRemoteCommand(com, isDebug);
		}
	}
	
}
