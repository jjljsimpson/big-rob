package com.jjsimpson.aaengine.communication;

public class ArduinoResponseFooter extends ArduinoResponseStandard
{
	protected static final byte[] STANDARD_FOOTER = {'<', '-', '-'};
	
	public ArduinoResponseFooter()
	{
		super();
		standard_response = STANDARD_FOOTER;
	}
	
	
}
