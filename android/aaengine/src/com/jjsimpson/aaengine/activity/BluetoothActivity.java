package com.jjsimpson.aaengine.activity;

import java.util.ArrayList;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jjsimpson.aaengine.ArduinoEngineApplication;
import com.jjsimpson.aaengine.R;
import com.jjsimpson.aaengine.bluetooth.BluetoothDiscovery;


public class BluetoothActivity extends ArduinoActivity implements AdapterView.OnItemClickListener
{	
	protected	ListView					mainListView;
	protected	ArrayAdapter<String>		listAdapter;  	
	protected	ArrayList<BluetoothDevice>	deviceList;
	protected	TextView					statusView;
	
	public void handleArduinoResponse()
	{
		//TODO read events (Don't do anything)
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		
		//Get the list view
		mainListView = (ListView) findViewById(R.id.bluetoothListView);
				
		//Get list of devices
		BluetoothDiscovery bluetooth = ((ArduinoEngineApplication)getApplication()).getBluetooth();
		bluetooth.init(this);	//Initialize just in case we didn't yet
		deviceList = bluetooth.getBluetoothDevices();
		ArrayList<String> names = new ArrayList<String>();
		for(int i=0; i<deviceList.size(); i++)
		{
			names.add(deviceList.get(i).getName());
		}
		listAdapter = new ArrayAdapter<String>(this, R.layout.view_bluetooth, names);
		
		//Get Status Text
		statusView = (TextView)findViewById(R.id.status);		
		getBluetoothStatus();
		
		//Set ListView
		mainListView.setAdapter(listAdapter);
		mainListView.setOnItemClickListener(this);		
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
		getBluetoothStatus();
	}
	
	
	protected void getBluetoothStatus()
	{
		//Get Bluetooth
		BluetoothDiscovery blue = ((ArduinoEngineApplication)this.getApplication()).getBluetooth();
		
		if(!blue.isInitialized())
		{
			statusView.setText(R.string.bluetooth_status_notinit);
		}
		else
		{
			if(!blue.isEnabled())
			{
				statusView.setText(R.string.bluetooth_status_notenabled);
			}
			else
			{
				if(!blue.isConnected())
				{
					BluetoothDevice bdevice = blue.getDevice();
					if(bdevice == null)
					{
						statusView.setText(R.string.bluetooth_status_disconnected);
					}
					else
					{
						String status = getResources().getString(R.string.bluetooth_status_disconnected_device);
						statusView.setText(String.format(status, bdevice.getName()));
					}
				}
				else
				{
					String status = getResources().getString(R.string.bluetooth_status_connected);
					statusView.setText(String.format(status, blue.getDevice().getName()));
				}
			}
		}
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bluetooth, menu);
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
	
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		//Make sure position is within bounds
		if(position >= 0 && position < deviceList.size())
		{			
			//Connect
			BluetoothDiscovery bluetooth = ((ArduinoEngineApplication)getApplication()).getBluetooth();			
			bluetooth.connect(deviceList.get(position), this);
			
			getBluetoothStatus();
		}		
	}	
	
	
	
    public void onDisconnect(View view)
    {
		statusView.setText(R.string.bluetooth_disconnecting);
		
		//Get list of devices
		BluetoothDiscovery bluetooth = ((ArduinoEngineApplication)getApplication()).getBluetooth();
		bluetooth.connect(null, this);
		
		getBluetoothStatus();
    }
	
}
