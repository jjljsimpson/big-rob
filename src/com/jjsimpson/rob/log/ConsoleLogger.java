package com.jjsimpson.rob.log;

public class ConsoleLogger implements ILogger
{
	
	@Override
	public void info(String message) {
		System.out.println("[INFO] " + message);
	}

	@Override
	public void debug(String message) {
		System.out.println("[DEBUG] " + message);
	}

}
