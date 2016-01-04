package com.jjsimpson.test.rob.comm.commands.init;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jjsimpson.rob.comm.commands.init.InitSubCommands;
import com.jjsimpson.rob.comm.commands.init.PingCommand;

public class PingCommandTest
{

	
	@Test(groups = "unit")
	public void constructorTest()
	{
		PingCommand cmd = new PingCommand();		
		Assert.assertEquals(cmd.getType().getSubType(), InitSubCommands.PING_SUB_COMMAND);
	}	
	
	
}
