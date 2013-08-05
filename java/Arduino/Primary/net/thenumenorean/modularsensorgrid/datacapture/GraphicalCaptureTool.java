/**
 * 
 */
package net.thenumenorean.modularsensorgrid.datacapture;

import java.awt.Color;
import java.awt.Graphics;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;

import net.thenumenorean.modularsensorgrid.sensor.Sensor;

/**
 * @author The Numenorean
 * 
 */
public class GraphicalCaptureTool extends JFrame implements DataCaptureTool {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2893656367422723997L;
	private TreeMap<String, SensorContainer> sensors;

	/**
	 * 
	 */
	public GraphicalCaptureTool() {
		sensors = new TreeMap<String, SensorContainer>();
		
		this.setSize(700, 700);
		
		
		
		this.setVisible(true);
	}

	@Override
	public void addData(Sensor s, String dataName, long time, int value) {
		SensorContainer list = getSensorContainer(s);

		SensorData<Integer> data = (SensorData<Integer>) list.getSensorData(dataName);
		if (data == null) {
			data = new SensorData<Integer>();
			list.addSensorData(dataName, data);
		}

		data.addValue(time, value);
	}

	@Override
	public void addData(Sensor s, String dataName, long time, boolean value) {
		SensorContainer list = getSensorContainer(s);

		SensorData<Boolean> data = (SensorData<Boolean>) list.getSensorData(dataName);
		if (data == null) {
			data = new SensorData<Boolean>();
			list.addSensorData(dataName, data);
		}

		data.addValue(time, value);

	}

	@Override
	public void addData(Sensor s, String dataName, long time, long value) {
		SensorContainer list = getSensorContainer(s);

		SensorData<Long> data = (SensorData<Long>) list.getSensorData(dataName);
		if (data == null) {
			data = new SensorData<Long>();
			list.addSensorData(dataName, data);
		}

		data.addValue(time, value);

	}

	@Override
	public void addData(Sensor s, String dataName, long time, double value) {
		SensorContainer list = getSensorContainer(s);

		SensorData<Double> data = (SensorData<Double>) list.getSensorData(dataName);
		if (data == null) {
			data = new SensorData<Double>();
			list.addSensorData(dataName, data);
		}

		data.addValue(time, value);

	}

	private SensorContainer getSensorContainer(Sensor s) {
		SensorContainer list = sensors.get(s.getName());
		if (list == null){
			list = new SensorContainer();
			sensors.put(s.getName(),list);
			this.getContentPane().add(list);
			repaint();
		}
					
		return list;
	}
	
	private class SensorContainer  extends JComponent {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2721174287672393834L;
		TreeMap<String, SensorData<?>> data;
		
		public SensorContainer(){
			data = new TreeMap<String, SensorData<?>>();
			
			this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			this.setBackground(Color.GRAY);
			this.setSize(600, 100);
		}
		
		public SensorData<?> getSensorData(String name){
			return data.get(name);
		}
		
		public void addSensorData(String name, SensorData<?> sd){
			data.put(name, sd);
		}
		
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			
		}
		
	}

	private class SensorData<A> {

		private TreeMap<Long, A> values;

		public SensorData() {
			values = new TreeMap<Long, A>();
		}

		public void addValue(long time, A value) {
			values.put(time, value);
			repaint();
		}

	}

}
