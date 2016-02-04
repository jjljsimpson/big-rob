package com.jjsimpson.rob.sensor;

import java.util.List;

import com.jjsimpson.rob.log.ILogger;
import com.jjsimpson.rob.sensor.value.SensorLong;
import com.jjsimpson.rob.sensor.value.SensorTripleInt;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.i2c.I2CDevice;

/**
 *	JRadarSensor is a sensor I created out of 5 different sensors
 *	There is an accelerometer which gives you an x,y,z value
 *	There is a magnetometer which gives you compass directions (x,y,z value)
 *	There are 3 ultrasonic sensor which give you a Long for the time it takes to hit an obstacle 
 */
public class JRadarSensor
{
	protected ILogger logger;
	public SensorLong leftValue;
	public SensorLong centerValue;
	public SensorLong rightValue;
	
	public SensorTripleInt compassValue;
	public SensorTripleInt accelerometerValue;
	
	protected TriUltrasonic ultrasonicSensor;
	protected LSM303Sensor compass;
	
	public JRadarSensor(byte accelerometerId, I2CDevice accel,
						byte magnetometerId, I2CDevice mag,
						byte centerId, GpioPinDigitalInput centerEcho, GpioPinDigitalOutput centerTrigger,
						byte leftId, GpioPinDigitalInput leftEcho, GpioPinDigitalOutput leftTrigger,
						byte rightId, GpioPinDigitalInput rightEcho, GpioPinDigitalOutput rightTrigger,
						ILogger verbose)
	{
		logger = verbose;
		ultrasonicSensor = new TriUltrasonic(centerId, centerEcho, centerTrigger,
											leftId, leftEcho, leftTrigger,
											rightId, rightEcho, rightTrigger, logger);
		compass = new LSM303Sensor(accelerometerId, magnetometerId, accel, mag, logger);
		
		leftValue = null;
		centerValue = null;
		rightValue = null;
		compassValue = null;
		accelerometerValue = null;
	}
	
	//Start the sensors
	public void startSensors()
	{
		compass.startSensor();
	}
	
	
	//Get values from the sensors
	public void checkSensors()
	{
		compass.checkSensor();
		ultrasonicSensor.checkSensor();
	}
	
	
	public void getCurrentValues()
	{
		//Note that the values will be NULL
		// if there are no value. Otherwise
		// each value has the latest value.
		// it is not guaranteed that the sensors are in sync.
		
		//Get latest ultrasonic values
		leftValue = getSensorLongValue(ultrasonicSensor.getLeftId(), ultrasonicSensor.getLeftValues());
		centerValue = getSensorLongValue(ultrasonicSensor.getCenterId(), ultrasonicSensor.getCenterValues());
		rightValue = getSensorLongValue(ultrasonicSensor.getRightId(), ultrasonicSensor.getRightValues());
		
		//Get compass value
		compassValue = getTripleIntValue(compass.magnetometerQueue);
		accelerometerValue = getTripleIntValue(compass.accelerometerQueue);
	}
	
	
	protected SensorLong getSensorLongValue(byte id, List<Long> queue)
	{
		SensorLong result = null;
		
		//Get last result
		boolean didHit = false;
		long val = 0;
		while(!queue.isEmpty())
		{
			val = queue.remove(0);
			didHit = true;
		}
		
		if(didHit) {
			result = new SensorLong(id, val);
		}
		
		return result;
	}
	
	
	protected SensorTripleInt getTripleIntValue(List<SensorTripleInt> queue)
	{
		SensorTripleInt result = null;
		
		while(!queue.isEmpty())
		{
			result = queue.remove(0);
		}
		
		return result;
	}
	
	

}
