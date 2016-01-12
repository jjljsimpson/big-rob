package com.jjsimpson.rob.utils;

//This allows a class to be called either in a separate thread,
//	or, they can be grouped together in a larger loop.
//  If grouped, you need to call loopInit and loopShutdown directly
public interface ILoopRunner
{
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
}
