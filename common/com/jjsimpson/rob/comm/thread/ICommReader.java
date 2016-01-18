package com.jjsimpson.rob.comm.thread;

import java.io.InputStream;

import com.jjsimpson.rob.comm.model.BasicFrame;

public interface ICommReader {
	
	//Pause, or resume the reader
	void pause(boolean doPause);
	
	//Set the stream that we read from
	//You can also use this to reset the stream to a different stream
	void setStream(InputStream stream);
	
	//Get the next queued frame
	BasicFrame getNextFrame();
	
	//set writer so feedback can be sent
	void setWriter(ICommWriter writer);
	
	//Reset the stream and clear the queue
	void reset();
	
	//Finish running the thread and stop
	void finish();
	
	//Use this to start the thread
	void start();
}
