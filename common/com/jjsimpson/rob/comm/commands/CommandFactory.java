package com.jjsimpson.rob.comm.commands;

import com.jjsimpson.rob.comm.commands.init.FeedbackCommand;
import com.jjsimpson.rob.comm.commands.init.HandshakeCommand;
import com.jjsimpson.rob.comm.commands.init.InitSubCommands;
import com.jjsimpson.rob.comm.commands.init.LogCommand;
import com.jjsimpson.rob.comm.commands.init.PingCommand;
import com.jjsimpson.rob.comm.commands.init.ShutdownCommand;
import com.jjsimpson.rob.comm.commands.sensorvalues.SensorLongCommand;
import com.jjsimpson.rob.comm.commands.sensorvalues.SensorSubCommands;
import com.jjsimpson.rob.comm.commands.sensorvalues.SensorTripleIntCommand;
import com.jjsimpson.rob.comm.model.BasicFrame;
import com.jjsimpson.rob.comm.model.CommType;

public class CommandFactory
{
	public static BaseCommand frameToCommand(BasicFrame frame, CommType type)
	{
		BaseCommand result = null;
		
		if(frame != null)
		{
			switch(type.getMainType())
			{
				case CommType.TYPE_INIT:
					result = createInitCommand(frame, type);
					break;
				case CommType.TYPE_SENSOR_VALUES:
					result = createSensorCommand(frame, type);
					break;
			}
		}
		
		//If it was created, then load
		if(result != null)
		{
			result.readFromByte(frame.getData());
		}
		
		return result;
	}
	
	
	
	protected static BaseCommand createInitCommand(BasicFrame frame, CommType type)
	{
		BaseCommand result = null;
		
		switch(type.getSubType())
		{
			case InitSubCommands.BASE_COMMAND:
				result = new BaseCommand();
				break;			
			case InitSubCommands.FEEDBACK_SUB_COMMAND:
				result = new FeedbackCommand();
				break;
			case InitSubCommands.PING_SUB_COMMAND:
				result = new PingCommand();
				break;
			case InitSubCommands.HANDSHAKE_SUB_COMMAND:
				result = new HandshakeCommand();
				break;
			case InitSubCommands.LOG_SUB_COMMAND:
				result = new LogCommand();
				break;
			case InitSubCommands.SHUTDOWN_SUB_COMMAND:
				result = new ShutdownCommand();
				break;
		}
		return result;
	}
	
	
	protected static BaseCommand createSensorCommand(BasicFrame frame, CommType type)
	{
		BaseCommand result = null;
		
		switch(type.getSubType())
		{
			case SensorSubCommands.LONG_VALUE:
				result = new SensorLongCommand();
				break;
			case SensorSubCommands.TRIPLE_INT_VALUE:
				result = new SensorTripleIntCommand();
				break;
		}
		
		return result;
	}
	
	
}
