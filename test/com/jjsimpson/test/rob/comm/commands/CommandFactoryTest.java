package com.jjsimpson.test.rob.comm.commands;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.commands.CommandFactory;
import com.jjsimpson.rob.comm.commands.init.InitSubCommands;
import com.jjsimpson.rob.comm.model.BasicFrame;
import com.jjsimpson.rob.comm.model.CommType;

public class CommandFactoryTest
{
	@Test(groups = "unit")
	public void frameToCommandTest()
	{
		BaseCommand cmd = new BaseCommand();
		BasicFrame frame = new BasicFrame(cmd);
		
		BaseCommand newCmd = CommandFactory.frameToCommand(frame, cmd.getType());
		Assert.assertEquals(newCmd.getType().getFullType(), 0);
		Assert.assertEquals(newCmd.getType().getMainType(), CommType.TYPE_INIT);
		Assert.assertEquals(newCmd.getType().getSubType(), InitSubCommands.BASE_COMMAND);
	}
}
