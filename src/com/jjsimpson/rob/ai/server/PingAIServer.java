package com.jjsimpson.rob.ai.server;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.commands.init.PingCommand;
import com.jjsimpson.rob.comm.model.CommType;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.log.ILogger;

//This server sends a ping command every few seconds
public class PingAIServer extends BaseAIServer
{
	protected static long	MAX_WAIT = 1000;	//every second
	
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
		resetPing(ctime);
		
		//Send a ping command			
		writer.writeCommand(new PingCommand());		
	}
	
	
	protected void resetPing() { lastPing = System.currentTimeMillis(); }
	protected void resetPing(long ctime) { lastPing = ctime; }
	
	
	
	
	@Override
	protected void handleCommand(BaseCommand command, CommType type)
	{
		//We heard from the device, so reset ping
		resetPing();
		
		super.handleCommand(command, type);
	}	

}
