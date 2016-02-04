package com.jjsimpson.rob.ai.server;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.commands.sensorvalues.SensorLongCommand;
import com.jjsimpson.rob.comm.commands.sensorvalues.SensorSubCommands;
import com.jjsimpson.rob.comm.model.CommType;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.log.ILogger;
import com.jjsimpson.rob.sensor.value.SensorLong;

public class DistanceServer extends PingAIServer {

	protected static final byte DISTANCE_SENSOR_ID = 0;
	
	protected long lastSensorRead;
	
	public DistanceServer(ICommClient comm, ILogger log) {
		super(comm, log);
		lastSensorRead = 0;
	}

	
	@Override
	protected void handleCommand(BaseCommand command, CommType type)
	{
		//super handle command
		super.handleCommand(command, type);
		
		if(type.getMainType() == CommType.TYPE_SENSOR_VALUES)
		{
			handleSensorValues(command, type);
		}
	}	

	
	protected void handleSensorValues(BaseCommand command, CommType type)
	{
		//Only look for long values
		if(type.getMainType() == CommType.TYPE_SENSOR_VALUES && type.getSubType() == SensorSubCommands.LONG_VALUE)
		{
			//Convert to long value
			handleLongSensor((SensorLongCommand)command);
		}

	}
	
	
	protected void handleLongSensor(SensorLongCommand command)
	{
		//Make sure it is the correct ID
		if(command.getSensorId() == DISTANCE_SENSOR_ID)
		{
			logger.debug("--- Got Sensor Value ---");
			SensorLong sensor = new SensorLong((byte)command.getSensorId(), command.getValue());
			logger.debug("Sensor Value - " + sensor.toString());
			
			//Output time since last sensor value
			if(lastSensorRead == 0) {
				lastSensorRead = System.currentTimeMillis();
			}
			else {
				long now = System.currentTimeMillis();
				long elap = now - lastSensorRead;
				lastSensorRead = now;
				logger.debug("Mill since last sensor read - " + Long.toString(elap));
			}
		}
	}
	

}
