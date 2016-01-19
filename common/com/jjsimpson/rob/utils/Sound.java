package com.jjsimpson.rob.utils;

public class Sound {
	
	public final static double BILLION = 10E9;
	public final static double SPEED_OF_SOUND = 34029;	//34029 cm per second
	public final static double SOUND_ROUND_TRIP = SPEED_OF_SOUND / 2;


	//Distance is in cm
	public static double getDistanceFromNanoUltrasonic(long nano)
	{
		double result = 0;
		
		//convert to seconds
		double inSeconds = nano / BILLION;
		
		//Find the distance
		result = inSeconds * SOUND_ROUND_TRIP;
		
		return result;
	}
	
	
	//distance is in cm
	public static long getNanoFromDistance(double dist)
	{
		long result = 0;
		
		//get seconds
		double inSeconds = dist / SPEED_OF_SOUND;
		
		//convert to nano seconds
		result = (long)(inSeconds * BILLION);
		
		return result;
	}
	
	
}
