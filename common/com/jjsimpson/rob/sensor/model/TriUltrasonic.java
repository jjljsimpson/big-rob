package com.jjsimpson.rob.sensor.model;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class TriUltrasonic {

	protected Ultrasonic center;
	protected Ultrasonic left;
	protected Ultrasonic right;
	
	public TriUltrasonic(int centerId, GpioPinDigitalInput centerEcho, GpioPinDigitalOutput centerTrigger,
						int leftId, GpioPinDigitalInput leftEcho, GpioPinDigitalOutput leftTrigger,
						int rightId, GpioPinDigitalInput rightEcho, GpioPinDigitalOutput rightTrigger)
	{
		//Create sensor controls for the three ultrasonic controls
		center = new Ultrasonic(centerId, centerEcho, centerTrigger);
		left = new Ultrasonic(leftId, leftEcho, leftTrigger);
		right = new Ultrasonic(rightId, rightEcho, rightTrigger);
	}
	
	
	public void checkSensor()
	{
		center.checkSensor();
		left.checkSensor();
		right.checkSensor();
	}
	
	
	public void setContinuous(boolean val)
	{
		center.setContinuous(val);
		left.setContinuous(val);
		right.setContinuous(val);
	}	
	
	
	public void restart()
	{
		center.restart();
		left.restart();
		right.restart();
	}
	
	
}
