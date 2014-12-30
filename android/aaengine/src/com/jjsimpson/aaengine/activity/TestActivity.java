package com.jjsimpson.aaengine.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jjsimpson.aaengine.ArduinoEngineApplication;
import com.jjsimpson.aaengine.R;
import com.jjsimpson.aaengine.bluetooth.BluetoothDiscovery;
import com.jjsimpson.aaengine.engine.TestEngine;

public class TestActivity extends ArduinoActivity
{
	protected TestEngine engine;
	protected BluetoothDiscovery bluetooth;
	
	@Override
	public void handleArduinoResponse()
	{
		//TODO JLS
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		//Get writer and state
		ArduinoEngineApplication app = (ArduinoEngineApplication)getApplication();
		bluetooth = app.getBluetooth();
		
		//Create the engine
		engine = new TestEngine(app.getBluetooth(), app.getArduinoState());
		engine.initialize();			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}
	
	public void onTestMotor(View view)
	{
		//This sends commands to test the motor (including pause commands)
		engine.testDualMotor();
	}
	


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
