package com.pi4j.io.gpio;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.pi4j.io.gpio.event.GpioPinListener;
import com.pi4j.io.gpio.trigger.GpioTrigger;

public class GpioPinDigitalInputMock implements GpioPinDigitalInput {

	protected static final long TOGGLE_LENGTH = 250;	//250 milliseconds	
	protected boolean pinIsHigh;
	protected long lastTime;
	
	public GpioPinDigitalInputMock()
	{
		pinIsHigh = false;
		lastTime = 0;
	}
	
	public GpioPinDigitalInputMock(boolean isHigh)
	{
		pinIsHigh = isHigh;
		lastTime = 0;
	}
	
	
	protected void togglePin()
	{
		long now = System.currentTimeMillis();
		
		if(lastTime == 0)
		{
			lastTime = now;
		}
		else
		{
			if(now - lastTime > TOGGLE_LENGTH)
			{
				pinIsHigh = !pinIsHigh;
				lastTime = now;
			}
		}
	}
	
	
	@Override
	public PinState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isHigh() {
		boolean result = pinIsHigh;
		togglePin();
		return result;
	}

	@Override
	public boolean isLow() {
		boolean result = !pinIsHigh;
		togglePin();
		return result;
	}

	@Override
	public boolean isState(PinState arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addListener(GpioPinListener... arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(List<? extends GpioPinListener> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearProperties() {
		// TODO Auto-generated method stub

	}

	@Override
	public void export(PinMode arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void export(PinMode arg0, PinState arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<GpioPinListener> getListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PinMode getMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pin getPin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProperty(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProperty(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GpioProvider getProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PinPullResistance getPullResistance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GpioPinShutdown getShutdownOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasListener(GpioPinListener... arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasProperty(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isExported() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMode(PinMode arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPullResistance(PinPullResistance arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeAllListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(GpioPinListener... arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeListener(List<? extends GpioPinListener> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeProperty(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMode(PinMode arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setName(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setProperty(String arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPullResistance(PinPullResistance arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShutdownOptions(GpioPinShutdown arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShutdownOptions(Boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShutdownOptions(Boolean arg0, PinState arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShutdownOptions(Boolean arg0, PinState arg1,
			PinPullResistance arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShutdownOptions(Boolean arg0, PinState arg1,
			PinPullResistance arg2, PinMode arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTag(Object arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unexport() {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTrigger(GpioTrigger... arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addTrigger(List<? extends GpioTrigger> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<GpioTrigger> getTriggers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAllTriggers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTrigger(GpioTrigger... arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTrigger(List<? extends GpioTrigger> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getDebounce(PinState arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasDebounce(PinState arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDebounce(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDebounce(int arg0, PinState... arg1) {
		// TODO Auto-generated method stub

	}

}
