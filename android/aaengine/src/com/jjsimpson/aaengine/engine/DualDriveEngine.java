package com.jjsimpson.aaengine.engine;

import com.jjsimpson.aaengine.arduino.ArduinoState;
import com.jjsimpson.aaengine.bluetooth.BluetoothDiscovery;
import com.jjsimpson.aaengine.controller.DualMotorController;

public class DualDriveEngine extends Engine
{
	DualMotorController		motor;
	
	public DualDriveEngine(BluetoothDiscovery blue, ArduinoState state)
	{
		super(blue, state);
		
		//Create controllers
		motor = new DualMotorController();
		
		//Add them to the state
		state.addController(motor);
	}
		
	
	public void stopLeft()
	{
		motor.stopLeft();
	}
	
	
	public void stopRight()
	{
		motor.stopRight();
	}
	
	
	public void moveLeft(int speed)
	{
		motor.moveLeft(speed);
	}
	
	
	public void moveRight(int speed)
	{
		motor.moveRight(speed);
	}
	
	
	public int getLeftSpeed()
	{
		return motor.getLeftSpeed();
	}
	
	
	public int getRightSpeed()
	{
		return motor.getRightSpeed();
	}
	
}
