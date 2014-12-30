package com.jjsimpson.aaengine.activity;

import android.support.v7.app.ActionBarActivity;

import com.jjsimpson.aaengine.ArduinoEngineApplication;
import com.jjsimpson.aaengine.bluetooth.BluetoothDiscovery;

public abstract class ArduinoActivity extends ActionBarActivity
{
	public abstract void handleArduinoResponse();
	
	//From background thread, we call this to update on the UI thread
	public final void updateActivityFromBgThread()
	{
	    runOnUiThread(new Runnable()
	    {
	    	@Override
	    	public void run()
	    	{
	    		handleArduinoResponse();
	    	}
	     });
	}
	
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
		//set activity for handling events
		ArduinoEngineApplication app = (ArduinoEngineApplication)getApplication();
		app.setCurrentActivity(this);
		
		//Start Bluetooth
		startBluetooth();				
	}
	
	
	@Override
	protected void onStop()
	{
		//set activity for handling events
		ArduinoEngineApplication app = (ArduinoEngineApplication)getApplication();
		app.setCurrentActivity(null);

		super.onStop();
	}
	
	
	//This initializes the bluetooth if it needs initialization
	protected void startBluetooth()
	{
		//Get the app
		ArduinoEngineApplication app = (ArduinoEngineApplication)getApplication();
		
		//Auto connect to bluetooth
		BluetoothDiscovery blue = app.getBluetooth();
		blue.init(this);		
	}
	
}
