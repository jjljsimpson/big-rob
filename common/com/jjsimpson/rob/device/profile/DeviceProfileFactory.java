package com.jjsimpson.rob.device.profile;

import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.log.ILogger;

public class DeviceProfileFactory
{

	public static IDeviceProfile createDeviceProfile(int profileId, ICommClient comm, ILogger logger)
	{
		IDeviceProfile result = null;
		
		switch(profileId)
		{
			case BaseDeviceProfile.LOGGER_DEVICE_PROFILE:
				result = new LoggerDevice(comm);
				result.setLogger(logger);
				break;
			case BaseDeviceProfile.DISTANCE_DEVICE_PROFILE:
				result = new DistanceDevice(comm);
				result.setLogger(logger);
				break;
			case BaseDeviceProfile.RADAR_DEVICE_PROFILE:
				result = new RadarDevice(comm);
				result.setLogger(logger);
				break;
		}
				
		return result;
	}
}
