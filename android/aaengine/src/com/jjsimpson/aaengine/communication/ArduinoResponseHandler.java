package com.jjsimpson.aaengine.communication;

public interface ArduinoResponseHandler
{
	//Call this, which calls the current activity on the UI thread
	public void activityHandleResponse();
}
