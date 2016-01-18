package com.jjsimpson.rob.comm.thread;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jjsimpson.rob.comm.commands.init.FeedbackCommand;
import com.jjsimpson.rob.comm.model.BasicFrame;

public class BasicCommReader extends Thread implements ICommReader
{
	protected List<BasicFrame>	queue;	//This is thread safe
	protected ICommWriter		feedbackWriter;
	protected boolean			isRunning;
	protected boolean			isPaused;
	protected DataInputStream	stream;
	protected BasicFrame		current;
	
	public BasicCommReader()
	{
		queue = Collections.synchronizedList(new ArrayList<BasicFrame>());
		feedbackWriter = null;
		isRunning = false;
		isPaused = false;
		stream = null;
		current = null;
	}
	
	@Override
	public void run()
	{
		isRunning = true;
		isPaused = false;
		
		while(isRunning)
		{
			if(!isPaused)
			{
				loopLogic();
			}			
		}
	}
	
	protected void loopLogic()
	{
		if(current == null) {
			current = new BasicFrame();
		}
		
		//Read some data
		current.read(stream);
		
		if(current.isFinished())
		{
			if(current.isValid()) {
				queue.add(current);		//Add the message to the queue
//				if(feedbackWriter != null) {
//					feedbackWriter.sendFeedback(new FeedbackCommand(current.getControl()));	//send feedback that we got a message
//				}
			}
//			else {
//				feedbackWriter.sendFeedback(new FeedbackCommand(FeedbackCommand.INVALID_CONTROL));	//Got something invalid, so send feedback
//			}
			
			current = null;
		}
	}
	
	@Override
	public void pause(boolean doPause)
	{
		isPaused = doPause;
	}
	
	
	@Override
	public void finish()
	{
		isRunning = false;
	}
	

	@Override
	public synchronized void setStream(InputStream inStream)
	{
		if(inStream != null) {
			stream = new DataInputStream(inStream); }
		else {
			stream = null;
		}
	}

	@Override
	public BasicFrame getNextFrame()
	{
		BasicFrame result = null;
		
		if(queue.size() > 0)
		{
			return queue.remove(0);
		}
		
		return result;
	}

	@Override
	public void setWriter(ICommWriter writer)
	{
		feedbackWriter = writer;
	}
	
	@Override
	public void reset()
	{
		//TODO make this thread safe
		queue.clear();
		stream = null;
		current = null;
	}

}