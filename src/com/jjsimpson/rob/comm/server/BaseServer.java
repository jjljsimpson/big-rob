package com.jjsimpson.rob.comm.server;

import com.jjsimpson.rob.ai.server.IAIServer;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.log.ConsoleLogger;
import com.jjsimpson.rob.log.ILogger;

//The server is a simple construct.
// It waits for a connection. When it gets one
// it creates an AI profile and uses it to run the device.
//		This particular server is not threaded, but it could be
public class BaseServer implements Runnable
{
	protected ICommClient	commClient;
	protected ILogger		logger;
	protected IAIServer		aiServer;
	
	public BaseServer()
	{
		commClient = null;
		logger = new ConsoleLogger("{Server} ");
		aiServer = null;
	}
	
	
	public void setLogger(ILogger log) { logger = log; }
	
	public IAIServer getAiServer() { return aiServer; }
	
	@Override
	public void run()
	{
		//Wait until we have a connection
		waitForConnection();
		
		//Now we have a connection, do something
		handleConnection();		
	}
	

	protected void waitForConnection()
	{

	}
	
	
	protected void handleConnection()
	{
		
	}
			
}
