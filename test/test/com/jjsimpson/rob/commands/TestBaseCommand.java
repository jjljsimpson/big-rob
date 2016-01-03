package test.com.jjsimpson.rob.commands;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jjsimpson.rob.comm.commands.BaseCommand;

public class TestBaseCommand
{

	@BeforeClass (groups = {"unit"})
	public void init()
	{
		
	}
	
	@Test(groups = "unit")
	public void checkWrite()
	{
		BaseCommand cmd = new BaseCommand();
		
		byte[] data = cmd.writeToByte();
	
		Assert.assertEquals(data.length, 2);
		Assert.assertEquals(data[0], 0);
		Assert.assertEquals(data[1], 0);
	}
	
	
}