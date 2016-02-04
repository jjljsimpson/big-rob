package com.jjsimpson.rob.utils;

public class NumberConversion
{
	protected static final float MIN_HEADING_VALUE = 0.8f;	//Headings within this value are considered the same	
	
	//Convert 12 bits to an int
	public static int intFrom12Bits(byte firstByte, byte secondByte)
	{
		int n = (firstByte & 0xFF) | ((secondByte & 0xFF) << 8); // Low, high bytes
		if (n > 32767) 
			n -= 65536;                           // 2's complement signed
		
		return n >> 4;                          // 12-bit resolution
	}
	
	
	//Convert 2 bytes to an int
	public static int intFromTwoBytes(byte firstByte, byte secondByte)
	{
		int n = ((firstByte & 0xFF) << 8) | (secondByte & 0xFF);   // High, low bytes
		
		return (n < 32768 ? n : n - 65536);       // 2's complement signed
	}	
	
	
	//Get the heading with x,y,z compass coordinates
	public static float getHeading(int xmag, int ymag, int zmag)
	{
		float result = (float)Math.toDegrees(Math.atan2(ymag, xmag));
		
	      while (result < 0)
	    	  result += 360f;
	      
	      return result;
	}
	
	
	//Compare two headings
	//It returns true, if the headings match, false if they don't match
	public static boolean compareHeadings(float head1, float head2)
	{
		float diff = 0;
		
		if(head1 > head2)
		{
			diff = head1 - head2;
		}
		else
		{
			diff = head2 - head1;
		}
		
		return diff <= MIN_HEADING_VALUE;
		
	}
	
}
