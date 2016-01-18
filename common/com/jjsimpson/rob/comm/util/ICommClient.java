package com.jjsimpson.rob.comm.util;

import java.io.InputStream;
import java.io.OutputStream;

import com.jjsimpson.rob.comm.thread.ICommReader;
import com.jjsimpson.rob.comm.thread.ICommWriter;

public interface ICommClient
{
	//Attempt to connect to a server
	void connect();
	
	//Find out if client has connected yet
	boolean isConnected();
	
	//Once it is connected, get the input stream
	InputStream getIntputStream();
	
	//Once it is connected get the output stream
	OutputStream getOutputStream();
	
	//Close the connection silently
	void closeSilently();
	
	//If we created threads, save them so we can reuse them
	void setCommThreads(ICommReader reader, ICommWriter writer);
	
	ICommReader getCommReader();
	
	ICommWriter getCommWriter();
}
