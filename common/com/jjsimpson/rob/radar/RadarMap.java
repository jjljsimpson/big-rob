package com.jjsimpson.rob.radar;

import java.util.Collection;
import java.util.TreeMap;


public class RadarMap
{
	protected TreeMap radarMap;
	
	public RadarMap()
	{
		radarMap = new TreeMap<Float, RadarPoint>();
	}
	
	
	public void reset() {
		radarMap.clear();
	}
	
	
	public void addPoint(RadarPoint pt) {
		
		float heading = pt.getHeading();
		
		if(radarMap.containsKey(heading))
		{
			//Heading already has a distance. So average the value
			RadarPoint old = (RadarPoint)radarMap.get(heading);
			pt.setDistance((pt.getDistance() + old.getDistance()) / 2.0D);
		}
		
		//Put the distance (or averaged distance)
		radarMap.put(heading, pt);		
	}
	
	
	public Collection<RadarPoint> getValues() {
		return radarMap.values();
	}
	
	
}
