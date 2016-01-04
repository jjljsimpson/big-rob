package com.jjsimpson.test.rob.comm.commands.init;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jjsimpson.rob.comm.commands.init.InitSubCommands;
import com.jjsimpson.rob.comm.commands.init.LogCommand;

public class LogCommandTest
{

	@Test(groups = "unit")
	public void constructorTest()
	{
		String msg = "some message";
		
		LogCommand cmd = new LogCommand();		
		Assert.assertEquals(cmd.getType().getSubType(), InitSubCommands.LOG_SUB_COMMAND);

		cmd = new LogCommand(msg);		
		Assert.assertEquals(cmd.getType().getSubType(), InitSubCommands.LOG_SUB_COMMAND);
		Assert.assertEquals(cmd.getMessage(), msg);
	}	
	
}
