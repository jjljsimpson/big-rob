package com.jjsimpson.rob.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.comm.util.SocketClient;
import com.jjsimpson.rob.device.profile.BaseDeviceProfile;
import com.jjsimpson.rob.device.profile.DeviceProfileFactory;
import com.jjsimpson.rob.device.profile.IDeviceProfile;
import com.jjsimpson.rob.log.ConsoleLogger;
import com.jjsimpson.rob.log.ILogger;

public class DeviceRunner
{
	public static void main(String[] args)
	{
		int			profileId;
		ICommClient commClient = null;
		ILogger		logger = null;
		
		//Get device id
		profileId = (args.length > 0) ? getProfileId(args[0]) : getProfileId(null);
				
		commClient = (args.length > 1) ? getCommClient(args[1]) : getCommClient("net:10.4.246.132");
				
		logger = new ConsoleLogger("{DEVICE} ");
		
		//Run profile
		runProfile(profileId, commClient, logger);
	}
	
	
	public static void runProfile(int id, ICommClient clientComm, ILogger logger)
	{
		//Create Profile from factory
		IDeviceProfile deviceProfile = DeviceProfileFactory.createDeviceProfile(id, clientComm, logger);
		
		//Now start running it
		deviceProfile.run();
	}
	
	
	protected static int getProfileId(String stringId)
	{
		int result = BaseDeviceProfile.LOGGER_DEVICE_PROFILE;
	
		if(stringId != null)
		{
			result = Integer.parseInt(stringId);
		}
		
		return result;
	}
	
	
	protected static byte[] getNetworkAddress(String stringAddress)
	{
		byte[] result = null;
		
		if(stringAddress != null)
		{
			String[] strAddress = stringAddress.split("\\.");
			
			result = new byte[strAddress.length];
			for(int i=0; i<strAddress.length; i++)
			{
				result[i] = (byte)Integer.parseInt(strAddress[i]);
			}
		}
		
		return result;
	}
	
	
	protected static ICommClient getCommClient(String clientInfo)
	{
		ICommClient result = null;
		
		if(clientInfo != null)
		{
			String[] args = clientInfo.split(":");
			
			if(args[0].equals("net"))
			{
				try {
					byte[] add = getNetworkAddress(args[1]);
					result = new SocketClient(InetAddress.getByAddress(add));
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
}
