package com.jjsimpson.rob.device.profile;

import com.jjsimpson.rob.log.ILogger;

public interface IDeviceProfile
{
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
	//This is specific for IDevice Profile
	
	//Set Logger used
	void setLogger(ILogger logger);
}
