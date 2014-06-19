package net.thenumenorean.modularsensorgrid;

import java.util.ArrayList;
import java.util.List;

import net.thenumenorean.modularsensorgrid.datacapture.DataCaptureTool;
import net.thenumenorean.modularsensorgrid.sensors.Sensor;

public class ModularSensorGrid {
	
	private ArrayList<Sensor> sensors;
	private ArrayList<DataCaptureTool> captureTools;
	protected boolean started;

	public ModularSensorGrid() {
		captureTools = new ArrayList<DataCaptureTool>();
		sensors = new ArrayList<Sensor>();
		started = false;
	}
	
	public void addAll(List<Sensor> sensors){
		for(Sensor s : sensors)
			addSensor(s);
	}
	
	public void addSensor(Sensor add){
		if(started)
			throw new SensorGridException("Cannot add Sensors after start!");
		
		for(Sensor s : sensors)
			if(s.getName().equals(add.getName()))
				return;
		sensors.add(add);
	}
	
	public Sensor getSensor(String name){
		for(Sensor s : sensors)
			if(s.getName().equals(name))
				return s;
		return null;
	}
	
	public boolean removeSensor(Sensor s){
		if(started)
			throw new SensorGridException("Cannot add Sensors after start!");
		return sensors.remove(s);
	}
	
	public boolean removeSensor(String name){
		for(Sensor s : sensors)
			if(s.getName().equals(name))
				return sensors.remove(s);
		return false;
	}
	
	public ArrayList<Sensor> getAllSensors(){
		return sensors;
	}
	
	public void destroy(){
		stop();
		for(Sensor s : sensors)
			s.destroy();
	}
	
	public void stop(){
		started = false;
		for(Sensor s : sensors){
			s.stop();
			s.getDataCaptureTools().clear();
		}
	}
	
	public void start(){
		started = true;
		for(Sensor s : sensors){
			for(DataCaptureTool d : captureTools)
				s.addDataCaptureTool(d);
			s.start();
		}
		
	}
	
	public void addDataCaptureTool(DataCaptureTool d){
		if(started)
			throw new SensorGridException("Cannot add DataCaptureTool after start!");
		
		captureTools.add(d);
	}
	
	public boolean removeDataCaptureTool(DataCaptureTool d){
		return captureTools.remove(d);
	}
	
	public ArrayList<DataCaptureTool> getDataCaptureTools(){
		return captureTools;
	}

}
