package com.jjsimpson.test.rob.radar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.testng.annotations.Test;

import com.jjsimpson.rob.radar.RadarMap;
import com.jjsimpson.rob.radar.RadarPoint;
import com.jjsimpson.rob.radar.RadarVisualization;

public class RadarVisualizationTest
{
	protected static final String IMAGE_PATH = "/Users/simpson/Desktop/radar/";
	protected static final double MAX_DISTANCE = 348.0D;
	protected static final double MIN_DISTANCE = 6.0D;
	
	protected static final int MAX_HEADING = 360;
	protected static final int MOVE_AMOUNT = 2;
	protected static final int MOVE_SAME = 5;
	
	protected RadarMap map;
	
	public RadarVisualizationTest()
	{
		map = new RadarMap();
		
		//setup
		setup();
	}
	
	protected void setup()
	{
		map = new RadarMap();
		
		//Create directory to hold images
		File dir = new File(IMAGE_PATH);
		if(!dir.exists()) {
			dir.mkdir();
		}
		
		int heading = 0;
		double distance = getRandomDistance();
		while(heading < MAX_HEADING)
		{
			if(heading % MOVE_SAME == 0) {
				distance = getRandomDistance();
			}
			
			map.addPoint(new RadarPoint((float) heading, distance));

			heading += MOVE_AMOUNT;	//Move 
		}
	}
	
	protected double getRandomDistance()
	{
		return (Math.random() * (MAX_DISTANCE - MIN_DISTANCE)) + MIN_DISTANCE;
	}
	
	
	@Test(groups = "unit")
	public void testLine()
	{
		RadarVisualization radar = new RadarVisualization(map);
		radar.setUseFill(false);
		radar.setUseBoundry(false);
		radar.setUseDots(false);
		radar.setUseCenter(false);
		
		BufferedImage img = radar.createImage();
		File output = new File(IMAGE_PATH + "line_test.png");
		try {
			ImageIO.write(img, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test(groups = "unit")
	public void testFill()
	{
		RadarVisualization radar = new RadarVisualization(map);
		radar.setUseFill(true);
		radar.setUseBoundry(false);
		radar.setUseDots(false);
		radar.setUseCenter(false);
		
		BufferedImage img = radar.createImage();
		File output = new File(IMAGE_PATH + "fill_test.png");
		try {
			ImageIO.write(img, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test(groups = "unit")
	public void testBoundry()
	{
		RadarVisualization radar = new RadarVisualization(map);
		radar.setUseFill(true);
		radar.setUseBoundry(true);
		radar.setUseDots(false);
		radar.setUseCenter(false);
		
		BufferedImage img = radar.createImage();
		File output = new File(IMAGE_PATH + "boundry_test.png");
		try {
			ImageIO.write(img, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
	@Test(groups = "unit")
	public void testPoints()
	{
		RadarVisualization radar = new RadarVisualization(map);
		radar.setUseFill(true);
		radar.setUseBoundry(false);
		radar.setUseDots(true);
		radar.setUseCenter(false);
		
		BufferedImage img = radar.createImage();
		File output = new File(IMAGE_PATH + "point_test.png");
		try {
			ImageIO.write(img, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups = "unit")
	public void testCenter()
	{
		RadarVisualization radar = new RadarVisualization(map);
		radar.setUseFill(true);
		radar.setUseBoundry(false);
		radar.setUseDots(false);
		radar.setUseCenter(true);
		
		BufferedImage img = radar.createImage();
		File output = new File(IMAGE_PATH + "center_test.png");
		try {
			ImageIO.write(img, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
	@Test(groups = "unit")
	public void testFull()
	{
		RadarVisualization radar = new RadarVisualization(map);
		radar.setUseFill(true);
		radar.setUseBoundry(true);
		radar.setUseDots(true);
		radar.setUseCenter(true);
		
		BufferedImage img = radar.createImage();
		File output = new File(IMAGE_PATH + "full_test.png");
		try {
			ImageIO.write(img, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test(groups = "unit")
	public void testLineFull()
	{
		RadarVisualization radar = new RadarVisualization(map);
		radar.setUseFill(false);
		radar.setUseBoundry(true);
		radar.setUseDots(true);
		radar.setUseCenter(true);
		
		BufferedImage img = radar.createImage();
		File output = new File(IMAGE_PATH + "full_line_test.png");
		try {
			ImageIO.write(img, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
}
