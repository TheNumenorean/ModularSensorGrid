/**
 * 
 */
package net.thenumenorean.modularsensorgrid.populator;

import net.thenumenorean.modularsensorgrid.ModularSensorGrid;

/**
 * @author The Numenorean
 *
 */
public abstract class GridPopulator {
	
	private ModularSensorGrid msg;

	public GridPopulator(ModularSensorGrid msg){
		this.msg = msg;
		
	}

	public ModularSensorGrid getMSG() {
		return msg;
	}
	
	/**
	 * Starts searching
	 */
	public abstract void start();
	
	/**
	 * Searches until the given number of sensors are found
	 * @param sensors Number of sensors to look for, or 0 to disable it
	 */
	public abstract void start(int sensors);
	
	/**
	 * Stops the search
	 */
	public abstract void stop();
	
	/**
	 * Gets whether this is running
	 * @return 
	 */
	public abstract boolean running();
}
