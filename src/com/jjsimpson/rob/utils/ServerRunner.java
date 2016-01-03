package com.jjsimpson.rob.utils;

import com.jjsimpson.rob.ai.server.IAIServer;
import com.jjsimpson.rob.comm.server.BaseServer;
import com.jjsimpson.rob.comm.server.SingleSocketServer;

public class ServerRunner
{
	public static void main(String[] args)
	{
		//This is a communication server. We create it to talk to the client
		BaseServer mainServer = new SingleSocketServer();
		
		//Run the server in this thread (it blocks until finished)
		mainServer.run();
		
		//At this point we have an AI server profile, so run it (in this thread)
		IAIServer aiServer = mainServer.getAiServer();
		if(aiServer != null)
		{
			aiServer.run();
			
			aiServer.close();
		}
		
		//Ok, all done now, so exit
	}
	
	
}
