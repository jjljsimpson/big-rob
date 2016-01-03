package com.jjsimpson.rob.comm.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.jjsimpson.rob.comm.thread.ICommReader;
import com.jjsimpson.rob.comm.thread.ICommWriter;

public class SocketClient implements ICommClient
{
	public static final int DEFAULT_PORT = 31975;
	
	protected InetAddress	socketAddress;
	protected int			socketPort;
	
	protected Socket		socket;
	protected InputStream	inStream;
	protected OutputStream	outStream;
	
	protected ICommReader	reader;
	protected ICommWriter	writer;
	
	public SocketClient(InetAddress address)
	{
		init(address, DEFAULT_PORT);
	}
	
	public SocketClient(InetAddress address, int port)
	{
		init(address, port);
	}
	
	protected void init(InetAddress address, int port)
	{
		socketAddress = address;
		socketPort = port;
		socket = null;
		inStream = null;
		outStream = null;		
	}
	
	
	@Override
	public void setCommThreads(ICommReader reader, ICommWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

	@Override
	public ICommReader getCommReader() { return reader; }

	@Override
	public ICommWriter getCommWriter() { return writer; }
	
	
	@Override
	public void connect()
	{
		try {
			socket = new Socket(socketAddress, socketPort);
			
			if(socket != null && socket.isConnected())
			{
				inStream = socket.getInputStream();
				outStream = socket.getOutputStream();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	

	@Override
	public boolean isConnected()
	{
		return socket != null && socket.isConnected();
	}
	

	@Override
	public InputStream getIntputStream()
	{
		return inStream;
	}
	

	@Override
	public OutputStream getOutputStream()
	{
		return outStream;
	}
	

	@Override
	public void closeSilently()
	{
		if(inStream != null) {
			try {
				inStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(outStream != null) {
			try {
				outStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		inStream = null;
		outStream = null;
		socket = null;
	}

}
