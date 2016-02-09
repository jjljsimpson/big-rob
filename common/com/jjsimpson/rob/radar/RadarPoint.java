package com.jjsimpson.rob.radar;

import com.jjsimpson.rob.sensor.value.Point2D;

public class RadarPoint
{
	protected float heading;	//in degrees
	protected double distance;	//in cm
	
	public RadarPoint() {
		reset(heading, distance);
	}
	
	public RadarPoint(float dir, double dist) {
		reset(dir, dist);
	}
	
	public float	getHeading() { return heading; }
	public void		setHeading(float heading) { this.heading = heading; }
	
	public double	getDistance() { return distance; }
	public void		setDistance(double distance) { this.distance = distance; }
		
	protected void reset(float dir, double dist) {
		heading = dir;
		distance = dist;
	}	
	
	
}
