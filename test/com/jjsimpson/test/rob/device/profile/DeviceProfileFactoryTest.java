package com.jjsimpson.test.rob.device.profile;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jjsimpson.rob.device.profile.BaseDeviceProfile;
import com.jjsimpson.rob.device.profile.DeviceProfileFactory;
import com.jjsimpson.rob.device.profile.IDeviceProfile;

public class DeviceProfileFactoryTest
{
	
	@Test(groups="unit")
	public void basicTest()
	{
		IDeviceProfile profile = DeviceProfileFactory.createDeviceProfile(BaseDeviceProfile.LOGGER_DEVICE_PROFILE, null, null);
		
		Assert.assertNotNull(profile);
	}

}
