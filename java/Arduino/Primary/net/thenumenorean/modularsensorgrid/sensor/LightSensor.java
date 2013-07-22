package net.thenumenorean.modularsensorgrid.sensor;

import java.io.IOException;
import java.net.UnknownHostException;

public class LightSensor extends Arduino {

	public LightSensor(String name, String ip, int port)
			throws UnknownHostException, IOException {
		super(name, ip, port);
		
	}
	
	public double getCurrent(){
		return Double.parseDouble(sendCommand("data").trim());
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
