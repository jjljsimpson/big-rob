package com.jjsimpson.rob.ai.server;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.commands.CommandFactory;
import com.jjsimpson.rob.comm.commands.init.ShutdownCommand;
import com.jjsimpson.rob.comm.model.BasicFrame;
import com.jjsimpson.rob.comm.model.CommType;
import com.jjsimpson.rob.comm.thread.ICommReader;
import com.jjsimpson.rob.comm.thread.ICommWriter;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.log.ILogger;
import com.jjsimpson.rob.utils.BaseLoopRunner;

public class BaseAIServer extends BaseLoopRunner implements IAIServer
{
	protected ICommClient	commClient;
	protected ILogger		logger;
	protected ICommReader	reader;
	protected ICommWriter	writer;	
	
	public BaseAIServer(ICommClient comm, ILogger log)
	{
		commClient = comm;
		logger = log;
		isRunning = false;		
		reader = commClient.getCommReader();
		writer = commClient.getCommWriter();
	}
	
	@Override
	public void loopShutdown()
	{
		tellClientToShutdown();
		
		reader.finish();
		writer.finish();
		
		commClient.closeSilently();		
	}
	
	
	@Override
	protected void loopLogic()
	{
		loopRead();
	}
	
	
	protected void loopRead()
	{
		//Read a frame
		BasicFrame frame = reader.getNextFrame();

		//TODO need to cap this at some time interval
		while(frame != null)
		{
			//if frame isn't valid ignore it.
			if(frame.isValid())
			{
				//Get a command from the frame
				CommType type = frame.getType();
				BaseCommand command = CommandFactory.frameToCommand(frame, type);
				
				//Handle the command
				if(command != null) {
					handleCommand(command, type);
				}
			}
			else
			{
				logger.info("There was an invalid frame");
			}
			
			//Keep going
			frame = reader.getNextFrame();
		}		
	}
	
	protected void handleCommand(BaseCommand command, CommType type)
	{
		
	}

	
	protected void tellClientToShutdown()
	{
		writer.writeCommand(new ShutdownCommand());
	}


}
