package net.thenumenorean.modularsensorgrid.usb.ardulink;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import net.thenumenorean.modularsensorgrid.connector.Connector;
import net.thenumenorean.modularsensorgrid.sensors.LightSensor;
import net.thenumenorean.modularsensorgrid.sensors.Sensor;

import org.zu.ardulink.Link;
import org.zu.ardulink.gui.SerialConnectionPanel;

public class ArdulinkGridPopulatorFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5569451493565140337L;
	private SerialConnectionPanel serialConnectionPanel;
	private ArrayList<Sensor> sensors;
	private Object wait;
	private JTextField name;
	private JButton add;
	private JComboBox type;
	private JPanel left;
	private JPanel right;
	private JButton finish;
	private JScrollPane scroll;

	public static final String[] SENSOR_TYPES = { "LightSensor" };

	public ArdulinkGridPopulatorFrame(Object wait) {

		this.wait = wait;
		sensors = new ArrayList<Sensor>();

		this.setSize(800, 650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = this.getContentPane();
		content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));

		left = new JPanel();
		left.setMaximumSize(new Dimension(400, 600));
		right = new JPanel();
		right.setSize(300, 600);

		scroll = new JScrollPane(right);
		
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));

		serialConnectionPanel = new SerialConnectionPanel();
		serialConnectionPanel.setMaximumSize(new Dimension(400, 100));
		left.add(serialConnectionPanel);

		type = new JComboBox(SENSOR_TYPES);
		type.setMaximumSize(new Dimension(200, 20));
		left.add(type);

		name = new JTextField("Sensor Name");
		name.setColumns(30);
		name.setMaximumSize(new Dimension(200, 40));
		left.add(name);

		add = new JButton("Add Sensor");
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try { // This is not because i'm stupid, but because
						// getConnectionPort throws a nullPointerException when
						// it is empty, but in case its fixed.
					if (serialConnectionPanel.getConnectionPort() == null
							|| serialConnectionPanel.getConnectionPort()
									.isEmpty())
						throw new NullPointerException();
				} catch (NullPointerException e) {
					JOptionPane.showMessageDialog(
							ArdulinkGridPopulatorFrame.this,
							"Please select a port.");
					return;
				}

				// add.setEnabled(false);
				for (Sensor s : sensors) {
					if (s.getName().equals(name.getText())) {
						JOptionPane.showMessageDialog(
								ArdulinkGridPopulatorFrame.this,
								"Two sensors cannot have the same name!");
						return;
					}

					if (((ArdulinkConnector) s.getConnector()).getPort()
							.equals(serialConnectionPanel.getConnectionPort())) {
						JOptionPane.showMessageDialog(
								ArdulinkGridPopulatorFrame.this,
								"Two sensors cannot have the same port!");
						return;
					}

				}

				Link l = Link.createInstance(name.getText());

				try {
					l.connect(serialConnectionPanel.getConnectionPort());
					Thread.sleep(2000);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (!l.isConnected()) {
					JOptionPane.showMessageDialog(
							ArdulinkGridPopulatorFrame.this,
							"Could not connect to Arduino!");
					return;
				}

				String typeName = (String) type.getSelectedItem();
				Connector connector = new ArdulinkConnector(l,
						serialConnectionPanel.getConnectionPort());
				if (typeName.equals("LightSensor"))
					sensors.add(new LightSensor(name.getText(), connector));

				refreshRightPane();

				// add.setEnabled(true);

			}

		});
		left.add(add);

		finish = new JButton("Finish");
		finish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArdulinkGridPopulatorFrame.this.setVisible(false);
				synchronized (ArdulinkGridPopulatorFrame.this.wait) {
					ArdulinkGridPopulatorFrame.this.wait.notifyAll();
				}

			}

		});
		
		left.add(finish);

		content.add(left);
		content.add(scroll);

		setVisible(true);
	}

	protected void refreshRightPane() {

		right.removeAll();

		for (Sensor s : sensors)
			right.add(new SensorGUI(s));
		
		this.validate();
	    this.repaint();
	}

	public ArrayList<Sensor> getSensors() {
		return sensors;
	}

	private class SensorGUI extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -7409672641888632049L;
		private Sensor s;

		public SensorGUI(Sensor s) {
			this.s = s;
			this.setMaximumSize(new Dimension(300, 70));
			setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

			add(new JLabel("Sensor Name: " + s.getName()));
			add(new JLabel("Sensor Port: "
					+ ((ArdulinkConnector) s.getConnector()).getPort()));
			add(new JLabel("Sensor Type: " + s.getType()));
			
			JButton del = new JButton("Delete");
			del.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					SensorGUI.this.s.destroy();
					sensors.remove(SensorGUI.this.s);
					refreshRightPane();
				}
				
			});
			add(del);
		}

	}

}