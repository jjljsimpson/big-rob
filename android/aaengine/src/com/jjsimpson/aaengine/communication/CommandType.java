package com.jjsimpson.aaengine.communication;

public class CommandType
{
	/* Commands */
	public static final int COMMAND_SET_PIN_TYPE=        'p';   /* set how the pin will be used */
	public static final int COMMAND_SET_PIN_VALUE=       'v';   /* set the value for a pin */
	public static final int COMMAND_SET_COMPASS=		 'c';	/* set 2 pins for a compass sensor */
	public static final int COMMAND_SET_PING=			 'i';	/* For controlling a ultrasonic sensor */
	public static final int COMMAND_SET_SERVO=			 's';	/* Set pin as a servo */
	public static final int COMMAND_WAIT=                'w';   /* how long to wait before processing the next command. This only works for test commands */
	public static final int COMMAND_GET_PIN_VALUE=       'r';   /* arduino sends back value from a pin */

}
