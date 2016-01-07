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
		
		buffer.put(message.getBytes());		
	}
	
	@Override
	protected void readData(ByteBuffer buffer)
	{
		super.readData(buffer);
		
		int size = buffer.capacity() - buffer.position();
		message = new String(buffer.array(), buffer.position(), size);		
	}
	
	@Override
	public int getSize()
	{
		return super.getSize() + message.length();
	}		
	
}
