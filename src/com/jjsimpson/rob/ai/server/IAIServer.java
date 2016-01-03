package com.jjsimpson.rob.ai.server;

public interface IAIServer {
		
	//Run the server
	void run();
	
	//shutdown the server
	void shutdown();
	
	//Shut down the communication
	void close();
	
}
