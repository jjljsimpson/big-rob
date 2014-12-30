package com.jjsimpson.aaengine.communication;

public class ArduinoResponseStandard
{
	protected byte[] standard_response;
	protected boolean loaded;
	protected boolean valid;
	protected int position;
	
	public ArduinoResponseStandard()
	{
		standard_response = null;	//Add a pointer to this in implementation
		reset();
	}
	
	
	public boolean readByte(byte data)
	{
		//Don't do anything if already loaded
		if(loaded)
			return loaded;
		
		//Take the position and make sure it matches the response
		if(standard_response[position] == data)
		{
			//move to the next position
			position++;
			valid = true;
			if(position >= standard_response.length)
			{
				loaded = true;
			}
		}
		else
		{
			valid = false;
			position = 0;
		}
		
		return loaded;
	}
	
	public boolean isLoaded() { return loaded; }
	public boolean isValid() { return valid; }
	public int size() { return standard_response.length; }
	public void reset() {		
		loaded = false;
		valid = true;
		position = 0;
	}
	
}
