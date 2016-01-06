package com.jjsimpson.rob.ai.server;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.commands.init.HandshakeCommand;
import com.jjsimpson.rob.comm.commands.init.InitSubCommands;
import com.jjsimpson.rob.comm.model.CommType;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.device.profile.BaseDeviceProfile;
import com.jjsimpson.rob.log.ILogger;

public class AIServerFactory extends BaseDeviceProfile
{	
	protected int deviceProfileId;
	
	public AIServerFactory(ICommClient client)
	{
		super(client);
		deviceProfileId = 0;
	}
	
	public AIServerFactory(ICommClient client, ILogger newLogger)
	{
		super(client);
		deviceProfileId = 0;
		logger = newLogger;
	}
	
	
	@Override
	protected void stopThreads()
	{
		//Don't stop the threads, they will be passed onto the AI server
	}
	
	@Override
	protected void closeConnection()
	{
		//Don't close the connections, they will be passed onto the AI server
	}
	
	@Override
	protected void readCommand(BaseCommand command, CommType type)
	{
		logger.debug("Reading command");
		if(type.getMainType() == CommType.TYPE_INIT && type.getSubType() == InitSubCommands.HANDSHAKE_SUB_COMMAND)
		{
			handleHandshakeCommand(command);
		}
		else
		{
			super.readCommand(command, type);
		}
	}
	
	
	protected void handleHandshakeCommand(BaseCommand command)
	{
		logger.debug("Getting handshake command");
		HandshakeCommand cmd = (HandshakeCommand)command;
		deviceProfileId = cmd.getProfileId();
		
		//Now that we know what kind of device we have, let's stop reading stuff
		//This gets us out of the run loop
		isRunning = false;
	}
	
	@Override
	protected void sendFeedback(int control, CommType type)
	{
		//For AIServerFactory, we don't send feedback
	}
	
	
	public int getDeviceProfileId() { return deviceProfileId; }
	
	
	public static IAIServer createAIServer(int profileId, ICommClient commClient, ILogger logger)
	{
		IAIServer result = null;
		
		switch(profileId)
		{
			case BaseDeviceProfile.LOGGER_DEVICE_PROFILE:
				logger.debug("Creating logger ai server");
				result = new LoggerAIServer(commClient, logger);
				break;
		}
		
		return result;
	}
	
	
	@Override
	protected void startThreads()
	{
		writer.start();
		reader.start();
				
		logger.debug("Setting comm threads");
		clientConnection.setCommThreads(reader, writer);		
	}
	
}
