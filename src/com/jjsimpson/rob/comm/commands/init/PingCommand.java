package com.jjsimpson.rob.comm.commands.init;

import com.jjsimpson.rob.comm.commands.BaseCommand;
import com.jjsimpson.rob.comm.model.CommType;


public class PingCommand extends BaseCommand
{
	public PingCommand()
	{
		super();
		type = new CommType(CommType.TYPE_INIT, InitSubCommands.PING_SUB_COMMAND);
	}
}
