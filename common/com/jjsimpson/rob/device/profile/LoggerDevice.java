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
	public void closeLoop() {
		msg("Starting shutdown");
		super.closeLoop();
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
		msg("Start sendfeedback for " + type.toString());
		super.sendFeedback(control, type);
		msg("FInished sendfeedback");
	}
	
	@Override
	protected void readCommand(BaseCommand command, CommType type)
	{
		msg("start readCommand for " + type.toString());
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
	
	@Override
	protected void handleShutdownCommand(BaseCommand command)
	{
		msg("Shutting down device");
		super.handleShutdownCommand(command);
	}

	
	protected void msg(String message)
	{
		if(logger != null)
		{
			logger.debug(message);
		}
	}
}
