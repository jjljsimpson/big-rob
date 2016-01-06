package com.jjsimpson.mock.rob.comm.util;

import java.net.InetAddress;
import java.net.Socket;

import com.jjsimpson.rob.comm.util.SocketClient;

public class MockSocketClient extends SocketClient
{
	protected MockSocket mockSocket;

	public MockSocketClient(InetAddress address) {
		super(address);
		mockSocket = null;
	}
	
	public MockSocketClient()
	{
		super(null);
		mockSocket = null;
	}
	
	
	@Override
	protected Socket getSocket()
	{
		return mockSocket;
	}
	
	
	public void setSocket(MockSocket socket)
	{
		mockSocket = socket;
	}

}
