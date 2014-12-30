package com.jjsimpson.aaengine.arduino;

//I used constants because we have to convert on arduino (so no enums)
public class PinType
{
	/* Options for setting pin type */
	public static final int PIN_TYPE_UNINITIALIZED =    0;   /* Pin hasn't been initialized. It is set to D Input by default */
	public static final int PIN_TYPE_D_INPUT =          1;   /* Digitial Read will be called on this pin */
	public static final int PIN_TYPE_A_INPUT  =         2;   /* Analog Read will be called on this pin */
	public static final int PIN_TYPE_D_INPUT_PULLUP =	3;   /* Digital Read will be calld on this pin */
	public static final int PIN_TYPE_A_INPUT_PULLUP	=	4;   /* Analog Read will be called on this pin */
	public static final int PIN_TYPE_D_OUTPUT  =        5;   /* Digial write will be called on this pin */
	public static final int PIN_TYPE_A_OUTPUT  =        6;   /* Digial write will be called on this pin */
	public static final int PIN_TYPE_BLUETOOTH =		7; 	 /* Pin is used for bluetooth (which is hardcodeded) */
	public static final int PIN_TYPE_COMPASS =			8; 	 /* Pin is used to control digital compass */
	public static final int PIN_TYPE_PING =				9;	 /* Pin is used to control ultrasonic sensor */
	public static final int PIN_TYPE_SERVO =			10;	 /* Pin is used to control a servo */
}
