package com.jjsimpson.rob.comm.commands.sensorvalues;

import java.nio.ByteBuffer;

import com.jjsimpson.rob.comm.model.CommType;
import com.jjsimpson.rob.sensor.value.SensorTripleInt;

public class SensorTripleIntCommand extends BasicSensorValueCommand
{
	protected int xval;
	protected int yval;
	protected int zval;
	
	public SensorTripleIntCommand() {
		super();
		reset((byte)0, 0, 0, 0);
	}
	
	public SensorTripleIntCommand(byte id, int x, int y, int z) {
		super();
		reset(id, x, y, z);
	}
	
	public SensorTripleIntCommand(SensorTripleInt value)
	{
		super();
		reset(value.sensorId, value.x, value.y, value.z);
	}
	
	
	public SensorTripleInt getSensorTripleInt() {
		return new SensorTripleInt(sensorId, xval, yval, zval);
	}
	
	
	@Override
	public String toString()
	{
		String result = "id: " + sensorId + " value: " + Integer.toString(xval) + "," + Integer.toString(yval) + "," + Integer.toString(zval);
		
		return result;
	}
	
	@Override
	protected void writeData(ByteBuffer buffer)
	{
		super.writeData(buffer);
		buffer.putInt(xval);
		buffer.putInt(yval);
		buffer.putInt(zval);
	}
	
	
	@Override
	protected void readData(ByteBuffer buffer)
	{
		super.readData(buffer);
		xval = buffer.getInt();
		yval = buffer.getInt();
		zval = buffer.getInt();
	}	
	
	
	@Override
	public int getSize()
	{
		return super.getSize() + 4 + 4 + 4;	//x,y,z
	}
	
	
	protected void reset(byte id, int x, int y, int z)
	{
		sensorId = id;
		xval = x;
		yval = y;
		zval = z;
		
		type = new CommType(CommType.TYPE_SENSOR_VALUES, SensorSubCommands.TRIPLE_INT_VALUE);	//Default command
	}

}
