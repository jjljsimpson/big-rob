package com.jjsimpson.rob.comm.commands.sensorvalues;

import java.nio.ByteBuffer;

import com.jjsimpson.rob.comm.commands.BaseCommand;

public class BasicSensorValueCommand extends BaseCommand
{
	protected byte sensorId;	//ID of the sensor (1 byte)
	
	public BasicSensorValueCommand()
	{
		super();
		sensorId = 0;	
		//NOTE: there is no type, this is not a valid command, but should be extend by other commands
	}
	
	
	public BasicSensorValueCommand(byte id)
	{
		super();
		sensorId = id;
		//NOTE: there is no type, this is not a valid command, but should be extend by other commands
	}
	
	
	public int getSensorId() { return sensorId; }
	public void setSensorId(byte val) { sensorId = val; }
	
	
	@Override
	protected void writeData(ByteBuffer buffer)
	{
		super.writeData(buffer);
		buffer.put(sensorId);			
	}
	
	@Override
	protected void readData(ByteBuffer buffer)
	{
		super.readData(buffer);
		sensorId = buffer.get();
	}
	
	
	@Override
	public int getSize()
	{
		return super.getSize() + 1;	//1 byte for the id
	}

}
