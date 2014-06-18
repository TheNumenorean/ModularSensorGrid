import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.zu.ardulink.Link;
import org.zu.ardulink.event.DigitalReadChangeEvent;
import org.zu.ardulink.event.DigitalReadChangeListener;
import org.zu.ardulink.gui.SerialConnectionPanel;
import org.zu.ardulink.protocol.IProtocol;

public class Tester extends JFrame {

	public static void main(String[] args) {
		new Tester();

	}

	private Link l;

	public Tester() {

		setTitle("Input Signal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 330, 300);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(0, 0));

		final SerialConnectionPanel pan = new SerialConnectionPanel();
		contentPane.add(pan, BorderLayout.NORTH);

		JButton light = new JButton("Turn on LED");
		contentPane.add(light, BorderLayout.EAST);
		light.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				l.sendPowerPinSwitch(6, IProtocol.POWER_HIGH);

			}

		});

		final JTextArea name = new JTextArea();
		contentPane.add(name);

		JButton add = new JButton("Add Sensor");
		contentPane.add(add, BorderLayout.SOUTH);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				l = Link.createInstance(name.getText());
				l.connect(pan.getConnectionPort(),
						Integer.parseInt(pan.getBaudRate()));
				l.startListenDigitalPin(10);
				l.addDigitalReadChangeListener(new DigitalReadChangeListener() {

					@Override
					public int getPinListening() {
						return 10;
					}

					@Override
					public void stateChanged(DigitalReadChangeEvent arg0) {
						System.out.println(arg0);

					}

				});
				l.sendPowerPinSwitch(6, IProtocol.POWER_HIGH);
			}

		});

		this.setVisible(true);
	}

}
