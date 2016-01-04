package com.jjsimpson.rob.comm.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.jjsimpson.rob.comm.commands.BaseCommand;

public class BasicFrame
{
	public static final byte[] HEADER = {'J', 'R', 'O', 'B'};
	public static final byte[] FOOTER = {0,0,0,0};
	
	protected static final int START_SIZE = 9;
	protected static final int END_SIZE = 5;
	protected static final int MIN_SIZE = START_SIZE + END_SIZE;
	
	//Data in the frame
	protected byte[]	header;		//4	Start of the communication
	protected int		size;		//2	Size of this data (includes header)
	protected int		control;	//1	control value matches endControl to be valid
	protected int		type;		//2 type of frame
	protected byte[]	data;		//? The data of the communication
	protected int		endControl;	//1	The end control value which matches endControl (to be valid)
	protected byte[]	footer;		//4	The footer of the data
	
	//used to see how we are doing in the read process
	protected int		position;			//Byte position in the read
	protected boolean	isFinishedReading;	//If finished reading
	protected boolean	isValidReading;		//If valid while reading
	
	public BasicFrame()
	{
		header = new byte[4];
		footer = new byte[4];
		
		reset();
	}
	
	
	public BasicFrame(BaseCommand cmd)
	{
		header = new byte[4];
		footer = new byte[4];
		
		reset();
		data = cmd.writeToByte();
		type = cmd.getType().getFullType();
		size = data.length + MIN_SIZE;
	}
	
	
	public void reset()
	{
		header[0] = HEADER[0];	header[1] = HEADER[1];	header[2] = HEADER[2];	header[3] = HEADER[3];
		
		size = 0;
		control = 0;
		type = 0;
		data = null;
		endControl = 0;
		
		footer[0] = FOOTER[0];	footer[1] = FOOTER[1];	footer[2] = FOOTER[2];	footer[3] = FOOTER[3];
		
		position = 0;
		isFinishedReading = false;
		isValidReading = false;
	}
	
	
	public byte[] getData() { return data; }
	public void setData(byte[] newData)
	{
		data = newData;
		
		//Update size
		int dataSize = 0;
		if(data != null) {
			dataSize = data.length;
		}
		
		size = dataSize + MIN_SIZE;
	}
	
	public CommType getType() { return new CommType(type); }
	public void setType(CommType commType) {
		type = commType.getFullType();
	}
	
	public void setControl(int val) { control = endControl = val; }
	public int getControl() { return control; }
	public boolean isFinished() { return isFinishedReading; }
	public boolean isValid() { return isValidReading; }	
	public int getFullSize() { return size; }
	public int getDataSize()
	{
		int result = 0;
		
		if(data != null)
		{
			result = data.length;
		}
		
		return result;
	}
	
		
	
	// --------------- Writing ----------------
	
	//Write with a control value, or you can set the control and write
	public int write(DataOutputStream stream, int controlValue)
	{
		setControl(controlValue);
		return write(stream);
	}
	//Write, but control value was already set
	public int write(DataOutputStream stream)
	{
		int result = 0;
		
		if(stream != null)
		{
			try
			{
				result += writeHeader(stream);
				result += writeSize(stream);
				result += writeControl(stream, control);
				result += writeType(stream);
				result += writeData(stream);
				result += writeControl(stream, endControl);
				result += writeFooter(stream);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	//Methods for writing parts of this frame. To be overridden by sub classes
	protected int writeHeader(DataOutputStream stream) throws IOException {	stream.write(header);	return header.length; }	
	protected int writeSize(DataOutputStream stream) throws IOException {	stream.writeShort(size);	return 2; }	
	protected int writeControl(DataOutputStream stream, int controlVal) throws IOException {stream.writeByte(controlVal);	return 1; }
	protected int writeType(DataOutputStream stream) throws IOException {	stream.writeShort(type);	return 2; }	
	protected int writeFooter(DataOutputStream stream) throws IOException {	stream.write(footer);	return footer.length; }	
	protected int writeData(DataOutputStream stream) throws IOException
	{
		if(data != null && stream != null)
		{
			stream.write(data, 0, data.length);
			return data.length;
		}
		
		return 0;
	}
	
	
	// --------------- Reading ----------------	
	
	public void read(DataInputStream stream)
	{
		//Reset flags
		isValidReading = true;
		if(isFinishedReading) {
			isFinishedReading = false;
			position = 0;
		}
		
		//Loop through the data
		try
		{
			while(stream != null && stream.available() > 0 && !isFinishedReading)
			{
				if(position < 4) {
					readHeader(stream);					
				}
				else if(position < 6) {
					if(!readSize(stream)) {
						return;	//Not enough data to read, so try again later
					}
				}
				else if(position < 7) {
					readControl(stream);
				}
				else if(position < 9) {
					if(!readType(stream)) {
						return; //Not enough data to read, so try again later
					}
				}
				else if(position < size - END_SIZE) {
					readData(stream);
				}
				else {
					readEndControl(stream);
				}
			}
		}
		catch(IOException e)
		{
			isFinishedReading = true;
			position = 0;
			isValidReading = false;
			e.printStackTrace();
		}
	}
	
	public void readHeader(DataInputStream stream) throws IOException
	{
		if(stream != null)
		{
			//Grab a byte
			byte data = stream.readByte();
			
			if(data == HEADER[position])
			{
				header[position] = data;
				position++;
			}
			else
			{
				//Data didn't match, so keep looking for the correct header
				position = 0;
			}
		}
	}
	
	public boolean readSize(DataInputStream stream) throws IOException
	{
		if(stream != null && stream.available() >= 2) {
			size = stream.readShort();
			position += 2;
			return true;
		}
		
		return false;
	}
	
	public void readControl(DataInputStream stream) throws IOException
	{
		if(stream != null)
		{
			control = stream.readByte();
			position++;
		}
	}
	
	public boolean readType(DataInputStream stream) throws IOException
	{
		if(stream != null && stream.available() >= 2) {
			type = stream.readShort();
			position += 2;
			return true;
		}
		
		return false;
	}
	
	public void readEndControl(DataInputStream stream) throws IOException
	{
		if(stream != null)
		{
			endControl = stream.readByte();
			
			//Mark as finished (skip footer)
			isFinishedReading = true;
			
			//Check if valid
			isValidReading = control == endControl;
			
			position=0;
		}
	}	
	
	public void readData(DataInputStream stream) throws IOException
	{
		if(stream != null)
		{
			int avSize = stream.available();	//How much we can read
			int totalSize = size - MIN_SIZE;	//Total of how much needs to be read
			int offset = position - START_SIZE;	//Where we are starting
			int dataSize = totalSize - offset;	//How much we need to read		
			
			if(data == null && totalSize > 0)
			{
				data = new byte[totalSize];
			}
			
			if(avSize < dataSize)
			{
				stream.read(data, offset, avSize);
				position += avSize;
			}
			else if(avSize >= dataSize)
			{
				stream.read(data, offset, dataSize);
				position += dataSize;
			}		
		}
	}
	
		
	
	
}
