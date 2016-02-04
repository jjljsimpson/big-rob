package com.jjsimpson.pi.rob.utils;

import com.jjsimpson.mock.pi4j.io.gpio.GpioPinDigitalInputMock;
import com.jjsimpson.mock.pi4j.io.gpio.GpioPinDigitalOutputMock;
import com.jjsimpson.rob.log.ConsoleLogger;
import com.jjsimpson.rob.log.ILogger;
import com.jjsimpson.rob.sensor.Ultrasonic;
import com.jjsimpson.rob.utils.Sound;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class UltrasonicTest {

	protected static GpioPinDigitalInput echo;
	protected static GpioPinDigitalOutput trigger;
	protected static final long END_TIME = 5000;
	protected static ILogger logger;
	
	public static void main(String[] args) {
		logger = new ConsoleLogger();
		
		logger.info("----------- Starting the test");
								
		long startTime = System.currentTimeMillis();
		boolean isRunning = true;
		
		//Check if running with mock data or real pi data
//		piSetup();
		desktopSetup();
		
		Ultrasonic ultra = new Ultrasonic((byte)0, echo, trigger, logger);
		
		while(isRunning)
		{
			//Run Sensor
			ultra.checkSensor();
			
			//Check if we are done
			long end = System.currentTimeMillis();
			if(end - startTime > END_TIME) {
				isRunning = false;
			}
		}

		logger.info("Finishing the test -----------");
			
		logger.info("\n\n");
		logger.info("--- Values ---");
		while(ultra.queue.size() > 0)
		{
			long interval = ultra.queue.remove(0);
			intervalMessage(interval);
		}	
		
	}
	
	
	protected static void piSetup()
	{
	    final GpioController gpio = GpioFactory.getInstance();
	    trigger = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "Trig", PinState.LOW);
	    echo = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05,  "Echo");
		
	}
	
	
	protected static void desktopSetup()
	{
		trigger = new GpioPinDigitalOutputMock();
		echo = new GpioPinDigitalInputMock();		
	}
	
	

	
	protected static void intervalMessage(long val)
	{
		double inCm = Sound.getDistanceFromNanoUltrasonic(val);
		
		logger.info("interval - " + Long.toString(val) + " in CM: " + inCm);
	}

}
