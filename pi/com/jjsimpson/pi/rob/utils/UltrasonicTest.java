package com.jjsimpson.pi.rob.utils;

import com.jjsimpson.mock.pi4j.io.gpio.GpioPinDigitalInputMock;
import com.jjsimpson.mock.pi4j.io.gpio.GpioPinDigitalOutputMock;
import com.jjsimpson.rob.sensor.model.Ultrasonic;
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
	
	public static void main(String[] args) {	
		message("----------- Starting the test");
								
		long startTime = System.currentTimeMillis();
		boolean isRunning = true;
		
		//Check if running with mock data or real pi data
//		piSetup();
		desktopSetup();
		
		Ultrasonic ultra = new Ultrasonic(0, echo, trigger);
		
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

		message("Finishing the test -----------");
			
		message("\n\n");
		message("--- Values ---");
		while(ultra.queue.size() > 0)
		{
			long interval = ultra.queue.remove(0);
			intervalMessage(interval);
		}	
		
	}
	
	
	protected static void piSetup()
	{
	    final GpioController gpio = GpioFactory.getInstance();
	    final GpioPinDigitalOutput trigPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "Trig", PinState.LOW);
	    final GpioPinDigitalInput  echoPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_05,  "Echo");
		
	}
	
	
	protected static void desktopSetup()
	{
		echo = new GpioPinDigitalInputMock();
		trigger = new GpioPinDigitalOutputMock();		
	}
	
	
	protected static void message(String msg)
	{
		System.out.println(msg);
	}
	
	
	protected static void intervalMessage(long val)
	{
		System.out.println("interval - " + Long.toString(val));
	}

}
