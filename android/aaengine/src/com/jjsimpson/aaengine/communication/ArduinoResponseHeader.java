package com.jjsimpson.aaengine.communication;

public class ArduinoResponseHeader extends ArduinoResponseStandard
{
	protected static final byte[] STANDARD_HEADER = {'J','R', 'E', 'S'};
	
	
	public ArduinoResponseHeader()
	{
		super();
		
		standard_response = STANDARD_HEADER;
	}
	
	
	
	
}
