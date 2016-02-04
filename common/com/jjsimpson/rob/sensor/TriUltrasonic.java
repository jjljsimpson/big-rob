package com.jjsimpson.rob.sensor;

import java.util.List;

import com.jjsimpson.rob.log.ILogger;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class TriUltrasonic {

	protected Ultrasonic center;
	protected Ultrasonic left;
	protected Ultrasonic right;
	protected ILogger logger;
	
	public TriUltrasonic(byte centerId, GpioPinDigitalInput centerEcho, GpioPinDigitalOutput centerTrigger,
						byte leftId, GpioPinDigitalInput leftEcho, GpioPinDigitalOutput leftTrigger,
						byte rightId, GpioPinDigitalInput rightEcho, GpioPinDigitalOutput rightTrigger,
						ILogger verbose)
	{
		logger = verbose;
		
		//Create sensor controls for the three ultrasonic controls
		center = new Ultrasonic(centerId, centerEcho, centerTrigger, logger);
		left = new Ultrasonic(leftId, leftEcho, leftTrigger, logger);
		right = new Ultrasonic(rightId, rightEcho, rightTrigger, logger);		
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
	
	
	public List<Long> getCenterValues() {return center.queue;}
	public List<Long> getLeftValues() {return left.queue;}
	public List<Long> getRightValues() {return right.queue;}
	
	public byte getCenterId() { return center.id; }
	public byte getLeftId() { return left.id; }
	public byte getRightId() { return right.id; }
	
	
	public void restart()
	{
		center.restart();
		left.restart();
		right.restart();
	}
	
	
}
