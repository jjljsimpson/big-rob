package com.jjsimpson.test.rob.comm.commands.init;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jjsimpson.rob.comm.commands.init.FeedbackCommand;
import com.jjsimpson.rob.comm.commands.init.InitSubCommands;

public class FeedbackCommandTest
{

	@Test(groups = "unit")
	public void constructorTest()
	{
		FeedbackCommand cmd = new FeedbackCommand();		
		Assert.assertEquals(cmd.getType().getSubType(), InitSubCommands.FEEDBACK_SUB_COMMAND);

		cmd = new FeedbackCommand(2);		
		Assert.assertEquals(cmd.getType().getSubType(), InitSubCommands.FEEDBACK_SUB_COMMAND);		
	}
}
