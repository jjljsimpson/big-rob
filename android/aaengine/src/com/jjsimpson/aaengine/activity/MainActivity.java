package com.jjsimpson.aaengine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jjsimpson.aaengine.R;


public class MainActivity extends ArduinoActivity {

	public void handleArduinoResponse()
	{
		//Don't do anything here
	}

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    
    public void onBluetooth(View view)
    {
    	Intent intent = new Intent(this, BluetoothActivity.class);
    	startActivity(intent);
    }
    
    public void onTest(View view)
    {
    	Intent intent = new Intent(this, TestActivity.class);
    	startActivity(intent);
    }
    
    public void onManualDrive(View view)
    {
    	Intent intent = new Intent(this, ManualDriveActivity.class);
    	startActivity(intent);    	
    }
    
    public void onLocationTest(View view)
    {
    	Intent intent = new Intent(this, LocationActivity.class);
    	startActivity(intent);    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
