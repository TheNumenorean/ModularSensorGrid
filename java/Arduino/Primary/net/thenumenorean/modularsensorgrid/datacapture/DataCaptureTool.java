/**
 * 
 */
package net.thenumenorean.modularsensorgrid.datacapture;

/**
 * @author The Numenorean
 *
 */
public interface DataCaptureTool {
	
	public void addData(long time, String sensorName, Object value);
	public void addData(long time, String sensorName, int value);
	public void addData(long time, String sensorName, boolean value);
	public void addData(long time, String sensorName, long value);
	public void addData(long time, String sensorName, double value);
	
}
