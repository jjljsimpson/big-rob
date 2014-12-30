package com.jjsimpson.aaengine.bluetooth;

import android.util.Log;

public class CommunicationInformation
{
	//This for getting the static version
	protected static CommunicationInformation instance = null;
	
	protected static final String COMM_TAG = "commInfoTag";
	
	//Number of commands sent (not added to the queue)
	protected long numberOfSentCommands;
	protected long numberOfCommands;
	protected long numberOfReceipts;
	protected long numberOfOldReceipts;
	protected long numberOfMissedReceipts;
	protected long transportTime;
	protected long startTime;
	
	public CommunicationInformation()
	{
		numberOfCommands = numberOfReceipts = numberOfOldReceipts = numberOfMissedReceipts = 0;
		numberOfSentCommands = 0;
		transportTime = 0;
	}
	
	
	public void addCommand()
	{
		numberOfCommands++;
		startTime = System.currentTimeMillis();
	}
	
	
	public void addSentCommand()
	{
		numberOfSentCommands++;		
	}
	
	
	public void addMissedReceipt()
	{
		numberOfMissedReceipts++;
	}
	
	
	public void addReceipt()
	{
		numberOfReceipts++;
		long curTime = System.currentTimeMillis();
		transportTime += (curTime - startTime);	//Add the transport time
		
		//Only log here, we should have other info between
		logCommInfo();
	}
	
	
	public void addOldReceipt()
	{
		numberOfOldReceipts++;
	}
	
	
	public static CommunicationInformation getInstance()
	{
		if(instance == null)
		{
			instance = new CommunicationInformation();
		}
		
		return instance;
	}
	
	
	public void logCommInfo()
	{
		//Total Commands, Transport Time, Total Receipts, Missed, Old
		Log.i(COMM_TAG, "---------------");
		Log.i(COMM_TAG, "transportTime: " + transportTime);
		Log.i(COMM_TAG, "numberOfCommands: " + numberOfCommands);
		Log.i(COMM_TAG, "numberOfSentCommands: " + numberOfSentCommands);
		Log.i(COMM_TAG, "numberOfOldReceipts: " + numberOfOldReceipts);
		Log.i(COMM_TAG, "numberOfMissedReceipts: " + numberOfMissedReceipts);
//		float avgTime = (float)transportTime / (float)numberOfCommands;
//		Log.i(COMM_TAG, "Average Transport Time (mill): " + avgTime);
//		Log.i(COMM_TAG, "Total Commands: " + numberOfCommands);
//		long totalR = numberOfReceipts + numberOfOldReceipts + numberOfMissedReceipts;
//		Log.i(COMM_TAG, "Total Receipts: " + totalR);
//		float avgR = (float)numberOfCommands / (float) totalR;
//		Log.i(COMM_TAG, "Average Receipts Per Command: " + avgR);
//		float avgM = (numberOfMissedReceipts > 0) ? (float)numberOfCommands / (float) numberOfMissedReceipts : 0;
//		Log.i(COMM_TAG, "Average Missed per Command: " + avgM);
//		float avgO = (numberOfOldReceipts > 0) ? (float)numberOfCommands / (float) numberOfOldReceipts : 0;
//		Log.i(COMM_TAG, "Average Repeated Receipts: " + avgO);		
	}
	
	
}
