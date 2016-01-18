package com.jjsimpson.rob.sensor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;



public class Ultrasonic
{
	protected static final long STARTUP_TIME = 2000;	//2 seconds before sensor is ready
	protected static final long INITIALIZE_TIME = 10;	//milliseconds before using sensor
	protected static final long INITIALIZE_SENSOR_TIME = 10 * 1000;	//10 micro seconds
	
	public List<Long>	queue;	//This is thread safe
	public int		id;				//ID for this sensor
	public int		sensorStatus;	//status of the sensor (used in the loop)
	protected long	startupTime;	//When this class first started up
	protected long	initTime;		//When initialization started
	protected long	initSensor;		//When sensor was initialized
	protected long	startSensor;	//When the sensor starts reading a value
	protected boolean continuous;	//If we continue to grab values, or stop
	
	protected GpioPinDigitalInput echoPin;	//Pin for reading values	
	protected GpioPinDigitalOutput triggerPin;	//Pin for telling it to send signal
	
	protected int callCount;	//REMOVE THIS
	
	public Ultrasonic(int sensorId, GpioPinDigitalInput echo, GpioPinDigitalOutput trig)
	{
		id = sensorId;
		queue = Collections.synchronizedList(new ArrayList<Long>());
		startupTime = System.currentTimeMillis();
		continuous = true;		
		echoPin = echo;
		triggerPin = trig;
		sensorStatus = UltrasonicStates.READY_START;
		initTime = 0;
		startSensor = 0;
		initSensor = 0;
	}
	
	public void setContinuous(boolean val) { continuous = val; }
	public boolean getContinuous() { return continuous; }	
	
	public void checkSensor()
	{
		callCount++;
		switch(sensorStatus)
		{
			case UltrasonicStates.READY_START:
				checkReady();
				break;
			case UltrasonicStates.SENSOR_INIT:
				checkInit();
				break;
			case UltrasonicStates.SENSOR_START:
				checkStart();
				break;
			case UltrasonicStates.SENSOR_WAIT:
				checkWait();
				break;
			case UltrasonicStates.SENSOR_RESPOND:
				checkResponse();
				break;
			case UltrasonicStates.SENSOR_FINISHED:
				checkFinish();
				break;
		}
	}
	
	
	//Restart reading the sensor
	public void restart()
	{
		initTime = 0;
		startSensor = 0;
		initSensor = 0;
		sensorStatus = UltrasonicStates.READY_START;		
	}
	
	
	//Wait 2 seconds for sensor to become ready. This is one time only
	protected void checkReady()
	{
		//Wait 2 seconds for sensor to startup
		long current = System.currentTimeMillis();
		
		if(current - startupTime > STARTUP_TIME)
		{
			//Done waiting, move to sensor init
			initTime = 0;
			startSensor = 0;
			initSensor = 0;
			sensorStatus = UltrasonicStates.SENSOR_INIT;
			message("Switching from ready to sensor init");
		}
	}
	
	
	//Before we check for a value, we need to set to low to reset the sensor
	// This is every time before the sensor starts
	protected void checkInit()
	{
		if(initTime <= 0)
		{
			//set triger to low	
			triggerPin.low();

			//We are starting the init so set start to now
			initTime = System.currentTimeMillis();
		}
		
		long currentTime = System.currentTimeMillis();
		if(currentTime - initTime > INITIALIZE_TIME)
		{
			//initializing is done, so start
			sensorStatus = UltrasonicStates.SENSOR_START;
			message("Switching from init to sensor start");
		}
	}
	
		
	//Start the sensor
	// we set the trigger high for 10 micro seconds
	protected void checkStart()
	{
		//Set trigger to high for 10 micro seconds
		if(initSensor == 0)
		{
			//set trigger high
			triggerPin.high();
			initSensor = System.nanoTime();
		}
		else
		{
			long currentTime = System.nanoTime();
			if(currentTime - initSensor >= INITIALIZE_SENSOR_TIME)
			{
				//set trigger low
				triggerPin.low();
				
				//Done, so move to wait
				sensorStatus = UltrasonicStates.SENSOR_WAIT;
				message("Switching from start to sensor wait");
			}
		}		
	}
	
	
	//Wait until the sensor is high.
	protected void checkWait()
	{
		//check if echo is high
		if(echoPin.isHigh())
		{
			//This is when the signal goes from low to high
			startSensor = System.nanoTime();
			sensorStatus = UltrasonicStates.SENSOR_RESPOND;
			message("switching from sensor wait to sensor respond");
		}		
	}
	
	
	//See how long the sensor is high
	// once it is low, we are finished
	protected void checkResponse()
	{
		//wait until sensor has low value
		if(echoPin.isLow())	//if sensor is low
		{
			long endSensor = System.nanoTime();
			queue.add(endSensor - startSensor);	//Add it to the queue
			sensorStatus = UltrasonicStates.SENSOR_FINISHED;
			message("Switching from sensor response to sensor finished");
		}
	}
	
	
	//We are finished, reset and start again.
	// or stay here
	protected void checkFinish()
	{
		//If we should repeat, then start over
		if(continuous == true)
		{			
			//Reset and start over
			initTime = 0;
			initSensor = 0;
			startSensor = 0;
			sensorStatus = UltrasonicStates.SENSOR_INIT;
			message("Switching from sensor finish to sensor init");
			message("--------");
		}		
	}
	
	
	//------------ Remove this, only for testing
	protected void message(String msg)
	{
		System.out.println(msg + " - CallCount:" + Integer.toString(callCount));
		callCount = 0;
	}
	
	
}
