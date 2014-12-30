package com.jjsimpson.aaengine.engine;

import com.jjsimpson.aaengine.arduino.ArduinoState;
import com.jjsimpson.aaengine.bluetooth.BluetoothDiscovery;
import com.jjsimpson.aaengine.communication.RemoteCommand;


/*

This is a generic engine. This can be sub classed out for more specific controllers.
The basic engine logic is here, sub classes will mainly have easy methods for creating commands.

This holds pointers to two different threads.
- There is a writer thread. You send it commands, which are then sent through bluetooth to the arduino controller.
- There is a reader thread. This thread gets back responses from the arduino controller. It will call the engine which
will process the responses and fire off events.

 */


//This has the state of the Arduino
public class Engine
{
	protected ArduinoState			arduino;
	protected BluetoothDiscovery	bluetooth;
	
	
	public Engine(BluetoothDiscovery blue, ArduinoState state)
	{
		bluetooth = blue;
		arduino = state;
	}
	
	//Initialize the state
	public void initialize()
	{
		arduino.initStates();
	}
	
	
	public void sendRemotePause(int milliseconds)
	{
		arduino.sendRemoteCommand(RemoteCommand.pause(milliseconds), false);
	}
	
	
	public void sendDebugString(String message)
	{
		arduino.sendRemoteCommand(RemoteCommand.getDebugString(message), true);
	}
	
}
