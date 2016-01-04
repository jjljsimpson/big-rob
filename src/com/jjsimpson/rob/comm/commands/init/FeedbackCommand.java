package com.jjsimpson.rob.comm.commands.init;

import java.nio.ByteBuffer;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.model.CommType;


public class FeedbackCommand extends BaseCommand
{
	public static final int INVALID_CONTROL = 0;	
	
	//Value for the control that this is feedback for	
	protected int	control;
	
	public FeedbackCommand()
	{
		super();
		control = INVALID_CONTROL;
		type = new CommType(CommType.TYPE_INIT, InitSubCommands.FEEDBACK_SUB_COMMAND);
	}
	
	public FeedbackCommand(int feedbackControl)
	{
		super();
		control = feedbackControl;
		type = new CommType(CommType.TYPE_INIT, InitSubCommands.FEEDBACK_SUB_COMMAND);
	}	
	
	@Override
	protected void writeData(ByteBuffer buffer)
	{
		super.writeData(buffer);
		buffer.put((byte) control);
	}
	
	@Override
	protected void readData(ByteBuffer buffer)
	{
		super.readData(buffer);
		control = buffer.get();
	}
	
	@Override
	public int getSize()
	{
		return super.getSize() + 1;
	}
	
	
}
