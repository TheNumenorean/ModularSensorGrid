package net.thenumenorean.modularsensorgrid;

import java.util.ArrayList;
import java.util.List;

import net.thenumenorean.modularsensorgrid.sensor.Sensor;

public class ModularSensorGrid {
	
	private ArrayList<Sensor> sensors;

	public ModularSensorGrid() {
		sensors = new ArrayList<Sensor>();
	}
	
	public void addAll(List<Sensor> sensors){
		this.sensors.addAll(sensors);
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
