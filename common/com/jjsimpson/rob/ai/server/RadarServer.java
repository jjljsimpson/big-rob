package com.jjsimpson.rob.ai.server;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.commands.sensorvalues.SensorLongCommand;
import com.jjsimpson.rob.comm.commands.sensorvalues.SensorSubCommands;
import com.jjsimpson.rob.comm.commands.sensorvalues.SensorTripleIntCommand;
import com.jjsimpson.rob.comm.model.CommType;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.log.ILogger;
import com.jjsimpson.rob.sensor.value.SensorLong;
import com.jjsimpson.rob.sensor.value.SensorTripleInt;
import com.jjsimpson.rob.utils.NumberConversion;

public class RadarServer extends DistanceServer {
	
	protected static final byte LEFT_DIST_ID = 0;
	protected static final byte CENTER_DIST_ID = 1;
	protected static final byte RIGHT_DIST_ID = 2;
	protected static final byte ACC_ID = 3;
	protected static final byte MAG_ID = 4;
	
	protected SensorLong leftDistance;
	protected SensorLong centerDistance;
	protected SensorLong rightDistance;
	
	protected SensorTripleInt accelerometer;
	protected SensorTripleInt magnetometer;

	public RadarServer(ICommClient comm, ILogger log) {
		super(comm, log);
		
		resetSensor();
	}

	
	@Override
	protected void handleSensorValues(BaseCommand command, CommType type)
	{
		if(type.getMainType() == CommType.TYPE_SENSOR_VALUES)
		{
			//Only look for long values
			if(type.getSubType() == SensorSubCommands.LONG_VALUE)
			{
				//Convert to long value
				handleLongSensor((SensorLongCommand)command);				
			}
			else if(type.getSubType() == SensorSubCommands.TRIPLE_INT_VALUE)
			{
				handleTripleIntSensor((SensorTripleIntCommand)command);
			}
		}
		
	}	
	
	
	@Override
	protected void handleLongSensor(SensorLongCommand command)
	{
		SensorLong val = null;
		
		switch(command.getSensorId())
		{
			case LEFT_DIST_ID:
				val = leftDistance = command.getSensorLong();
				break;
			case CENTER_DIST_ID:
				val = centerDistance = command.getSensorLong();
				break;
			case RIGHT_DIST_ID:
				val = rightDistance = command.getSensorLong();
				break;
		}
		
		if(val != null)
		{
			logger.debug("Got a value: " + val.toString());
			checkSensor();
		}
	}
	
	
	protected void handleTripleIntSensor(SensorTripleIntCommand command)
	{
		SensorTripleInt val = null;
		
		switch(command.getSensorId())
		{
			case ACC_ID:
				val = accelerometer = command.getSensorTripleInt();
				break;
			case MAG_ID:
				val = command.getSensorTripleInt();
				checkCompass(val);
				magnetometer = val;
				break;
		}
		
		if(val != null)
		{
			logger.debug("Got a value: " + val.toString());
			checkSensor();
		}
	}
	
	
	protected void resetSensor()
	{
		leftDistance = null;
		centerDistance = null;
		rightDistance = null;
		
		accelerometer = null;
		magnetometer = null;
	}
	
	
	protected void checkCompass(SensorTripleInt val)
	{
		float head1 = NumberConversion.getHeading(val.x, val.y, val.z);
		float head2 = NumberConversion.getHeading(magnetometer.x, magnetometer.y, magnetometer.z);
		
		//If the compass values are different, then this is a different state
		if( !NumberConversion.compareHeadings(head1, head2) )
		{
			logger.debug("Got a different heading, so reseting values");
			resetSensor();
		}
	}
	
	
	//Call this to see if we have all values.
	// If so, consider this a state and log it
	protected void checkSensor()
	{
		if(leftDistance == null || centerDistance == null || rightDistance == null ||
				accelerometer == null || magnetometer == null)
		{
			return;	//Something is null, so not a full state
		}
				
		logger.debug("------- Sensor State --------");
		logger.debug("LEFT: " + leftDistance.toString());
		logger.debug("CENTER: " + centerDistance.toString());
		logger.debug("RIGHT: " + rightDistance.toString());
		logger.debug("ACCELEROMETER: " + accelerometer.toString());
		logger.debug("MAGNETOMETER: " + magnetometer.toString());
		logger.debug("-----------------------------");
		
		//Now reset and start over
		resetSensor();
	}

}
