package net.thenumenorean.modularsensorgrid.usb.ardulink;

import net.thenumenorean.modularsensorgrid.ModularSensorGrid;

public class ArdulinkGridPopulator {
	
	public static void populate(ModularSensorGrid msg){
		
		Object wait = new Object();
		ArdulinkGridPopulatorFrame frame = new ArdulinkGridPopulatorFrame(wait);
		
		try {
			synchronized(wait){
				wait.wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		msg.addAll(frame.getSensors());
	}
	
	

}
