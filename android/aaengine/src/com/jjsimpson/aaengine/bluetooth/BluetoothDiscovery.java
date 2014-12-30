package com.jjsimpson.aaengine.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

public class BluetoothDiscovery
{
	public static final boolean AUTO_CONNECT_BLUETOOTH = false;
	public static final int		REQUEST_ENABLE_BT = 7;	//This gets passed back later
	public static final UUID	BLUETOOTH_ID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	public static final String	BLUETOOTH_NAME_PREFERENCE = "bluetooth_name_preference";

	protected BluetoothAdapter 		adapter;	//interface to all bluetooth
	protected BluetoothDevice		device;		//current device we are connected to
	protected BluetoothSocket		socket;		//socket for communicating with bluetooth
	protected InputStream			inStream;	//input communicate
	protected OutputStream			outStream;	//output communicate
	
	protected BluetoothWriter		blueWriter;	//Object for writing to bluetooth (in a separate thread)
	protected BluetoothReader		blueReader;	//Object for reading from bluetooth (in a separate thread)
	
	public BluetoothDiscovery()
	{
		device = null;
		socket = null;
		inStream = null;
		outStream = null;
		
		blueWriter = new BluetoothWriter();
		blueWriter.start();
		
		blueReader = new BluetoothReader();
		blueReader.setWriter(blueWriter);
		blueReader.start();
	}
	
	
	public BluetoothDevice getDevice()
	{
		return device;
	}
	
	public Handler getWriteHandler() { return blueWriter.getHandler(); }	
	
	public boolean isInitialized() { return adapter != null; }
	public boolean isEnabled() { return adapter.isEnabled(); }
	public boolean isConnected() { return socket != null && socket.isConnected(); }
	
	
	//This makes sure blue tooth is only initialized once
	//Activity is passed so it can enable BT if needed
	// This will also auto connect to the last device we connected to
	// If we purposely disconnect, then we won't auto connect next time.
	public void init(Activity activity)
	{
		//Don't get adapter if we already go it.
		if(adapter == null)
		{
			adapter = BluetoothAdapter.getDefaultAdapter();
		}
		
		//Enable bluetooth
		if(adapter != null && !adapter.isEnabled())
		{
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			activity.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);				
		}
		
		//Auto connect if possible (This takes too long, so don't do it yet)
		if(AUTO_CONNECT_BLUETOOTH)
		{
			if(adapter != null && adapter.isEnabled() && !isConnected())
			{
				String deviceName = readAutoConnectDevice(activity);
				
				//Get list of devices
				if(deviceName != null)
				{
					connect(findDeviceByName(deviceName), activity);
				}
			}
		}
	}	
	
	
	public void destroy()
	{
		//Kill bluetooth
		disconnect();
		
		//Tell writer to stop
		blueWriter.getHandler().getLooper().quit();
		blueReader.interrupt();
	}
	
	
	protected BluetoothDevice findDeviceByName(String name)
	{
		BluetoothDevice result = null;
		
		//Get list of devices
		ArrayList<BluetoothDevice> deviceList = getBluetoothDevices();
		
		//Loop through
		BluetoothDevice bdevice;
		for(int i=0; i<deviceList.size(); i++)
		{
			bdevice = deviceList.get(i);	//get device
			if(bdevice != null && bdevice.getName().equals(name))
			{
				result = bdevice;	//we found it!
				break;	//stop looping
			}
		}
		
		return result;
	}
	
	
	
	public ArrayList<BluetoothDevice> getBluetoothDevices()
	{
		//Return empty list if bluetooth isn't enabled
		ArrayList<BluetoothDevice> result = new ArrayList<BluetoothDevice>();
		
		if(adapter != null && adapter.isEnabled())
		{
			Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();
			if(pairedDevices.size() > 0)
			{
				for(BluetoothDevice device : pairedDevices)
				{
					result.add(device);
				}
			}
		}
		
		return result;
	}
	
	
	
	public void connect(BluetoothDevice bdevice, Activity activity)
	{
		//If device is already connected then disconnect
		if(device != null)
		{
			disconnect();
		}
		
		device = bdevice;
		if(device != null)
		{
			try
			{
				socket = device.createRfcommSocketToServiceRecord(BLUETOOTH_ID);
				socket.connect();
				outStream = socket.getOutputStream();
				inStream = socket.getInputStream();
				
				//Set bluetooth stream
				blueWriter.setStream(outStream);
				blueReader.setStream(inStream);
			}
			catch (IOException e)
			{
				socket = null;
				outStream = null;
				inStream = null;
				e.printStackTrace();
			}			
		}
		
		
		String name = null;
		if(device != null)
			name = device.getName();
		writeAutoConnectDevice(activity, name);
	}	
	
	
	protected String readAutoConnectDevice(Activity activity)
	{
		return activity.getPreferences(Activity.MODE_PRIVATE).getString(BLUETOOTH_NAME_PREFERENCE, null);		
	}
	
	
	protected void writeAutoConnectDevice(Activity activity, String name)
	{
		SharedPreferences sharedPref = activity.getPreferences(Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		if(name == null)
			editor.remove(BLUETOOTH_NAME_PREFERENCE);
		else
			editor.putString(BLUETOOTH_NAME_PREFERENCE, name);
		editor.commit();		
	}
	
	
	protected void disconnect()
	{
		//Stop bluetooth from writing any more
		blueWriter.setStream(null);
		blueReader.setStream(null);
		
		if(inStream != null)
		{
			try {
				inStream.close(); }
			catch (IOException e) {
				e.printStackTrace(); }
			finally {
				inStream = null; }
		}
		
		if(outStream != null)
		{
			try {
				outStream.close(); }
			catch (IOException e) {
				e.printStackTrace(); }
			finally {
				outStream = null; }
		}
		
		if(socket != null)
		{
			try {
				socket.close(); }
			catch (IOException e) {
				e.printStackTrace(); }
			finally {
				socket = null; }
		}		
	}	
}
