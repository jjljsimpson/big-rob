package com.jjsimpson.rob.sensor.value;

public class SensorLong
{
	public byte sensorId;
	public long value;
	
	public SensorLong()
	{
		sensorId = 0;
		value = 0;
	}
	
	public SensorLong(byte id, long val) {
		sensorId = id;
		value = val;
	}
	
	
	@Override
	public String toString()
	{
		String result = "id: " + Byte.toString(sensorId) + " value: " + Long.toString(value);
		
		return result;
	}
		
}
