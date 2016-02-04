package com.jjsimpson.rob.utils.loop;


public class ThreadLoopRunner extends Thread
{
	protected ILoopRunner loopRunner;
	
	public ThreadLoopRunner(ILoopRunner loop)
	{
		loopRunner = loop;
	}
	
	public void run()
	{
		loopRunner.run();
	}
		
	//pause the loop
	public void pauseLoop() { loopRunner.pauseLoop(); }
	
	//resume the loop (after a pause)
	public void resumeLoop() { loopRunner.resumeLoop(); }
	
	//Stop the loop
	public void closeLoop() { loopRunner.closeLoop(); }
	
}
