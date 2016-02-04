package com.jjsimpson.rob.sensor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jjsimpson.rob.log.ILogger;
import com.jjsimpson.rob.sensor.value.SensorTripleInt;
import com.jjsimpson.rob.utils.NumberConversion;
import com.pi4j.io.i2c.I2CDevice;

/**
 * 	The LSM303 has an accelerometer and magnetometer
 * 	They work through I2C.
 *
 */
public class LSM303Sensor
{
	public List<SensorTripleInt>	accelerometerQueue;	//This is thread safe
	public List<SensorTripleInt>	magnetometerQueue;	//This is thread safe
	
	protected byte accId;
	protected byte magId;
	protected I2CDevice accelerometer;
	protected I2CDevice magnetometer;
	protected byte[] accelData;
	protected byte[] magData;
	protected ILogger logger;
	
	//Addresses for the sensors
	public final static int ADDRESS_ACCEL = (0x32 >> 1); // 0x19
	public final static int ADDRESS_MAG   = (0x3C >> 1); // 0x1E

	//Registers for the accelerometer
	protected final static int REGISTER_ACCEL_CTRL_REG1_A = 0x20;
	protected final static int REGISTER_ACCEL_CTRL_REG4_A = 0x23;
	protected final static int REGISTER_ACCEL_OUT_X_L_A   = 0x28;
	
	//Registers for the magnetometer
	protected final static int REGISTER_MAG_CRB_REG_M     = 0x01;
	protected final static int REGISTER_MAG_MR_REG_M      = 0x02;
	protected final static int REGISTER_MAG_OUT_X_H_M     = 0x03;

	//Gain for magnetometer
	public final static int MAGGAIN_1_3 = 0x20; // +/- 1.3
	public final static int MAGGAIN_1_9 = 0x40; // +/- 1.9
	public final static int MAGGAIN_2_5 = 0x60; // +/- 2.5
	public final static int MAGGAIN_4_0 = 0x80; // +/- 4.0
	public final static int MAGGAIN_4_7 = 0xA0; // +/- 4.7
	public final static int MAGGAIN_5_6 = 0xC0; // +/- 5.6
	public final static int MAGGAIN_8_1 = 0xE0; // +/- 8.1	
	
	
	
	public LSM303Sensor(byte accelerometerId, byte magnetometerId, I2CDevice accel, I2CDevice mag, ILogger verbose)
	{
		logger = verbose;
		accId = accelerometerId;
		magId = magnetometerId;
		accelerometer = accel;
		magnetometer = mag;
		
		accelData = new byte[6];
		magData = new byte[6];
		
		accelerometerQueue = Collections.synchronizedList(new ArrayList<SensorTripleInt>());
		magnetometerQueue = Collections.synchronizedList(new ArrayList<SensorTripleInt>());
	}

	
	public void startSensor()
	{
		if(logger != null) {
			logger.info("Starting sensors for LSM303");
		}
		
		try
		{
			//Start accelerometer
			accelerometer.write(REGISTER_ACCEL_CTRL_REG1_A, (byte)0x27);			
			accelerometer.write(REGISTER_ACCEL_CTRL_REG4_A, (byte)0x00);
			
		}
		catch (IOException e)
		{
			error("Unable to startup accelerometer");
		}
		
		try
		{
		      magnetometer.write(REGISTER_MAG_MR_REG_M, (byte)0x00);
		      magnetometer.write(REGISTER_MAG_CRB_REG_M, (byte)MAGGAIN_1_3);
		}
		catch (IOException e)
		{
			error("Unable to startup magnometer");
		}

	}
	
	
	public void checkSensor()
	{
		int read = 0;
		
		try
		{
			read = accelerometer.read(REGISTER_ACCEL_OUT_X_L_A | 0x80, accelData, 0, 6);
			if(read == 6)
			{
				int x = NumberConversion.intFrom12Bits(accelData[0], accelData[1]);
				int y = NumberConversion.intFrom12Bits(accelData[2], accelData[3]);
				int z = NumberConversion.intFrom12Bits(accelData[4], accelData[5]);	
				
				magnetometerQueue.add(new SensorTripleInt(accId, x, y, z));
			}
			else
			{
				error("Didn't get enough data when reading accelerometer data");
			}
		}
		catch (IOException e)
		{
			error("Unable to read accelerometer data ");
			e.printStackTrace();
		}
		
		
		try
		{
			read = magnetometer.read(REGISTER_MAG_OUT_X_H_M, magData, 0, 6);
			if(read == 6)
			{
				int x = NumberConversion.intFromTwoBytes(magData[0], magData[1]);
				int y = NumberConversion.intFromTwoBytes(magData[2], magData[3]);
				int z = NumberConversion.intFromTwoBytes(magData[4], magData[5]);
				
				accelerometerQueue.add(new SensorTripleInt(magId, x, y, z));
			}
			else
			{
				error("Didn't get enough data when reading mag data");
			}
		}
		catch (IOException e)
		{
			error("Unable to read mag data");
			e.printStackTrace();
		}
	}		
	
	
	protected void error(String message)
	{
		if(logger != null) {
			logger.info(message);
		}
		else
		{
			System.err.println(message);
		}
	}
	
	
	
}
