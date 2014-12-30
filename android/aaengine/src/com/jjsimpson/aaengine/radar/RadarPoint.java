package com.jjsimpson.aaengine.radar;

public class RadarPoint {
	//Type of point
	protected PointType pointType;
	
	//X and Y coordinate for point.
	//(This is used for the point, but can also use the angle / length)
	protected float	xpos;
	protected float	ypos;
	
	//Angle and length (from the origin)
	protected float angle;
	protected long length;
	
	public RadarPoint(float x, float y, PointType type)
	{
		pointType = type;
		xpos = x;
		ypos = y;		
	}
	
	public RadarPoint(float angle, long length)
	{
		
	}
	
	//Simple Get
	public PointType getType() { return pointType; }
	public float getX() { return xpos; }
	public float getY() { return ypos; }
	public float getAngle() { return angle; }
	public long getLength() { return length; }
	
	//Clone
	public RadarPoint clone() { return new RadarPoint(xpos, ypos, pointType); }
	
	
	//Probably need add, subtract, dist, etc
}
