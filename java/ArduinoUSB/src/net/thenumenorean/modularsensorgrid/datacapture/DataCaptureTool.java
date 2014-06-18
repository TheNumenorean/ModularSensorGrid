/**
 * 
 */
package net.thenumenorean.modularsensorgrid.datacapture;

import net.thenumenorean.modularsensorgrid.sensors.Sensor;

/**
 * @author The Numenorean
 *
 */
public interface DataCaptureTool {
	
	//public void addData(Sensor s, String dataName, long time, Object value);
	public void addData(Sensor s, String dataName, long time, int value);
	public void addData(Sensor s, String dataName, long time, boolean value);
	public void addData(Sensor s, String dataName, long time, long value);
	public void addData(Sensor s, String dataName, long time, double value);
	
}
