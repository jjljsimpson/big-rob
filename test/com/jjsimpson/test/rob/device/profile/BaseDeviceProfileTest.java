package com.jjsimpson.test.rob.device.profile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jjsimpson.mock.rob.comm.util.MockSocket;
import com.jjsimpson.mock.rob.comm.util.MockSocketClient;
import com.jjsimpson.mock.rob.device.profile.MockBaseDeviceProfile;
import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.device.profile.BaseDeviceProfile;
import com.jjsimpson.rob.log.ConsoleLogger;
import com.jjsimpson.rob.log.ILogger;

public class BaseDeviceProfileTest
{
	protected BaseDeviceProfileTest profile;
	

	@Test(groups="unit")	
	public void testEmptyProfile()
	{
		//Create the Logger
		ILogger logger = getLogger();
		
		//Create Input/Output streams
		MockSocket socket = getSocket();
		
		//Create the comm client
		ICommClient commClient = getClient(socket);
		
		//Create the profile
		BaseDeviceProfile profile = getProfile(logger, commClient);
				
		//Now Run the profile
		profile.run();
		
		//We shouldn't have read anything
		Assert.assertEquals(socket.getInputReadSize(), 0);
		
		//We should have written a handshake (17 bytes)
		byte[] outData = socket.getOutputData();
		Assert.assertEquals(outData.length, 17);
	}
			
	
	protected ILogger getLogger()
	{
		return new ConsoleLogger();
	}
	
	
	protected MockSocket getSocket()
	{
		MockSocket result = new MockSocket();
		
		byte[] inData = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		ByteArrayInputStream inStream = new ByteArrayInputStream(inData);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		result.setStreams(inStream, outStream);
		
		return result;
	}
	
	protected ICommClient getClient(MockSocket socket)
	{
		ICommClient result = null;
		
		MockSocketClient mockSock = new MockSocketClient();
		mockSock.setSocket(socket);
		result = mockSock;
		
		return result;
	}	
	
	
	protected BaseDeviceProfile getProfile(ILogger logger, ICommClient comm)
	{
		BaseDeviceProfile result = new MockBaseDeviceProfile(comm);
		result.setLogger(logger);
		
		return result;
	}
		
	
}
