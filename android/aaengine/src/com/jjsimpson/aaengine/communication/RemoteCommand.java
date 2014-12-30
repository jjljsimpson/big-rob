package com.jjsimpson.aaengine.communication;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RemoteCommand {
	public static final int COMMAND_MESSAGE = 1975; // Used to identify the message type
	public static final int COMMAND_DEBUG = 1976; // Used for debug
	public static final int COMMAND_RECIEPT = 1977;
	public static final char CURRENT_VERSION = '0';

	// Size is 12 bytes
	protected byte[] header = { 'J', 'C', 'O', 'M' };	//4 bytes
	protected byte control;		// 1 byte
	protected byte pin;			// 1 byte
	protected byte command;		// 1 byte
	protected int value;		// 4 bytes
	protected byte receipt;		// 1 byte

	protected String debugMessage;

	public RemoteCommand(int com, int pinId, int val) {
		pin = (byte) pinId;
		command = (byte) com;
		value = val;
		debugMessage = null;
	}

	public RemoteCommand(String message) {
		pin = 0;
		command = 0;
		value = 0;
		debugMessage = message;
	}
	
	public RemoteCommand(int cont)
	{
		pin = 0;
		command = 0;
		value = 0;
		debugMessage = null;
		control = (byte)cont;
		receipt = control;
	}
	
	public void setControl(int cont)
	{
		control = (byte) cont;
		receipt = control;
	}
	
	public byte getControl()
	{
		return control;
	}
	
	public void write(OutputStream stream) {
		// Convert to data output stream
		DataOutputStream dstream = new DataOutputStream(stream);

		// Now write everything
		try {
			dstream.writeByte(header[0]); // write header
			dstream.writeByte(header[1]);
			dstream.writeByte(header[2]);
			dstream.writeByte(header[3]);
			dstream.writeByte(control);
			dstream.writeByte(pin);
			dstream.writeByte(command); // write command
			dstream.writeInt(value); // write value
			dstream.writeByte(receipt);	//write receipt			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void debug(OutputStream stream) {
		DataOutputStream dstream = new DataOutputStream(stream);

		try {
			if (debugMessage != null)
				dstream.writeUTF(debugMessage);
			else
				dstream.writeUTF("NO MESSAGE");			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static RemoteCommand setPinType(int pinId, int type) {
		return new RemoteCommand(CommandType.COMMAND_SET_PIN_TYPE, pinId, type);
	}

	public static RemoteCommand setPinValue(int pinId, int val) {
		return new RemoteCommand(CommandType.COMMAND_SET_PIN_VALUE, pinId, val);
	}

	public static RemoteCommand pause(int milliseconds) {
		return new RemoteCommand(CommandType.COMMAND_WAIT, 0, milliseconds);
	}

	public static RemoteCommand getPinValue(int pinId) {
		return new RemoteCommand(CommandType.COMMAND_GET_PIN_VALUE, pinId, 0);
	}

	public static RemoteCommand getDebugString(String message) {
		return new RemoteCommand(message);
	}
	
	public static RemoteCommand getReceipt(int receipt) {
		return new RemoteCommand(receipt);
	}
	
	public static RemoteCommand setCompass(int inputPin, int timingPin) {
		return new RemoteCommand(CommandType.COMMAND_SET_COMPASS, inputPin, timingPin);
	}
	
	public static RemoteCommand setPing(int pinId) {
		return new RemoteCommand(CommandType.COMMAND_SET_PING, pinId, 0);
	}
	
	public static RemoteCommand setServo(int pinId) {
		return new RemoteCommand(CommandType.COMMAND_SET_SERVO, pinId, 0);
	}

}
