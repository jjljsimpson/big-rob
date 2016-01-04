package com.jjsimpson.test.rob.comm.commands.init;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jjsimpson.rob.comm.commands.init.HandshakeCommand;
import com.jjsimpson.rob.comm.commands.init.InitSubCommands;

public class HandshakeCommandTest
{

	@Test(groups = "unit")
	public void constructorTest()
	{
		int profileId = 2;
		
		HandshakeCommand cmd = new HandshakeCommand();		
		Assert.assertEquals(cmd.getType().getSubType(), InitSubCommands.HANDSHAKE_SUB_COMMAND);

		cmd = new HandshakeCommand(profileId);		
		Assert.assertEquals(cmd.getType().getSubType(), InitSubCommands.HANDSHAKE_SUB_COMMAND);
		Assert.assertEquals(cmd.getProfileId(), profileId);
	}
	
	
}
