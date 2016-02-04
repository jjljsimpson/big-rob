package com.jjsimpson.rob.utils.loop;


public class BaseLoopRunner implements ILoopRunner, Runnable
{
	protected boolean	isRunning;
	protected boolean	isPaused;
	
	public BaseLoopRunner()
	{
		isRunning = false;
		isPaused = false;
	}
	
	@Override
	public void run() {
		loopInit();
		
		while(isRunning)
		{
			runOneLoop();
		}
		
		loopShutdown();		
	}
	

	@Override
	public void runOneLoop() {

		if(isRunning && !isPaused)
		{
			//Run the loop logic
			loopLogic();
		}		
	}
	
	
	protected void loopLogic()
	{
		
	}
	

	@Override
	public void loopInit() {
		isRunning = true;
		isPaused = false;
	}

	@Override
	public void loopShutdown() {
		isRunning = false;
		isPaused = false;
	}

	@Override
	public void pauseLoop() {
		isPaused = true;		
	}

	@Override
	public void resumeLoop() {
		isPaused = false;
	}

	@Override
	public void closeLoop() {
		isRunning = false;
	}

}
