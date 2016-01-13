package com.jjsimpson.rob.ai.server;

import com.jjsimpson.rob.comm.commands.init.LogCommand;
import com.jjsimpson.rob.comm.model.CommType;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.log.ILogger;

public class LoggerAIServer extends PingAIServer
{	
	protected static final int MAX_LOG_COUNT = 4;
	protected static final long MAX_TIME = 4500;	//send a message every 4.5 seconds
	protected int logNumber;
	protected long lastLog;
	
	public LoggerAIServer(ICommClient comm, ILogger log)
	{
		super(comm, log);
		
		logNumber = 0;
		lastLog = System.currentTimeMillis();
	}
	
	
	@Override
	protected void loopLogic()
	{
		super.loopLogic();
		loopAI();
	}	
	
	protected void loopAI()
	{
		//Check if we need to send a log message
		long now = System.currentTimeMillis();
		if(now - lastLog > MAX_TIME)
		{
			lastLog = now;
			String msg = "Log Message from server " + Integer.toString(getLogNumber());
			LogCommand cmd = new LogCommand(msg);			
			logger.debug("Server is sending log");
			logger.debug(msg);
			writer.writeCommand(cmd);
			
			//we sent something so reset ping
			resetPing(now);
		}
	}
	
	
	protected int getLogNumber()
	{
		logNumber++;
		
		if(logNumber > 200) {
			logNumber = 0;
		}
		
		//Only send log a few times
		if(logNumber > (MAX_LOG_COUNT-1)) {
			isRunning = false;
		}
		
		return logNumber;
	}
	
	@Override
	protected void sendPing(long ctime)
	{
		logger.debug("Server is sending a ping");
		super.sendPing(ctime);
	}
	
	@Override
	protected void tellClientToShutdown()
	{
		logger.debug("Server is going to shutdown, telling client to shutdown");
		super.tellClientToShutdown();
	}
}
