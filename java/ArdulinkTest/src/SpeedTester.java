import java.io.IOException;

import org.zu.ardulink.Link;
import org.zu.ardulink.RawDataListener;
import org.zu.ardulink.event.AnalogReadChangeEvent;
import org.zu.ardulink.event.AnalogReadChangeListener;
import org.zu.ardulink.event.DigitalReadChangeEvent;
import org.zu.ardulink.event.DigitalReadChangeListener;
import org.zu.ardulink.protocol.IProtocol;

public class SpeedTester {

	public static int last = IProtocol.POWER_LOW;

	public static void main(String[] args) throws InterruptedException,
			IOException {

		final Link l = Link.createInstance("asd");
		l.connect("COM3", 115200);

		Thread.sleep(3000);

		/*l.addRawDataListener(new RawDataListener(){

			@Override
			public void parseInput(String arg0, int arg1, int[] arg2) {
				for(int i = 0; i < arg1; i++)
					System.out.print((char)arg2[i]);
				System.out.println();
			}
			
		});
		
		
		l.addDigitalReadChangeListener(new DigitalReadChangeListener() {

			long lastTime;

			@Override
			public int getPinListening() {
				return 10;
			}

			@Override
			public void stateChanged(DigitalReadChangeEvent arg0) {
				System.out.println(System.currentTimeMillis() - lastTime);
				lastTime = System.currentTimeMillis();

			}

		});*/
		
		l.addAnalogReadChangeListener(new AnalogReadChangeListener() {

			@Override
			public int getPinListening() {
				return 1;
			}

			@Override
			public void stateChanged(AnalogReadChangeEvent arg0) {
				System.out.println(arg0.getValue());

			}

		});

		new Thread() {

			@Override
			public void run() {
				while (true) {
					last = last == IProtocol.POWER_HIGH ? IProtocol.POWER_LOW
							: IProtocol.POWER_HIGH;

					//System.out.println(last);
					l.sendPowerPinSwitch(6, last);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}.start();

		while (System.in.available() < 1)
			Thread.sleep(500);

		l.disconnect();
		System.exit(0);
	}

}
