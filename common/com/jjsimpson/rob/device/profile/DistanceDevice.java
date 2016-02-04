package com.jjsimpson.rob.device.profile;

import java.util.List;

import com.jjsimpson.rob.comm.commands.sensorvalues.SensorLongCommand;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.log.ILogger;
import com.jjsimpson.rob.sensor.TriUltrasonic;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class DistanceDevice extends BaseDeviceProfile
{
	protected TriUltrasonic	distanceSensor;
	
	public DistanceDevice(ICommClient client)
	{
		super(client);
		
		distanceSensor = null;
	}
	
	
	@Override
	public void loopLogic()
	{
		//Run regular loop logic
		super.loopLogic();
		
		//Now check sensors
		checkSensors();
	}
	
	
	public void setPins(	byte centerId, GpioPinDigitalInput centerEcho, GpioPinDigitalOutput centerTrigger,
							byte leftId, GpioPinDigitalInput leftEcho, GpioPinDigitalOutput leftTrigger,
							byte rightId, GpioPinDigitalInput rightEcho, GpioPinDigitalOutput rightTrigger)
	{
		distanceSensor = new TriUltrasonic(centerId, centerEcho, centerTrigger,
											leftId, leftEcho, leftTrigger,
											rightId, rightEcho, rightTrigger, logger);
	}
	
	
	protected void checkSensors()
	{
		if(distanceSensor != null)
		{
			//Run logic to check the sensor
			distanceSensor.checkSensor();
			
			//Get the values, and send them
			sendValues(distanceSensor.getCenterId(), distanceSensor.getCenterValues());
			sendValues(distanceSensor.getLeftId(), distanceSensor.getLeftValues());
			sendValues(distanceSensor.getRightId(), distanceSensor.getRightValues());
		}
	}
	
	
	protected void sendValues(byte id, List<Long> queue)
	{
		boolean hasValue = false;
		long result = 0;
		
		//Loop through the queue and get the last value (we only care about the last distance value)
		//We do this instead of clear because it is multi-threaded. A value could be added right before we clear it
		while(queue.size() > 0)
		{
			hasValue = true;
			result = queue.remove(0);
		}
		
		//Send the value
		if(hasValue == true)
		{
			writer.writeCommand(new SensorLongCommand(id, result));
		}		
	}
	
	
}
