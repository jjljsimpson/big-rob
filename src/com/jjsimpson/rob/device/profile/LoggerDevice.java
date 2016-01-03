package com.jjsimpson.rob.device.profile;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.model.CommType;
import com.jjsimpson.rob.comm.util.ICommClient;

public class LoggerDevice extends BaseDeviceProfile
{	
	public LoggerDevice(ICommClient client) {
		super(client);
		
		profileId = LOGGER_DEVICE_PROFILE;
	}

	
	@Override
	public void shutdownDevice()
	{
		msg("Starting shutdown");
		super.shutdownDevice();
		msg("Finished shutdown");
	}
	
	
	@Override
	public void run()
	{
		msg("Starting run");
		super.run();
		msg("Finished run");
	}
	
	
	@Override
	protected void startThreads()
	{
		msg("Starting start threads");
		super.startThreads();
		msg("finished start threads");
	}
	
	
	@Override
	protected void stopThreads()
	{
		msg("Starting stopThreads");
		super.stopThreads();
		msg("Finished stopThreads");
	}
	
	@Override
	protected void waitForConnection()
	{
		msg("Starting waitForConnection");
		super.waitForConnection();
		msg("Finished waitForConnection");
	}
	
	
	@Override
	protected void sendFeedback(int control, CommType type)
	{
		msg("Start sendfeedback");
		super.sendFeedback(control, type);
		msg("FInished sendfeedback");
	}
	
	@Override
	protected void readCommand(BaseCommand command, CommType type)
	{
		msg("start readCommand");
		super.readCommand(command, type);
		msg("Finished readCommand");
	}
	
	@Override
	protected void handleLogCommand(BaseCommand command)
	{
		msg("Start handleLogCommand");
		super.handleLogCommand(command);
		msg("finished handleLogCommand");
	}
	
	protected void msg(String message)
	{
		if(logger != null)
		{
			logger.debug(message);
		}
	}
}
