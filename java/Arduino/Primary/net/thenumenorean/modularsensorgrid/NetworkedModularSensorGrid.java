package net.thenumenorean.modularsensorgrid;

import net.thenumenorean.modularsensorgrid.sensor.EthernetSensor;
import net.thenumenorean.modularsensorgrid.sensor.Sensor;

public class NetworkedModularSensorGrid extends ModularSensorGrid {
	
	@Override
	public void addSensor(Sensor s){
		if(!(s instanceof EthernetSensor))
			return;
		super.addSensor(s);
	}
	
	public void synchronizeTime(){
		
		DatagramSocket
		
	}

}
