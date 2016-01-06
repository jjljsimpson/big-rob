package com.jjsimpson.rob.log;

public class ConsoleLogger implements ILogger
{
	protected String prefix;
	
	public ConsoleLogger(String pre)
	{
		prefix = pre;
	}
	
	
	public ConsoleLogger()
	{
		prefix = "";
	}
	
	
	@Override
	public void info(String message) {
		System.out.println(prefix + "[INFO] " + message);
	}

	@Override
	public void debug(String message) {
		System.out.println(prefix + "[DEBUG] " + message);
	}

}
