package com.jjsimpson.rob.device.profile;

import com.jjsimpson.rob.log.ILogger;

public interface IDeviceProfile
{
	//Tell the device to stop running
	void shutdownDevice();
	
	//Called by thread to run.
	void run();
	
	//Set Logger used
	void setLogger(ILogger logger);
}
