package com.jjsimpson.rob.comm.commands.init;

import java.nio.ByteBuffer;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.model.CommType;

public class LogCommand extends BaseCommand
{
	protected String message;
	public LogCommand()
	{
		super();
		type = new CommType(CommType.TYPE_INIT, InitSubCommands.LOG_SUB_COMMAND);	//Default command		
		message = "";
	}
	
	public LogCommand(String message)
	{
		super();
		type = new CommType(CommType.TYPE_INIT, InitSubCommands.LOG_SUB_COMMAND);	//Default command
		this.message = message;
	}
	
	public String getMessage() { return message; }
	public void setMessage(String newMessage) { message = newMessage; }

	
	@Override
	protected void writeData(ByteBuffer buffer)
	{
		super.writeData(buffer);
		
		for(int i=0; i<message.length(); i++)
		{
			buffer.putChar(message.charAt(i));
		}
	}
	
	@Override
	protected void readData(ByteBuffer buffer)
	{
		super.readData(buffer);		
		message = new String(buffer.asCharBuffer().array());
	}
	
	@Override
	public int getSize()
	{
		return super.getSize() + message.length();
	}		
	
}
