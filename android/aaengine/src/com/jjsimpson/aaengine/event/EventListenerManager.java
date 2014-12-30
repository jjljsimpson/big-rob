package com.jjsimpson.aaengine.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventListenerManager
{
	//Map which holds event listeners
	Map<ArduinoEventType, ArrayList<ArduinoEventListener>> map;
	
	public EventListenerManager()
	{
		map = new HashMap<ArduinoEventType, ArrayList<ArduinoEventListener>>();
	}
	
	
	public void addEventHandler(ArduinoEventType eventType, ArduinoEventListener handler)
	{
		ArrayList<ArduinoEventListener> list = null;
		
		//See if we need to create an array to hold the event
		if(!map.containsKey(eventType))
		{
			list = new ArrayList<ArduinoEventListener>();
			map.put(eventType, list);
		}
		else
		{
			list = map.get(eventType);
		}
		
		//Now add controller to list
		list.add(handler);
	}
	
	
	public void clearEventHandlers()
	{
		map.clear();
	}
	
	
	public ArrayList<ArduinoEventListener> getEventListeners(ArduinoEventType type)
	{
		ArrayList<ArduinoEventListener> result = null;
		
		if(map.containsKey(type))
		{
			result = map.get(type);
		}
		
		return result;
	}
	
	
	public void removeHandler(ArduinoEventType type, ArduinoEventListener listner)
	{
		//Get the list for the event type
		ArrayList<ArduinoEventListener> listenerList = getEventListeners(type);
		
		//Now remove the listener
		if(listenerList != null)
		{
			listenerList.remove(listner);
		}
	}
	
	
	public void clearAllEventListeners()
	{
		//Just clear the map
		map.clear();
	}
	
	
	public void fireEvent(BaseEvent event)
	{
		//Loop through the event listers of the correct type and send event
		ArrayList<ArduinoEventListener> list = getEventListeners(event.eventType);
		if(list != null)
		{
			for(int i=0; i<list.size(); i++)
			{
				list.get(i).onArduinoEvent(event);
			}
		}
	}
	
	
}
