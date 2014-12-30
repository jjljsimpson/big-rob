package com.jjsimpson.aaengine;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import com.jjsimpson.aaengine.activity.ArduinoActivity;
import com.jjsimpson.aaengine.arduino.ArduinoCommandWriter;
import com.jjsimpson.aaengine.arduino.ArduinoState;
import com.jjsimpson.aaengine.bluetooth.BluetoothDiscovery;
import com.jjsimpson.aaengine.communication.ArduinoResponseHandler;
import com.jjsimpson.aaengine.communication.RemoteCommand;


/*
TODO -
	Remove Engine stuff?
- Have reader read responses
- Have activity handle responses	
*/

public class ArduinoEngineApplication extends Application implements ArduinoResponseHandler, ArduinoCommandWriter
{
	protected static final int PIN_NUMBER = 14;
	
	BluetoothDiscovery	bluetooth;
	ArduinoState		arduinoState;
	ArduinoActivity		currentActivity = null;
	
	//Set the current activity
	public synchronized void setCurrentActivity(ArduinoActivity activity) { currentActivity = activity; }
	
	//Tell the activity to handle responses	
	public synchronized void activityHandleResponse()
	{
		//Pass this event down to the activity
		if(currentActivity != null)
			currentActivity.updateActivityFromBgThread();
	}

	//Called when the service starts
	@Override
	public void onCreate()
	{
		super.onCreate();
				
		//Create the bluetooth object (and use this as a response handler)
		bluetooth = new BluetoothDiscovery();		
//		bluetooth.setResponseHandler(this);	//TODO JLS
		
		arduinoState = new ArduinoState(PIN_NUMBER, this);
	}
	
	
	
	//Called when the service ends
	@Override
	public void onTerminate()
	{
		//Disconnect from bluetooth and kill threads
		bluetooth.destroy();
		
		super.onTerminate();		
	}
	
	
	
	//Accessors to singletons
	public BluetoothDiscovery getBluetooth() { return bluetooth; }
	public ArduinoState getArduinoState() { return arduinoState; }
	
	
	//This sends a message to the writer thread.
	// The call is in the UI thread, but posted to the child thread.
	// The child tread gets the messages in the looper and processes
	public void sendRemoteCommand(RemoteCommand com) { sendRemoteCommand(com, false); }
	public void sendRemoteCommand(RemoteCommand com, boolean isDebug)
	{
		//Get writer handler
		Handler han = bluetooth.getWriteHandler();
		
		//Get message
		Message msg = han.obtainMessage();
		
		//Set the message
		msg.arg1 = (isDebug) ? RemoteCommand.COMMAND_DEBUG : RemoteCommand.COMMAND_MESSAGE;	//flag that says the object is a remote command
		msg.obj = com;
		
		//Send the message
		han.sendMessage(msg);
	}
	 
		
}
