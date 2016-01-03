package com.jjsimpson.rob.comm.thread;

import java.io.OutputStream;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.model.BasicFrame;

public interface ICommWriter
{
	//Pause writing to the stream
	void pause(boolean doPause);
	
	//Stream we are writing to. It can be used to reset the stream
	void setStream(OutputStream stream);
	
	//Adds a frame to the queue to be written
	void writeFrame(BasicFrame frame);
	
	//Adds a command (as a frame) to the queue to be written
	void writeCommand(BaseCommand command);
	
	//Send feedback that the frame was received
	void sendFeedback(BaseCommand command);
	
	//Finish the loop
	void finish();
	
	//Used this to start a thread
	void start();
}
