package com.jjsimpson.aaengine.arduino;

import com.jjsimpson.aaengine.communication.RemoteCommand;

public interface ArduinoCommandWriter
{
	public void sendRemoteCommand(RemoteCommand com);
	public void sendRemoteCommand(RemoteCommand com, boolean isDebug);
}
