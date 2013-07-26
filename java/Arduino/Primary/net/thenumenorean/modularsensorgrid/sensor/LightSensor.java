package net.thenumenorean.modularsensorgrid.sensor;

import java.io.IOException;
import java.net.UnknownHostException;

import net.thenumenorean.modularsensorgrid.datacapture.DataCaptureTool;

public class LightSensor extends Arduino {

	public LightSensor(String name, double version, String ip, int port)
			throws UnknownHostException, IOException {
		super(name, version, ip, port);
		
	}
	
	public double getValue(){
		if(this.isConnected())
			return Double.parseDouble(sendCommand("data", true).trim());
		return -1;
	}
	
	public void toggleLight(){
		sendCommand("light");
	}
	
	public void setLight(boolean on){
		sendCommand("light " + (on ? "on" : "off"));
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDataCaptureTool(DataCaptureTool tool) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
