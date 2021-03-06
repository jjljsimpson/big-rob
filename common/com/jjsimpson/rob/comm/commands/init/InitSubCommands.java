package com.jjsimpson.rob.comm.commands.init;

public class InitSubCommands
{
	public static final int BASE_COMMAND = 0;			//Default command, doesn't do anything
	public static final int FEEDBACK_SUB_COMMAND = 1;	//Feedback that a command was received
	public static final int PING_SUB_COMMAND = 2;		//Hello command, doesn't expect any action
	public static final int HANDSHAKE_SUB_COMMAND = 3;	//request for comm connection
	public static final int LOG_SUB_COMMAND = 4;		//a log message
	public static final int SHUTDOWN_SUB_COMMAND = 5;	//server shut down, client needs to shut down too.
	
}
