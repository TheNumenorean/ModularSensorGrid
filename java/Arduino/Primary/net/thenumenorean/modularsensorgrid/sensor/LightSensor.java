package net.thenumenorean.modularsensorgrid.sensor;

import java.io.IOException;
import java.net.UnknownHostException;

public class LightSensor extends Arduino {

	public LightSensor(String name, double version, String ip, int port)
			throws UnknownHostException, IOException {
		super(name, version, ip, port);
		
	}
	
	public double getValue(){
		return Double.parseDouble(sendCommand("data", true).trim());
	}
	
	public void toggleLight(){
		sendCommand("light");
	}
	
	public void setLight(boolean on){
		sendCommand("light " + (on ? "on" : "off"));
	}

	@Override
	public boolean isAvailible() {
		// TODO Auto-generated method stub
		return false;
	}

}
