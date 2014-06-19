/**
 * 
 */
package net.thenumenorean.modularsensorgrid.datacapture;

import java.awt.Color;
import java.awt.Graphics;
import java.util.AbstractMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

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
	private TreeMap<String, Boolean> binaryTracker;

	/**
	 * 
	 */
	public GraphicalCaptureTool() {
		sensors = new TreeMap<String, SensorContainer>();
		binaryTracker = new TreeMap<String, Boolean>();

		this.setSize(700, 700);

		height = 150;
		width = 600;

		this.setVisible(true);


	}

	@Override
	public void addData(Sensor s, String dataName, long time, int value) {
		addData(s, dataName, time, (double) value);
	}

	@Override
	public void addData(Sensor s, String dataName, long time, boolean value) {
		Boolean b = binaryTracker.get(s.getName() + "." + dataName);
		if(b == null)
			binaryTracker.put(s.getName() + "." + dataName, value);
		else if(b != value){
			addData(s, dataName, time, value ? 0.0 : 1.0);
			binaryTracker.put(s.getName() + "." + dataName, value);
		}
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
		repaint();
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
			this.setSize(width, height+3);

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

		private ConcurrentLinkedQueue<Entry<Long, Double>> values;
		private int height;
		private int width;
		private Color c;
		private double highest;
		private int timeRange;
		private long lastTime;
		private long lastUpdate;

		public SensorData(Color color) {
			values = new ConcurrentLinkedQueue<Entry<Long, Double>>();
			c = color;
			timeRange = 10000;
		}

		public void setDimensions(int width, int height) {
			this.width = width;
			this.height = height;
		}

		public void setColor(Color c) {
			this.c = c;
		}

		public void addValue(long time, double value) {
			values.add(new AbstractMap.SimpleEntry<Long, Double>(time, value));
			lastTime = time;
			lastUpdate = System.currentTimeMillis();
			while(values.peek().getKey() - time > timeRange)
				values.remove();
			if (value > highest)
				highest = value;
		}

		public void paint(Graphics g) {

			g.setColor(c);
			int oldX = -1, lastHeight = -1;

			double scalar = height / highest;
				
			//System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			long timeSince = System.currentTimeMillis() - lastUpdate;
			
			Iterator<Entry<Long, Double>> it = values.iterator();
			while (it.hasNext()) {
				
				Entry<Long, Double> next = it.next();
				int newHeight = (int) ((Double) next.getValue() * scalar);
				
				int x = (int) (width * (((double)(timeSince + lastTime - next.getKey())) / timeRange));
				
				if(oldX == -1)
					oldX = x;
				
				if(lastHeight == -1)
					lastHeight = newHeight;
				
				g.drawLine(width - oldX, height - lastHeight + 1, width - x, height - newHeight + 1);
				
				//System.out.println(lastTime + "\t" + x + "\t" + next.getKey() + "\t" + newHeight + "\t" + lastHeight);

				oldX = x;
				lastHeight = newHeight;
			}
			
			if(timeSince > 0){
				
				g.drawLine(width - oldX, height - lastHeight + 1, width, height - lastHeight + 1);
				
			}

		}

	}

}
