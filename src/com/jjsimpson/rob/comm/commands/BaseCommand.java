package com.jjsimpson.rob.comm.commands;

import java.nio.ByteBuffer;

import com.jjsimpson.rob.comm.model.CommType;

public class BaseCommand
{
	protected CommType type;
	
	
	public BaseCommand()
	{
		//Set comm Type
		type = new CommType(0);	//Default command
	}
	
	
	public byte[] writeToByte()
	{
		byte[] result = null;
		
		//Create buffer
		int size = getSize();
		
		if(size > 0) {
			ByteBuffer buffer = ByteBuffer.allocate(size);
			writeData(buffer);
			
			result = buffer.array();
		}
		
		return result;
	}
	
	public CommType getType() { return type; }	
	
	//Override this to write data
	protected void writeData(ByteBuffer buffer)
	{
		buffer.putShort((short)type.getFullType());
	}
	
	
	public void readFromByte(byte[] data)
	{
		if(data != null)
		{
			ByteBuffer buffer = ByteBuffer.wrap(data);
			
			//Do the actual reading
			readData(buffer);
		}
	}
	
	//Override this to read data
	protected void readData(ByteBuffer buffer)
	{
		//Read type
		type = new CommType(buffer.getShort());
	}
	
	
	//Override this to set size for writing data
	protected int getSize()
	{
		return 2;	//size for type
	}
	
	
	
}
