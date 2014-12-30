package com.jjsimpson.aaengine.arduino;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import com.jjsimpson.aaengine.controller.BaseController;

//This represents a pin on the Arduino
public class Pin
{
	//Size = 6 bytes
	public int		pinId;		//Read as 1 byte
	public int		pinType;	//Read as 1 byte
	public int		pinValue;	//Read as 4 bytes
	
	public BaseController	controller;	//Pointer to controller for this pin
	
	public Pin()
	{
		pinId = pinType = 0;
		pinValue = 0;
		controller = null;
	}
	
	
	public Pin(int id, int type, int value, BaseController control)
	{
		pinId = id;
		pinType = type;
		pinValue = value;
		controller = control;
	}
	
	
	public void read(byte[] buffer)
	{
		ByteArrayInputStream inStream = new ByteArrayInputStream(buffer);
		DataInputStream stream = new DataInputStream(inStream);
		
		try
		{
			pinId = stream.readUnsignedByte();
			pinType = stream.readUnsignedByte();
			pinValue = stream.readInt();			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
}
