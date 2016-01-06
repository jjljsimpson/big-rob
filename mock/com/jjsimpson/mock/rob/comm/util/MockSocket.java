package com.jjsimpson.mock.rob.comm.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MockSocket extends Socket
{
	protected ByteArrayInputStream byteInputStream;
	protected ByteArrayOutputStream byteOutputStream;
	protected int startSize;
	
	public MockSocket()
	{
		byteInputStream = null;
		byteOutputStream = null;
		startSize = 0;
	}
	
	public MockSocket(ByteArrayInputStream in, ByteArrayOutputStream out)
	{
		byteInputStream = in;
		byteOutputStream = out;		
	}
	
	public void setStreams(ByteArrayInputStream in, ByteArrayOutputStream out)
	{
		byteInputStream = in;
		byteOutputStream = out;		
	}
	
	
	protected void getStartSize()
	{
		startSize = 0;
		if(byteInputStream != null) {
			startSize = byteInputStream.available();
		}
	}
	
	@Override
	public boolean isConnected()
	{
		return true;
	}
	
	@Override
	public InputStream getInputStream() {
		return byteInputStream;
	}
	
	@Override
	public OutputStream getOutputStream() {
		return byteOutputStream;
	}
	
	@Override
	public void close()
	{
		//don't do anything.
	}
	
	public int getInputReadSize()
	{
		int result = 0;
		
		if(byteInputStream != null)
		{
			result = startSize - byteInputStream.available();
		}
		
		return result;
	}
	
	public byte[] getOutputData()
	{
		byte[] result = null;
		
		if(byteOutputStream != null) {
			result = byteOutputStream.toByteArray();
		}
		
		return result;
	}
	
}
