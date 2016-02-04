package com.jjsimpson.rob.sensor.value;

public class SensorTripleInt
{
	public byte sensorId;
	public int x;
	public int y;
	public int z;
	
	public SensorTripleInt() {
		init((byte)0, 0, 0, 0);
	}
	
	
	public SensorTripleInt(byte id) {
		init(id, 0, 0, 0);
	}
	
	
	public SensorTripleInt(byte id, int newx, int newy, int newz) {
		init(id, newx, newy, newz);
	}
	
	
	protected void init(byte id, int newx, int newy, int newz)
	{
		sensorId = id;
		x = newx;
		y = newy;
		z = newz;
	}
	
	
	@Override
	public String toString()
	{
		String result = "id: " + Byte.toString(sensorId) + " value: " + Long.toString(x) + "," + Long.toString(y) + "," + Long.toString(z);
		
		return result;
	}
	

}
