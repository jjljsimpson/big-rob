package com.jjsimpson.test.rob.comm.commands;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.commands.init.InitSubCommands;
import com.jjsimpson.rob.comm.model.CommType;

public class BaseCommandTest
{
	@Test(groups = "unit")
	public void constructorTest()
	{
		BaseCommand cmd = new BaseCommand();
		
		Assert.assertEquals(cmd.getType().getFullType(), 0);
		Assert.assertEquals(cmd.getType().getMainType(), CommType.TYPE_INIT);
		Assert.assertEquals(cmd.getType().getSubType(), InitSubCommands.BASE_COMMAND);
	}
	
	
	@Test(groups = "unit")
	public void writeTest()
	{
		BaseCommand cmd = new BaseCommand();		
		byte[] data = cmd.writeToByte();
		
		Assert.assertEquals(data.length, cmd.getSize());
	}
	
	
	@Test(groups = "unit")
	public void readTest()
	{
		BaseCommand cmd = new BaseCommand();
		byte[] data = {0, 0};
		
		cmd.readFromByte(data);
		
		Assert.assertEquals(cmd.getType().getFullType(), 0);
		Assert.assertEquals(cmd.getType().getMainType(), CommType.TYPE_INIT);
		Assert.assertEquals(cmd.getType().getSubType(), InitSubCommands.BASE_COMMAND);
	}
	
}
