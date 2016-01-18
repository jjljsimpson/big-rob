package com.jjsimpson.rob.device.profile;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.commands.CommandFactory;
import com.jjsimpson.rob.comm.commands.init.FeedbackCommand;
import com.jjsimpson.rob.comm.commands.init.HandshakeCommand;
import com.jjsimpson.rob.comm.commands.init.InitSubCommands;
import com.jjsimpson.rob.comm.commands.init.LogCommand;
import com.jjsimpson.rob.comm.model.BasicFrame;
import com.jjsimpson.rob.comm.model.CommType;
import com.jjsimpson.rob.comm.thread.BasicCommReader;
import com.jjsimpson.rob.comm.thread.BasicCommWriter;
import com.jjsimpson.rob.comm.thread.ICommReader;
import com.jjsimpson.rob.comm.thread.ICommWriter;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.log.ConsoleLogger;
import com.jjsimpson.rob.log.ILogger;
import com.jjsimpson.rob.utils.BaseLoopRunner;
import com.jjsimpson.rob.utils.ILoopRunner;

public class BaseDeviceProfile extends BaseLoopRunner implements IDeviceProfile, ILoopRunner, Runnable
{
	public static final int LOGGER_DEVICE_PROFILE = 1;
	
	protected int				profileId;
	protected ICommClient		clientConnection;
	protected ICommReader		reader;
	protected ICommWriter		writer;
	protected ILogger			logger;
	
	
	public BaseDeviceProfile(ICommClient client)
	{
		profileId = 0;	//unknown profile Id
		clientConnection = client;
		reader = new BasicCommReader();
		writer = new BasicCommWriter();
		logger = new ConsoleLogger();	//Default logger
		
		linkStreams();
	}
	
	
	protected void linkStreams()
	{
		//set the streams and threads
		if(clientConnection != null)
		{
			reader.setStream(clientConnection.getIntputStream());
			writer.setStream(clientConnection.getOutputStream());
			
			clientConnection.setCommThreads(reader, writer);			
		}
	}
	
	
	@Override
	public void setLogger(ILogger logger)
	{
		this.logger = logger;
	}
			
	
	@Override
	public void loopInit() {
		super.loopInit();
		
		//Wait until client is connected
		waitForConnection();
		
		//Start reader / writer threads
		startThreads();		
	}
	
	
	@Override
	public void loopShutdown() {
		//stop reader / writer threads
		stopThreads();
		
		//Close the connection
		closeConnection();		
	}
	
	
	protected void startThreads()
	{
		logger.debug("Setting comm threads");
		linkStreams();		
		
		writer.start();
		reader.start();
						
		logger.debug("Sending Hanshake command");
		
		//Start by sending a handshake
		writer.writeCommand(new HandshakeCommand(profileId));
	}
	
	
	protected void stopThreads()
	{
		if(reader != null) {
			reader.finish();
		}
		
		if(writer != null) {
			writer.finish();
		}
	}
	

	protected void waitForConnection()
	{
		//override this method
		if(clientConnection != null)
		{
			clientConnection.connect();
			
			//Create the reader and writer
			writer.setStream(clientConnection.getOutputStream());
			reader.setStream(clientConnection.getIntputStream());
			reader.setWriter(writer);
		}
	}
	
	
	protected void closeConnection()
	{
		//Close the connection
		clientConnection.closeSilently();		
	}
	
	
	protected void loopLogic()
	{
		//Read a frame
		BasicFrame frame = reader.getNextFrame();
		
		if(frame != null)
		{
			logger.debug("Got frame, going to run command");
			
			//If frame wasn't valid, respond
			if(!frame.isValid())
			{
				sendFeedback(FeedbackCommand.INVALID_CONTROL, new CommType(CommType.TYPE_INIT, InitSubCommands.FEEDBACK_SUB_COMMAND));
			}
			else
			{
				//Otherwise handle the message correctly
				CommType type = frame.getType();
				
				//Create command from the frame
				BaseCommand command = CommandFactory.frameToCommand(frame, type);
				
				//Handle the command
				readCommand(command, type);
				
				//Send response
				sendFeedback(frame.getControl(), type);				
			}
		}		
	}		
	
	
	protected void sendFeedback(int control, CommType type)
	{
		logger.debug("Sending feedback " + type.toString());
		
		boolean isFeedback = false;
		
		//Don't send feedback on feedback, or shutdown
		if(type.getMainType() == CommType.TYPE_INIT)
		{
			int subType = type.getSubType();
			if(subType == InitSubCommands.FEEDBACK_SUB_COMMAND || subType == InitSubCommands.SHUTDOWN_SUB_COMMAND)
			{
				isFeedback = true;
			}
		}
				
		if(!isFeedback)
		{
			writer.sendFeedback(new FeedbackCommand(control));
		}
	}
	

	//Right now we don't do anything
	protected void readCommand(BaseCommand command, CommType type)
	{
		logger.debug("Reading command");
		if(type.getMainType() == CommType.TYPE_INIT)
		{
			int subType = type.getSubType();
			if(subType == InitSubCommands.LOG_SUB_COMMAND)
			{
				handleLogCommand(command);
			}
			else if(subType == InitSubCommands.SHUTDOWN_SUB_COMMAND)
			{
				handleShutdownCommand(command);
			}
		}
		
	}
		
	
	protected void handleShutdownCommand(BaseCommand command)
	{
		closeLoop();
	}
	
	
	protected void handleLogCommand(BaseCommand command)
	{
		logger.debug("Handling Log command");
		//Convert to a log command
		LogCommand cmd = (LogCommand)command;
		
		//Get message
		String msg = cmd.getMessage();
		
		if(logger != null)
		{
			logger.info(msg);
		}
	}

}
