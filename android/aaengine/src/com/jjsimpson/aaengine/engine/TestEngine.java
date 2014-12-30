package com.jjsimpson.aaengine.engine;

import com.jjsimpson.aaengine.arduino.ArduinoState;
import com.jjsimpson.aaengine.bluetooth.BluetoothDiscovery;
import com.jjsimpson.aaengine.controller.DualMotorController;

public class TestEngine extends Engine
{
	protected static final int PAUSE_AMOUNT = 10000;
	
	DualMotorController		motor;
	
	public TestEngine(BluetoothDiscovery blue, ArduinoState state)
	{
		super(blue, state);
		
		//Create controllers
		motor = new DualMotorController();
		
		//Add them to the state
		state.addController(motor);
	}
	
	
	public void testDualMotor ()
	{		
		//Move forward 1/2 speed
		motor.moveLeft(128);
		motor.moveRight(128);
		
		//Wait
		sendRemotePause(PAUSE_AMOUNT);
		
		//Move Backward 1/2 speed
		motor.moveLeft(-128);
		motor.moveRight(-128);
		
		//Wait
		sendRemotePause(PAUSE_AMOUNT);
		
		//Spin
		motor.moveLeft(64);
		motor.moveRight(-64);
		
		//Wait
		sendRemotePause(PAUSE_AMOUNT);
		
		//Spin the other way
		motor.moveLeft(-32);
		motor.moveRight(32);
		
		//Wait
		sendRemotePause(PAUSE_AMOUNT);
		
		//Stop
		motor.stopLeft();
		motor.stopRight();
		
	}
	
	
	
}
