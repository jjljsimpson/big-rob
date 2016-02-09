package com.jjsimpson.rob.sensor.value;

public class Point2D
{
	public int x;
	public int y;
	
	public Point2D()
	{
		reset(0,0);
	}
	
	public Point2D(int xval, int yval) {
		reset(xval, yval);
	}
	
	
	protected void reset(int xval, int yval) { x = xval; y = yval; }
}
