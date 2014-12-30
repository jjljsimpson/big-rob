package com.jjsimpson.aaengine.bluetooth;

import java.io.InputStream;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.jjsimpson.aaengine.communication.ArduinoResponse;
import com.jjsimpson.aaengine.communication.RemoteCommand;


public class BluetoothReader extends Thread
{
	protected static final String INFO_TAG = "AAEngine";
	
	protected InputStream				stream;
	protected BluetoothWriter			blueWriter;
	protected CommunicationInformation	commInfo;
	
	public BluetoothReader()
	{
		stream = null;
		blueWriter = null;
		commInfo = CommunicationInformation.getInstance();
	}
		
	public void setStream(InputStream str)
	{
		stream = str;
	}
		
	public void setWriter(BluetoothWriter writer)
	{
		blueWriter = writer;
	}
	
	
	@Override
    public void run()
	{
		ArduinoResponse currentResponse = null;
		
		while(!Thread.interrupted())
		{
			//If there is a stream then read
			if(stream != null)
			{
				//If no response then create it
				if(currentResponse == null)
				{
					currentResponse = new ArduinoResponse();
				}
					
				//Read the response
				currentResponse.read(stream);
				
				//If done with this response, then handle the response
				if(currentResponse.isLoaded())
				{
					//Process the response
					processResponse(currentResponse);
					
					//Done with this response
					currentResponse = null;
				}				
			}
			
		}

    }
	
	
	protected void processResponse(ArduinoResponse currentResponse)
	{
		if(currentResponse != null)
		{
			if(currentResponse.getResponseType() == ArduinoResponse.RESPONSE_STRING)
			{
				//Just log the string
				Log.i(INFO_TAG, currentResponse.getResponseString());
			}
			else if(currentResponse.getResponseType() == ArduinoResponse.RESPONSE_RECEIPT)
			{
				//Get the receipt and send to writer
				if(blueWriter != null)
				{
					Handler blueHandler = blueWriter.getHandler();
					if(blueHandler != null)
					{
						RemoteCommand com = RemoteCommand.getReceipt(currentResponse.getResponseReceipt());
						
						//Get message
						Message msg = blueHandler.obtainMessage();
						
						//Set the message
						msg.arg1 = RemoteCommand.COMMAND_RECIEPT;
						msg.obj = com;
						
						//Send the message
						blueHandler.sendMessage(msg);						
					}
				}
			}
		}
	}
	
	
		
}
