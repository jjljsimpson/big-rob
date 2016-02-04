package com.jjsimpson.rob.comm.commands.sensorvalues;

import java.nio.ByteBuffer;

import com.jjsimpson.rob.comm.model.CommType;
import com.jjsimpson.rob.sensor.value.SensorLong;

public class SensorLongCommand extends BasicSensorValueCommand
{
	protected long value;	//Value is a long = 8 bytes
	
	public SensorLongCommand() {
		super();
		reset((byte)0, 0L);
	}
	
	public SensorLongCommand(byte id, long val) {
		super();
		reset(id, val);
	}
	
	
	public SensorLongCommand(SensorLong val) {
		super();
		reset(val.sensorId, val.value);
	}
	
	
	public long getValue() { return value; }	
	
	
	@Override
	protected void writeData(ByteBuffer buffer)
	{
		super.writeData(buffer);
		buffer.putLong(value);
	}
	
	@Override
	protected void readData(ByteBuffer buffer)
	{
		super.readData(buffer);
		value = buffer.getLong();
	}	
	
	
	@Override
	public int getSize()
	{
		return super.getSize() + 8;
	}
	
	
	public SensorLong getSensorLong()
	{
		return new SensorLong(this.sensorId, value);
	}
	
	
	protected void reset(byte id, long val)
	{
		sensorId = id;
		value = val;
		type = new CommType(CommType.TYPE_SENSOR_VALUES, SensorSubCommands.LONG_VALUE);	//Default command
	}
	
}
