package com.jjsimpson.rob.comm.commands.init;
import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.model.CommType;


public class ShutdownCommand extends BaseCommand
{
	public ShutdownCommand()
	{
		super();
		type = new CommType(CommType.TYPE_INIT, InitSubCommands.SHUTDOWN_SUB_COMMAND);
	}
}
