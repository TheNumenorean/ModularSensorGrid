package net.thenumenorean.modularsensorgrid;

import java.util.ArrayList;

import net.thenumenorean.modularsensorgrid.sensor.Sensor;

public class ModularSensorGrid {
	
	private ArrayList<Sensor> sensors;

	public ModularSensorGrid() {
		sensors = new ArrayList<Sensor>();
	}
	
	public void scan(){
		
	}
	
	public void addSensor(Sensor s){
		sensors.add(s);
	}
	
	public Sensor getSensor(String name){
		for(Sensor s : sensors)
			if(s.getName().equals(name))
				return s;
		return null;
	}

}
