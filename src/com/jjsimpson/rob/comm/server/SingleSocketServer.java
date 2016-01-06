package com.jjsimpson.rob.comm.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.jjsimpson.rob.ai.server.AIServerFactory;
import com.jjsimpson.rob.ai.server.IAIServer;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.comm.util.SocketClient;
import com.jjsimpson.rob.comm.util.SocketServer;

public class SingleSocketServer extends BaseServer
{
	protected ICommClient	commClient;
	
	public SingleSocketServer()
	{
		super();
		commClient = null;
		aiServer = null;
	}
	
	@SuppressWarnings("resource")
	@Override
	protected void waitForConnection()
	{
		try {
			//Create a socket and wait for a connection
			ServerSocket socket = new ServerSocket(SocketClient.DEFAULT_PORT);
			
			logger.debug("Server is waiting for connection");
			Socket client = socket.accept();
			if(client != null)
			{
				logger.debug("Server created commClient");
				commClient = new SocketServer(client);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	@Override
	protected void handleConnection()
	{
		if(commClient != null)
		{
			logger.debug("Server is starting AIServer Factory");
			//need to listen for a handshake command
			AIServerFactory factory = new AIServerFactory(commClient, logger);
			factory.run();
			
			//check if we have a profileId
			logger.debug("Server created new AIServer");
			aiServer = AIServerFactory.createAIServer(factory.getDeviceProfileId(), commClient, logger);
		}		
	}
	
	
}
