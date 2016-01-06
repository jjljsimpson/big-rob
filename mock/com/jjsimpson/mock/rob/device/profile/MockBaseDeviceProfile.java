package com.jjsimpson.mock.rob.device.profile;

import com.jjsimpson.rob.comm.util.ICommClient;
import com.jjsimpson.rob.device.profile.BaseDeviceProfile;

public class MockBaseDeviceProfile extends BaseDeviceProfile
{
	protected int counter;
	
	public MockBaseDeviceProfile(ICommClient client) {
		super(client);
		counter = 4;	//Run the loop logic 4 times
	}
	
	@Override
	protected void loopLogic()
	{
		counter--;
		
		if(counter <= 0) {
			//stop running when the counter reaches 0
			isRunning = false;
		}
		
		super.loopLogic();
	}

}
