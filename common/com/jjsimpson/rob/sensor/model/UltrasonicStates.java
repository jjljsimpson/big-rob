package com.jjsimpson.rob.sensor.model;

public class UltrasonicStates
{
	public static final int READY_START = 0;		//Starting up, only done once
	public static final int SENSOR_INIT = 1;		//set to low before sending pulse
	public static final int SENSOR_START = 2;		//sending start command
	public static final int SENSOR_WAIT = 3;		//waiting for sensor to start
	public static final int SENSOR_RESPOND = 4;		//waiting for response to finish
	public static final int SENSOR_FINISHED = 5;	//finished getting value
}
