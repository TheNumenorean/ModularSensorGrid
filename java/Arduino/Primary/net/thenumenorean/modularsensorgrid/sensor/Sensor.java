/**
 * 
 */
package net.thenumenorean.modularsensorgrid.sensor;

/**
 * @author The Numenorean
 * 
 */
public interface Sensor {

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

}
