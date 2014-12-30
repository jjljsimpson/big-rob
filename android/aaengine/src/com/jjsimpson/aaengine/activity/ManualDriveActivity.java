package com.jjsimpson.aaengine.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VerticalSeekBar;

import com.jjsimpson.aaengine.ArduinoEngineApplication;
import com.jjsimpson.aaengine.R;
import com.jjsimpson.aaengine.engine.DualDriveEngine;

public class ManualDriveActivity extends ArduinoActivity implements SeekBar.OnSeekBarChangeListener
{
	protected static final int MAX_VALUE = 511;
	protected static final int FORWARD = 256;
	
	protected DualDriveEngine	engine;
	protected VerticalSeekBar	leftSeek;
	protected VerticalSeekBar	rightSeek;
	protected TextView			leftText;
	protected TextView			rightText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual_drive);
		
		//Get writer and state
		ArduinoEngineApplication app = (ArduinoEngineApplication)getApplication();
		
		//Create the engine
		engine = new DualDriveEngine(app.getBluetooth(), app.getArduinoState());
		engine.initialize();
		
		//Get the text fields
		leftText = (TextView)findViewById(R.id.textViewLeft);
		rightText = (TextView)findViewById(R.id.textViewRight);
		
		//Get the seek bars
		leftSeek = (VerticalSeekBar) findViewById(R.id.seekBarLeft);
		rightSeek = (VerticalSeekBar) findViewById(R.id.seekBarRight);
		
		//Set the listener
		leftSeek.setOnSeekBarChangeListener(this);
		rightSeek.setOnSeekBarChangeListener(this);
		
		//Set the max and value
		leftSeek.setMax(MAX_VALUE-1);
		rightSeek.setMax(MAX_VALUE-1);
		onStopTrackingTouch(leftSeek);	//do a stop (set speed to 0)
		onStopTrackingTouch(rightSeek);	//do a stop (set speed to 0)
	}
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manual_drive, menu);
		return true;
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
	
	
	@Override
	public void handleArduinoResponse()
	{
		//TODO do something here
	}
	
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	{
		//Increment progress by 1 so goes from 1 - MAX_VALUE
		progress += 1;
		int speed = getSpeed(progress);
		
		//Send the updated value (and set text)
		if(seekBar.getId() == leftSeek.getId())
		{
			engine.moveLeft(speed);
			leftText.setText(Integer.toString(speed));
		}
		else if(seekBar.getId() == rightSeek.getId())
		{
			engine.moveRight(speed);
			rightText.setText(Integer.toString(speed));
		}		
	}
	
	
	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{
		//Don't need to do anything here
	}
	
	
	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{
		//When we lift up, then stop the movement
		if(seekBar.getId() == leftSeek.getId())
		{
			engine.stopLeft();
			leftText.setText("0");
		}
		else if(seekBar.getId() == rightSeek.getId())
		{
			engine.stopRight();
			rightText.setText("0");
		}
		
		if(seekBar != null)
			seekBar.setProgress(FORWARD-1);		
	}
	
	
/*	
 	(510+1) - 256 = 255
 	(255+1) - 256 = 0
 	(254+1) - 256 = -1
 	(0+1) - 256 = -255
*/
	
	protected int getSpeed(int val)
	{
		return val - FORWARD;
	}
	
	
	
	
}
