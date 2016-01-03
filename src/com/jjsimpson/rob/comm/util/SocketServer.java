package com.jjsimpson.rob.comm.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.jjsimpson.rob.comm.thread.ICommReader;
import com.jjsimpson.rob.comm.thread.ICommWriter;

public class SocketServer implements ICommClient
{
	protected Socket		socket;
	protected InputStream	inStream;
	protected OutputStream	outStream;
	protected ICommReader	reader;
	protected ICommWriter	writer;	

	public SocketServer(Socket clientSocket)
	{
		socket = clientSocket;
		inStream = null;
		outStream = null;
		if(socket != null) {
			try {
				inStream = socket.getInputStream();
				outStream = socket.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
	}
	
	@Override
	public void setCommThreads(ICommReader reader, ICommWriter writer) {
		this.reader = reader;
		this.writer = writer;
		
		this.reader.setStream(inStream);
		this.writer.setStream(outStream);
	}

	@Override
	public ICommReader getCommReader() { return reader; }

	@Override
	public ICommWriter getCommWriter() { return writer; }
	
	
	@Override
	public void connect() {
		//Don't do anything here
	}

	@Override
	public boolean isConnected() {
		return socket != null && socket.isConnected();
	}

	@Override
	public InputStream getIntputStream() {
		return inStream;
	}

	@Override
	public OutputStream getOutputStream() {
		return outStream;
	}

	@Override
	public void closeSilently() {
		if(inStream != null) {
			try {
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(outStream != null) {
			try {
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		inStream = null;
		outStream = null;
		socket = null;		
	}

}
