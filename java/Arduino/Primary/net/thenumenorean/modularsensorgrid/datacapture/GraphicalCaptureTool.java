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
import javax.swing.JPanel;

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
	private int height, width;

	/**
	 * 
	 */
	public GraphicalCaptureTool() {
		sensors = new TreeMap<String, SensorContainer>();
		
		this.setSize(700, 700);
		
		height = 150;
		width = 600;
		
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
		private JPanel container;
		
		public SensorContainer(){
			data = new TreeMap<String, SensorData<?>>();
			
			this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			this.setBackground(Color.GRAY);
			this.setSize(width, height);
			
			container = new JPanel();
		}
		
		public SensorData<?> getSensorData(String name){
			return data.get(name);
		}
		
		public void addSensorData(String name, SensorData<?> sd){
			data.put(name, sd);
			sd.setDimensions(width, height, 100);
		}
		
		@Override
		protected void paintComponent(Graphics g){
			super.paintComponent(g);

			for(SensorData<?> sd : data.values())
				sd.paint(g);
			
		}
		
	}

	private class SensorData<A> {

		private TreeMap<Long, A> values;
		private int height;
		private int width;
		private int increment;
		private Color c;
		private int show;
		private A highest;

		public SensorData() {
			values = new TreeMap<Long, A>();
			c = Color.BLACK;
			show = 250;
		}

		public void setDimensions(int width, int height) {
			this.width = width;
			this.height = height;
		}
		
		public void setColor(Color c){
			this.c = c;
		}
		
		public void setShow(int show){
			this.show = show;
		}

		public void addValue(long time, A value) {
			values.put(time, value);
			if(value > highest)
				value = highest;
			repaint();
		}
		
		public void paint(Graphics g, int margin){
			
			g.setColor(c);
			
			for(int y = 0; y < 250; y++){
				
			}
			g.drawLine(0, 0, 100, 100);
		}

	}

}
