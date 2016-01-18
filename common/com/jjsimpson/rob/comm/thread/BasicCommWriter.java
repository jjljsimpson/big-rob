package com.jjsimpson.rob.comm.thread;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.model.BasicFrame;

//Note, this class does just straight writing.
// There is no error checking
public class BasicCommWriter extends Thread implements ICommWriter
{
	protected static final int 	MAX_CONTROL = 254;
	protected static final int	MIN_CONTROL = 1;
	
	protected List<BasicFrame>	queue;	//This is thread safe
	protected boolean			isRunning;
	protected boolean			isPaused;
	protected DataOutputStream	stream;
	protected int				nextControl;

	public BasicCommWriter()
	{
		queue = Collections.synchronizedList(new ArrayList<BasicFrame>());
		isRunning = false;
		isPaused = false;
		stream = null;
		nextControl = MIN_CONTROL;
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
		if(stream != null && queue.size() > 0)
		{
			BasicFrame frame = queue.remove(0);
			frame.write(stream, getNextControl());
		}
	}
	
	@Override
	public void pause(boolean doPause)
	{
		isPaused = doPause;
	}

	@Override
	public void setStream(OutputStream strm)
	{
		if(strm != null) {
			stream = new DataOutputStream(strm);
		}
		else {
			stream = null;
		}
		
	}

	@Override
	public void writeFrame(BasicFrame frame)
	{
		queue.add(frame);
	}
	
	@Override
	public void writeCommand(BaseCommand command)
	{
		writeFrame(new BasicFrame(command));
	}

	@Override
	public void sendFeedback(BaseCommand command)
	{
		writeCommand(command);
	}
	
	@Override
	public void finish()
	{
		isRunning = false;
	}
	
	protected int getNextControl()
	{
		int result = nextControl;
		
		nextControl++;
		if(nextControl > MAX_CONTROL){
			nextControl = MIN_CONTROL;
		}
		
		return result;
	}

}
