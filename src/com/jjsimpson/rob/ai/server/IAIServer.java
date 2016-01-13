package com.jjsimpson.rob.ai.server;

public interface IAIServer {
		
	//-----
	//This is part of ILoopRunner
	
	//Run the loop (it blocks)
	void run();
	
	//This runs just one loop
	void runOneLoop();
	
	//This is called in run, before the loop starts
	void loopInit();
	
	//This is called in run, after the loop ends
	void loopShutdown();
	
	//pause the loop
	void pauseLoop();
	
	//resume the loop (after a pause)
	void resumeLoop();
	
	//Stop the loop
	void closeLoop();	
		
	//-----
	//This is specific for IAIServer Profile	
	
//	//Run the server
//	void run();
//	
//	//shutdown the server
//	void shutdown();
	
//	//Shut down the communication
//	void close();
	
}
