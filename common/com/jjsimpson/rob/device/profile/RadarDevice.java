package com.jjsimpson.rob.device.profile;

import com.jjsimpson.rob.comm.commands.sensorvalues.SensorLongCommand;
import com.jjsimpson.rob.comm.commands.sensorvalues.SensorTripleIntCommand;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.log.ILogger;
import com.jjsimpson.rob.sensor.JRadarSensor;
import com.jjsimpson.rob.sensor.value.SensorLong;
import com.jjsimpson.rob.sensor.value.SensorTripleInt;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.i2c.I2CDevice;

public class RadarDevice extends BaseDeviceProfile
{
	protected JRadarSensor radarSensor;
	
	public RadarDevice(ICommClient client)
	{
		super(client);
		
		radarSensor = null;
	}
	
	
	public RadarDevice(ICommClient client, ILogger verbose)
	{
		super(client);
		radarSensor = null;
		logger = verbose;
	}
	
	
	public void setPins(byte accelerometerId, I2CDevice accel,
						byte magnetometerId, I2CDevice mag,
						byte centerId, GpioPinDigitalInput centerEcho, GpioPinDigitalOutput centerTrigger,
						byte leftId, GpioPinDigitalInput leftEcho, GpioPinDigitalOutput leftTrigger,
						byte rightId, GpioPinDigitalInput rightEcho, GpioPinDigitalOutput rightTrigger)
	{
		radarSensor = new JRadarSensor(accelerometerId, accel, magnetometerId, mag, centerId, centerEcho, centerTrigger,
						leftId, leftEcho, leftTrigger, rightId, rightEcho, rightTrigger, logger);
	}

	
	@Override
	public void loopLogic()
	{
		//Run regular loop logic
		super.loopLogic();
		
		//Now check sensors
		checkSensors();
		
		//Get values
		getValues();
	}

	
	protected void checkSensors()
	{
		if(radarSensor != null)
		{
			radarSensor.checkSensors();
		}
	}
	
	
	protected void getValues()
	{
		if(radarSensor != null)
		{
			radarSensor.getCurrentValues();
			
			//If no values, then don't call this a new state
			if(radarSensor.leftValue == null && radarSensor.rightValue == null &&
			   radarSensor.centerValue == null && radarSensor.accelerometerValue == null &&
			   radarSensor.compassValue == null) {
				return;
			}
			
			if(logger != null)
			{
				logger.debug("--------- Device State --------------");
			}
			
			//Send the values
			sendLongValue(radarSensor.leftValue);
			sendLongValue(radarSensor.centerValue);
			sendLongValue(radarSensor.rightValue);
			sendTripleIntValue(radarSensor.accelerometerValue);
			sendTripleIntValue(radarSensor.compassValue);
			
			if(logger != null)
			{
				logger.debug("--------- ------------ --------------");
			}			
		}
		else if(logger != null)
		{
			logger.debug("Radar Sensor not setup yet");
		}
	}
	
	
	protected void sendLongValue(SensorLong value)
	{
		if(value != null)
		{
			writer.writeCommand(new SensorLongCommand(value));
			
			if(logger != null) {
				logger.debug(value.toString());
			}
		}
		else if(logger != null)
		{
			logger.debug("Null Long Value");
		}
	}
	
	protected void sendTripleIntValue(SensorTripleInt value)
	{
		if(value != null)
		{
			writer.writeCommand(new SensorTripleIntCommand(value));
			
			if(logger != null) {
				logger.debug(value.toString());
			}
		}
		else if(logger != null)
		{
			logger.debug("Null Triple Int Value");
		}
	}

}
