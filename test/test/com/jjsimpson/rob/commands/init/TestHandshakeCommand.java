package test.com.jjsimpson.rob.commands.init;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jjsimpson.rob.comm.commands.init.HandshakeCommand;
import com.jjsimpson.rob.comm.commands.init.InitSubCommands;
import com.jjsimpson.rob.comm.model.BasicFrame;
import com.jjsimpson.rob.comm.model.CommType;

public class TestHandshakeCommand
{

	@Test(groups = "unit")
	public void testHanshake()
	{
		int	profileId = 4;
		HandshakeCommand cmd = new HandshakeCommand(profileId);
		
		byte[] data = cmd.writeToByte();
					
		Assert.assertEquals(cmd.getSize(), data.length);
		Assert.assertEquals(cmd.getProfileId(), profileId);
		Assert.assertEquals(cmd.getType().getMainType(), CommType.TYPE_INIT);
		Assert.assertEquals(cmd.getType().getSubType(), InitSubCommands.HANDSHAKE_SUB_COMMAND);
		
		//Now read
		cmd = new HandshakeCommand();
		cmd.readFromByte(data);
		
		Assert.assertEquals(cmd.getSize(), data.length);
		Assert.assertEquals(cmd.getProfileId(), profileId);		
		Assert.assertEquals(cmd.getType().getMainType(), CommType.TYPE_INIT);
		Assert.assertEquals(cmd.getType().getSubType(), InitSubCommands.HANDSHAKE_SUB_COMMAND);		
	}
	
	
	@Test(groups = "unit")
	public void testFrame()
	{
		int profileId = 4;
		HandshakeCommand cmd = new HandshakeCommand(profileId);
		
		BasicFrame outFrame = new BasicFrame(cmd);
		
		//Write to a stream
		ByteArrayOutputStream	outStream = new ByteArrayOutputStream();
		DataOutputStream		doutStream = new DataOutputStream(outStream);
		outFrame.write(doutStream);
		
		//Get the data as a byte array
		byte[] outByteData = outStream.toByteArray();
		Assert.assertNotNull(outByteData);
		Assert.assertEquals(outByteData.length, 17);	//Frame = 14 + 3 = 17
		
		//Now put it int a stream to write
		ByteArrayInputStream	inStream = new ByteArrayInputStream(outByteData);
		DataInputStream			dinStream = new DataInputStream(inStream);
		
		BasicFrame inFrame = new BasicFrame();
		inFrame.read(dinStream);
		byte[] inByteData = inFrame.getData();
		
		Assert.assertNotNull(outByteData);
		Assert.assertEquals(outByteData.length, 3);	//command = 3 bytes		
	}
	
}
