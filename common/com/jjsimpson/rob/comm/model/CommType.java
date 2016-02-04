package com.jjsimpson.rob.comm.model;


/*
 * This is a CommType. It stores the type of command in 2 bytes.
 * First byte is the main type, second byte is the sub type
 */
public class CommType
{

	public static final int	TYPE_INIT = 0;		//Main types of communication frames
	public static final int TYPE_SENSOR_VALUES = 1;		//Sensor value commands
	
	//First byte is main type
	//Second byte is sub type	
	protected int mainType;
	protected int subType;
	
	public CommType(int fullType)
	{
		subType = fullType & 0xFF;
		mainType = fullType >> 8;
	}
	
	public CommType(int mainType, int subType)
	{
		this.mainType = mainType;
		this.subType = subType;
		subType = subType & 0xFF;
	}
	
	
	public int getMainType() { return mainType; }		
	public int getSubType() { return subType; }
	public int getFullType()
	{
		int result = mainType << 8;
		result = result | subType;
		
		return result;
	}
	
	
	public String toString()
	{
		String result = "[CommType] " + Integer.toString(mainType) + ":" + Integer.toString(subType);
		
		return result;
	}
	
}
