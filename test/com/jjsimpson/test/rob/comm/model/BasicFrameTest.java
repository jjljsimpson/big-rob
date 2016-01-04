package com.jjsimpson.test.rob.comm.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.model.BasicFrame;

public class BasicFrameTest
{

	@Test(groups = "unit")
	public void defaultConstructor()
	{
		BasicFrame frame = new BasicFrame();
		
		Assert.assertEquals(frame.getDataSize(), 0);
		Assert.assertEquals(frame.getFullSize(), BasicFrame.MIN_SIZE);
		Assert.assertNull(frame.getData());
	}
	
	
	@Test(groups = "unit")
	public void commandConstructor()
	{
		BaseCommand cmd = new BaseCommand();		
		BasicFrame frame = new BasicFrame(cmd);
		
		Assert.assertEquals(frame.getDataSize(), cmd.getSize());
		Assert.assertEquals(frame.getFullSize(), BasicFrame.MIN_SIZE + cmd.getSize());
		Assert.assertNotNull(frame.getData());
	}
	
	
	@Test(groups = "unit")
	public void resetTest()
	{
		BaseCommand cmd = new BaseCommand();
		BasicFrame frame = new BasicFrame(cmd);
		
		frame.reset();
		
		Assert.assertEquals(frame.getDataSize(), 0);
		Assert.assertEquals(frame.getFullSize(), BasicFrame.MIN_SIZE);
		Assert.assertNull(frame.getData());
	}
	
	@Test(groups = "unit")
	public void readWriteTest()
	{
		int control = 4;
		BaseCommand cmd = new BaseCommand();
		BasicFrame frame = new BasicFrame(cmd);
		
		//Write to byte array
		ByteArrayOutputStream outByteStream = new ByteArrayOutputStream(frame.getFullSize());
		DataOutputStream outStream = new DataOutputStream(outByteStream);				
		frame.write(outStream, control);
		
		Assert.assertEquals(frame.getFullSize(), BasicFrame.MIN_SIZE + cmd.getSize());
		Assert.assertEquals(outStream.size(), frame.getFullSize());
		Assert.assertEquals(frame.getControl(), control);
		
		//Convert to Input Stream
		byte[] dataWritten = outByteStream.toByteArray();
		Assert.assertEquals(frame.getFullSize(), dataWritten.length);
		
		ByteArrayInputStream inByteStream = new ByteArrayInputStream(dataWritten);
		DataInputStream inStream = new DataInputStream(inByteStream);
		
		//Read
		BasicFrame readFrame = new BasicFrame();
		readFrame.read(inStream);
		
		Assert.assertEquals(readFrame.getFullSize(), BasicFrame.MIN_SIZE + cmd.getSize());
		Assert.assertEquals(readFrame.getType().getFullType(), 0);
		
	}
}
