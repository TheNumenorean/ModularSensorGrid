/**
 * 
 */
package net.thenumenorean.modularsensorgrid.sensors;

import java.util.ArrayList;

import net.thenumenorean.modularsensorgrid.connector.Connector;
import net.thenumenorean.modularsensorgrid.datacapture.DataCaptureTool;

/**
 * @author Francesco Macagno
 * 
 */
public abstract class Sensor {
	
	private Connector connector;
	private String name;
	private ArrayList<DataCaptureTool> dataCaptureTools;

	public Sensor(String name, Connector c){
		connector = c;
		this.name = name;
		dataCaptureTools = new ArrayList<DataCaptureTool>();
	}
	
	/**
	 * Tells this Sensor to start capturing data. This method should return immediately.
	 */
	public abstract void start();
	
	/**
	 * Tells this sensor to stop capturing data.
	 */
	public abstract void stop();
	
	/**
	 * Returns whether this sensor is currently running
	 * @return True if it is
	 */
	public abstract boolean isRunning();
	
	/**
	 * Adds a datacapturetool to this Sensor that will be used to store data 
	 * when start() is called.
	 * @param tool The tool to add
	 * @return 
	 */
	public boolean addDataCaptureTool(DataCaptureTool tool) {
		return dataCaptureTools.add(tool);
	}
	
	/**
	 * Removes the given DataCaptureTool from this Sensor.
	 * @param d DataCaptureTool to remove.
	 * @return True if successfull, false otherwise
	 */
	public boolean removeDataCaptureTool(DataCaptureTool d){
		return dataCaptureTools.remove(d);
	}
	
	/**
	 * Gets all the DataCaptureTools that are connected to this sensor.
	 * @return A potentially empty ArrayList.
	 */
	public ArrayList<DataCaptureTool> getDataCaptureTools(){
		return dataCaptureTools;
	}
	
	/**
	 * Gets the name of this sensor
	 * @return A name, or null if this sensor does not have one
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name for this Sensor.
	 * @param name The name to give this sensor
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Should be called when a sensor is no longer needed, this method should
	 * release all resources in use.
	 */
	public void destroy() {
		connector.destroy();
	}
	
	public Connector getConnector(){
		return connector;
	}
	
	/**
	 * Gets the type of this sensor
	 * @return A String that represents the type.
	 */
	public abstract String getType();
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Sensor))
			return false;
		return ((Sensor)o).getName().equals(this.getName());
	}
	
	public void setStatusLight(boolean on){
		connector.setDigitalPin(2, on);
	}
	
}
