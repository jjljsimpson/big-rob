package com.jjsimpson.rob.ai.server;

import com.jjsimpson.rob.comm.commands.init.PingCommand;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.log.ILogger;

//This server sends a ping command every few seconds
public class PingAIServer extends BaseAIServer
{
	protected static long	MAX_WAIT = 1000 * 5;	//every 5 seconds
	
	protected long lastPing;
	
	public PingAIServer(ICommClient comm, ILogger log)
	{
		super(comm, log);
		
		lastPing = System.currentTimeMillis();
	}
	
	@Override
	protected void loopRead()
	{
		super.loopRead();
		checkIfPingIsNeeded();
	}
	
	@Override
	protected void loopWrite()
	{
		super.loopWrite();
		checkIfPingIsNeeded();
	}
	
	
	protected void checkIfPingIsNeeded()
	{
		long ctime = System.currentTimeMillis();
		
		if(ctime - lastPing > MAX_WAIT)
		{
			sendPing(ctime);
		}
	}
	
	protected void sendPing(long ctime)
	{
		lastPing = ctime;
		
		//Send a ping command			
		writer.writeCommand(new PingCommand());		
	}

}
