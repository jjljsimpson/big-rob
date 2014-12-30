package com.jjsimpson.aaengine.communication;

import java.io.IOException;
import java.io.InputStream;


public class ArduinoResponse
{
	protected enum ReadMode { header, body, complete };

	protected static final int BUFFER_SIZE = 1024;	//1k buffer
	
	public		static final byte RESPONSE_STRING = 's';
	public		static final byte RESPONSE_RECEIPT = 'r';	
	
	//This is used to read the response
	protected ArduinoResponseHeader header;
	protected ArduinoResponseFooter footer;
	protected byte responseType;
	protected byte[] buffer;
	protected int bufferPosition;
	protected ReadMode mode;
	
	public ArduinoResponse()
	{
		header = new ArduinoResponseHeader();
		footer = new ArduinoResponseFooter();
		responseType = 0;
		buffer = new byte[BUFFER_SIZE];	//create buffer to read. This will fail if response is bigger than buffer size
		bufferPosition = 0;
		mode = ReadMode.header;
	}
	
	
	/*
	 * This reads from the stream until one of the following happens.
	 * 1. there are no bytes to read
	 * 2. We finish reading the response
	 */
	public void read(InputStream stream)
	{		
		//Read a response
		if(stream != null)
		{
			//Loop through and read
			try 
			{
				byte data = 0;
				while(stream.available() > 0 && mode != ReadMode.complete)
				{
					//Get a byte from the stream
					data = (byte)stream.read();
					if(mode == ReadMode.header) {
						readHeader(data);
					}
					else if(mode == ReadMode.body) {
						readBody(data);
					}
				}
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	
	
	public boolean isLoaded()
	{
		return mode == ReadMode.complete;
	}
	
	
	public byte getResponseType()
	{
		return responseType;
	}
	
	
	public String getResponseString()
	{
		String result = "";
		
		if(isLoaded() && responseType == RESPONSE_STRING)
		{
			result = new String(buffer, 0, bufferPosition-(1+footer.size()));
		}
		
		return result;
	}
	
	public int getResponseReceipt()
	{
		int result = 0;
		
		if(isLoaded() && responseType == RESPONSE_RECEIPT)
		{
			result = (int)buffer[0];
		}
		
		return result;	
	}
	

	protected void readHeader(byte data)
	{
		//Have header read. If true, then it finished reading
		if(header.readByte(data))
			mode = ReadMode.body;
	}
	
	
	protected void readBody(byte data)
	{
		//Set Response Type
		if(responseType == 0)
		{
			if(data == RESPONSE_STRING) {
				responseType = RESPONSE_STRING;
			}
			else if(data == RESPONSE_RECEIPT) {
				responseType = RESPONSE_RECEIPT;
			}
			else {
				mode = ReadMode.header;	//There is an error, start over
				header.reset();
			}
		}
		else
		{
			//Add it to the buffer
			buffer[bufferPosition++] = data;
			
			//Check if we have read the footer
			if(footer.readByte(data))
				mode = ReadMode.complete;
			
			//Check if we ran out of buffer (ignore that response)
			if(bufferPosition >= BUFFER_SIZE)
			{
				mode = ReadMode.header;
				header.reset();
			}
		}
	}
			
	
		
	
	
}
