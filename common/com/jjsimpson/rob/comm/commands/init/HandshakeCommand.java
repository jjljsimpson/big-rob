package com.jjsimpson.rob.comm.commands.init;

import java.nio.ByteBuffer;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.model.CommType;

public class HandshakeCommand extends BaseCommand
{
	protected int	profileId;	//what type of device
	
	public HandshakeCommand()
	{
		super();
		type = new CommType(CommType.TYPE_INIT, InitSubCommands.HANDSHAKE_SUB_COMMAND);
	}
	
	public HandshakeCommand(int id)
	{
		super();
		type = new CommType(CommType.TYPE_INIT, InitSubCommands.HANDSHAKE_SUB_COMMAND);
		
		profileId = id;
	}
	
	
	public void setProfileId(int id) { profileId = id; }
	public int getProfileId() { return profileId; }
	
	
	@Override
	protected void writeData(ByteBuffer buffer)
	{
		super.writeData(buffer);
		buffer.put((byte) profileId);
	}
	
	@Override
	protected void readData(ByteBuffer buffer)
	{
		super.readData(buffer);
		profileId = buffer.get();
	}
	
	@Override
	public int getSize()
	{
		return super.getSize() + 1;
	}	
	
}
