/**
 * 
 */
package net.thenumenorean.modularsensorgrid.datacapture;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;

import net.thenumenorean.modularsensorgrid.sensors.Sensor;

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
		
		new Thread(){
			@Override
			public void run(){
				
				while(1 == 1){
					repaint();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	@Override
	public void addData(Sensor s, String dataName, long time, int value) {

		addData(s, dataName, time, (double) value);
	}

	@Override
	public void addData(Sensor s, String dataName, long time, boolean value) {
		addData(s, dataName, time, value ? 1.0 : 0.0);
	}

	@Override
	public void addData(Sensor s, String dataName, long time, long value) {
		addData(s, dataName, time, (double) value);
	}

	@Override
	public void addData(Sensor s, String dataName, long time, double value) {
		SensorContainer list = getSensorContainer(s);
		list.addValue(dataName, time, value);
	}

	private SensorContainer getSensorContainer(Sensor s) {
		SensorContainer list = sensors.get(s.getName());
		if (list == null) {
			list = new SensorContainer();
			sensors.put(s.getName(), list);
			this.getContentPane().add(list);
			repaint();
		}

		return list;
	}

	private class SensorContainer extends JComponent {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2721174287672393834L;
		TreeMap<String, SensorData> data;
		private Queue<Color> colors;

		public SensorContainer() {
			data = new TreeMap<String, SensorData>();

			this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
			this.setBackground(Color.GRAY);
			this.setSize(width, height);

			colors = new LinkedList<Color>();

			colors.add(Color.BLUE);
			colors.add(Color.CYAN);
			colors.add(Color.DARK_GRAY);
			colors.add(Color.GRAY);
			colors.add(Color.GREEN);
			colors.add(Color.LIGHT_GRAY);
			colors.add(Color.MAGENTA);
			colors.add(Color.ORANGE);
			colors.add(Color.PINK);
			colors.add(Color.RED);
			colors.add(Color.WHITE);
			colors.add(Color.YELLOW);
			colors.add(Color.BLACK);

		}

		public void addValue(String dataName, long time, double value) {

			SensorData data = getSensorData(dataName);
			if (data == null) {
				data = new SensorData(colors.poll());
				addSensorData(dataName, data);
			}

			data.addValue(time, value);

		}

		public SensorData getSensorData(String name) {
			return data.get(name);
		}

		public void addSensorData(String name, SensorData sd) {
			data.put(name, sd);
			sd.setDimensions(width, height);
			this.invalidate();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			for (SensorData sd : data.values())
				sd.paint(g);
		}

	}

	private class SensorData {

		private Map<Long, Double> values;
		private int height;
		private int width;
		private Color c;
		private int range;
		private double highest;
		int lastHeight;

		public SensorData(Color color) {
			values = Collections.synchronizedMap(new TreeMap<Long, Double>());
			c = color;
			range = 250;
			lastHeight = 0;
		}

		public void setDimensions(int width, int height) {
			this.width = width;
			this.height = height;
		}

		public void setColor(Color c) {
			this.c = c;
		}

		public void setRange(int range) {
			this.range = range;
		}

		public void addValue(long time, double value) {
			values.put(time, value);
			if (value > highest)
				highest = value;
		}

		public void paint(Graphics g) {

			g.setColor(c);
			double pixls = width / range;
			int margin = 0;
			
			double scalar = height / highest;
			
			
			for (int y = 1; y <= 300 && y <= values.size(); y++) {
				int newHeight = (int) ((Double)values.values().toArray()[values.size() - y] * scalar);
				//System.out.println(newHeight + " " + margin + " " + highest);
				g.drawLine(width - margin, height - lastHeight, width
						- (int) (margin += pixls), height - newHeight);
				
				lastHeight = newHeight;
			}

		}

	}

}
