package com.jjsimpson.aaengine.bluetooth;

import java.io.OutputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.jjsimpson.aaengine.communication.RemoteCommand;

public class BluetoothWriter extends Thread {
	protected static final int MAX_CONTROL = 255;
	protected static final int MIN_CONTROL = 1;

	protected Handler messageHandler;
	protected OutputStream stream;
	protected int control;
	protected boolean doWait;
	protected ArrayList<RemoteCommand> commandList;	
	protected CommunicationInformation	commInfo;

	public BluetoothWriter() {
		control = MAX_CONTROL;
		doWait = false;
		commandList = new ArrayList<RemoteCommand>();
		commInfo = CommunicationInformation.getInstance();
	}

	@SuppressLint("HandlerLeak")
	@Override
	public void run() {
		Looper.prepare();

		messageHandler = new Handler() {

			public void handleMessage(Message msg) {
				// Process the message but in this thread
				processMessage(msg);
			}

		};

		Looper.loop();
	}

	
	public Handler getHandler() {
		return messageHandler;
	}

	public void setStream(OutputStream str) {
		stream = str;
	}	

	protected void processMessage(Message msg) {
		RemoteCommand com = (RemoteCommand)msg.obj;

		// Get JCommand
		if (msg.arg1 == RemoteCommand.COMMAND_DEBUG) {
			// send debug message, but wait for now
			sendDebug(com);
		} else if (msg.arg1 == RemoteCommand.COMMAND_MESSAGE) {
			//Set Control
			if(++control > MAX_CONTROL)
				control = MIN_CONTROL;			
			// Add message to the list
			com.setControl(control);
			commandList.add(com);
			commInfo.addCommand();
			sendCommand();
		} else if (msg.arg1 == RemoteCommand.COMMAND_RECIEPT) {
			processReceipt(com);
		}

	}

	protected void sendCommand()
	{
		//Only process if there is a stream to write to
		if (stream != null)
		{
			//Only send command if doWait is false
			if(!doWait)
			{
				//only if there are some commands
				if(commandList.size() > 0)
				{
					//Get the next command
					RemoteCommand com = commandList.get(0);
										
					//Wait for receipt
					doWait = true;
					
					//Write it to the stream
					com.write(stream);
					commInfo.addSentCommand();
				}
			}			
		}
		
	}

	protected void sendDebug(RemoteCommand com) {
		if (stream != null) {
			com.debug(stream);
		}
	}

	protected void processReceipt(RemoteCommand com)
	{		
		//Only if there is a command to send
		if(commandList.size() > 0)
		{
			//Get the current command
			RemoteCommand rcom = commandList.get(0);
			
			//if receipt is 0, then resend last command
			if(com.getControl() == 0)
			{
				commInfo.addMissedReceipt();				
				doWait = false;
				sendCommand();
			}
			else if(com.getControl() == rcom.getControl())
			{
				//It matched, so remove and send next command
				commInfo.addReceipt();
				doWait = false;
				commandList.remove(0);
				sendCommand();
			}
			else
			{
				//Receipt for an old command, don't do anything
				commInfo.addOldReceipt();
			}
		}		
	}

}
