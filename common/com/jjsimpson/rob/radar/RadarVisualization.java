package com.jjsimpson.rob.radar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Iterator;

import com.jjsimpson.rob.sensor.Ultrasonic;
import com.jjsimpson.rob.sensor.value.Point2D;
import com.jjsimpson.rob.utils.Sound;

public class RadarVisualization
{
	protected static final int	IMG_WIDTH = 640;
	protected static final int	IMG_HEIGHT = 640;
	protected static final double RADAR_RADIUS = IMG_WIDTH / 2;
	protected static final double RADIAN_OFFSET = Math.PI * 2;
	protected static final double DEG = 360D;
	
	protected static final int IMG_RADIUS = IMG_WIDTH;
	protected static final int IMG_MID_X = IMG_WIDTH >> 1;
	protected static final int IMG_MID_Y = IMG_HEIGHT >> 1;
	protected static final int POINT_RADIUS = 6;
	
	protected static final int STROKE_SMALL = 1;
	protected static final int STROKE_SIZE = 4;
	
	protected static final Color COLOR_BACKGROUND = Color.BLACK;
	protected static final Color COLOR_LINE = Color.WHITE;
	protected static final Color COLOR_POINT = Color.RED;
	protected static final Color COLOR_DISTANCE = Color.GREEN;
	
	protected RadarMap radarMap;
	protected boolean useFill;		//True = fill area between points. False = known line only
	protected boolean useDots;		//Put dots at points
	protected boolean useBoundry;	//Show radar boundry line
	protected boolean useCenter;	//Show center point
	protected double maxDistance;	//max distance for each point
	protected BasicStroke stroke;
	protected BasicStroke smallStroke;
	
	public RadarVisualization() {
		reset(null, true, true, true, true);
	}
	
	public RadarVisualization(RadarMap map) {
		reset(map, true, true, true, true);
	}

	public boolean isUseFill() { return useFill; }
	public void setUseFill(boolean useFill) { this.useFill = useFill; }

	public boolean isUseDots() { return useDots; }
	public void setUseDots(boolean useDots) { this.useDots = useDots; }

	public boolean isUseBoundry() { return useBoundry; }
	public void setUseBoundry(boolean useBoundry) { this.useBoundry = useBoundry; }

	public boolean isUseCenter() { return useCenter; }
	public void setUseCenter(boolean useCenter) { this.useCenter = useCenter; }
	
	public void setMap(RadarMap map) { radarMap = map; }
	
	protected void reset(RadarMap map, boolean fill, boolean dots, boolean boundry, boolean center)
	{
		radarMap = map;
		useFill = fill;
		useDots = dots;
		useBoundry = boundry;
		useCenter = center;
		maxDistance = Sound.getDistanceFromNanoUltrasonic(Ultrasonic.MAX_DISTANCE);
		stroke = new BasicStroke(STROKE_SIZE);
		smallStroke = new BasicStroke(STROKE_SMALL);
	}
	
		
	
	public BufferedImage createImage()
	{
		//Create the image
		BufferedImage img = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = img.createGraphics();
		
		//Set the background color
		graphics.setColor(COLOR_BACKGROUND);
		graphics.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);
		
		if(useFill)
		{
			createFillImage(graphics);
		}
		else
		{
			createLineImage(graphics);
		}
		
		//Draw radar outline
		if(useBoundry) {
			drawOutline(graphics); }
		
		//Draw center point
		if(useCenter) {
			drawPoint(graphics, new Point2D(IMG_MID_X, IMG_MID_Y), COLOR_DISTANCE);
		}
		
		return img;
	}
	
	
	protected void createFillImage(Graphics2D graphics)
	{
		//Only draw if there are more than 3 points
		if(radarMap.radarMap.size() > 2)
		{
			graphics.setColor(COLOR_LINE);
			
			Collection<RadarPoint> coll = radarMap.getValues();
			Iterator<RadarPoint> it = coll.iterator();
			int xpoints[] = new int[coll.size()];
			int ypoints[] = new int[coll.size()];
			
			//Loop through and put them into an array
			RadarPoint radarPt;
			Point2D	point;
			int position = 0;
			while(it.hasNext())
			{
				radarPt = it.next();
				point = convertToPoint(radarPt);
				xpoints[position] = point.x;
				ypoints[position] = point.y;
				
				position++;
			}
			
			//Now draw the polygon
			graphics.fillPolygon(xpoints, ypoints, xpoints.length);

			//Now draw the dots
			if(useDots)
			{
				graphics.setColor(COLOR_POINT);
				
				for(int i=0; i<xpoints.length; i++)
				{
					drawPoint(graphics, new Point2D(xpoints[i], ypoints[i]) );
				}
			}
			
		}
	}
	
	protected void createLineImage(Graphics2D graphics)
	{
		graphics.setColor(COLOR_LINE);
		Point2D center = new Point2D(IMG_MID_X, IMG_MID_Y);
		
		//Loop through and draw lines
		Collection<RadarPoint> coll = radarMap.getValues();
		Iterator<RadarPoint> it = coll.iterator();
		RadarPoint radarPt;
		Point2D endPoint;
		while(it.hasNext())
		{
			radarPt = it.next();
			endPoint = convertToPoint(radarPt);
			
			graphics.setStroke(stroke);
			graphics.drawLine(center.x, center.y, endPoint.x, endPoint.y);
			
			if(useDots) {
				graphics.setStroke(smallStroke);
				graphics.setColor(COLOR_POINT);
				drawPoint(graphics, endPoint);
				graphics.setColor(COLOR_LINE);
			}
			
		}
	}
	
			
	protected void drawOutline(Graphics2D graphics)
	{
		graphics.setColor(COLOR_DISTANCE);
		graphics.drawOval(0, 0, IMG_RADIUS, IMG_RADIUS);
	}
	
	
	protected void drawPoint(Graphics2D graphics, Point2D pt)
	{
		drawPoint(graphics, pt, COLOR_POINT);
	}
	protected void drawPoint(Graphics2D graphics, Point2D pt, Color clr)
	{
		int offset = POINT_RADIUS >> 1;
		graphics.setColor(clr);
		graphics.fillOval(pt.x-offset, pt.y-offset, POINT_RADIUS, POINT_RADIUS);
	}
	
	
	protected Point2D convertToPoint(RadarPoint pt)
	{
		Point2D result = null;
		
		//Get distance as a percentage
		double perc = pt.distance / maxDistance;
		
		//Now get distance
		double dist = perc * RADAR_RADIUS;
		
		//convert heading to radians
		double rad = (RADIAN_OFFSET * pt.heading) / DEG;
		
		//Get X, and Y
		double xval = Math.cos(rad);
		double yval = Math.sin(rad);
		
		//Now convert to int
		int ixval = Math.round( (float)(xval * dist) );
		int iyval = Math.round( (float)(yval * dist) );
		
		//Move to image space
		result = new Point2D(ixval + IMG_MID_X, iyval + IMG_MID_Y);
		
		return result;
	}
	
	
	
	
	
	
	
}
