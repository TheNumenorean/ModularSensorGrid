/**
 * 
 */
package net.thenumenorean.modularsensorgrid.sensor;

import java.util.ArrayList;

import net.thenumenorean.modularsensorgrid.datacapture.DataCaptureTool;

/**
 * @author The Numenorean
 * 
 */
public interface Sensor {
	
	/**
	 * Tells this Sensor to start capturing data. This method should return immediately.
	 */
	public void start();
	
	/**
	 * Tells this sensor to stop capturing data.
	 */
	public void stop();
	
	/**
	 * Returns whether this sensor is currently running
	 * @return True if it is
	 */
	public boolean isRunning();
	
	/**
	 * Adds a datacapturetool to this Sensor that will be used to store data 
	 * when start() is called.
	 * @param tool The tool to add
	 */
	public void addDataCaptureTool(DataCaptureTool tool);
	
	/**
	 * Removes the given DataCaptureTool from this Sensor.
	 * @param d DataCaptureTool to remove.
	 * @return True if successfull, false otherwise
	 */
	public boolean removeDataCaptureTool(DataCaptureTool d);
	
	/**
	 * Gets all the DataCaptureTools that are connected to this sensor.
	 * @return A potentially empty ArrayList.
	 */
	public ArrayList<DataCaptureTool> getDataCaptureTools();

	/**
	 * Tests whether the sensor is available for data retrieval.
	 * 
	 * @return True if it is, false otherwise.
	 */
	public boolean isAvailible();
	
	/**
	 * Gets the name of this sensor
	 * @return A name, or null if this sensor does not have one
	 */
	public String getName();
	
	/**
	 * Sets the name for this Sensor.
	 * @param name The name to give this sensor
	 * 
	 */
	public void setName(String name);
	
	/**
	 * Should be called when a sensor is no longer needed, this method should
	 * release all resources in use.
	 */
	public void destroy();
	
	/**
	 * Gets the type of this sensor
	 * @return A String that represents the type.
	 */
	public String getType();
	
}
