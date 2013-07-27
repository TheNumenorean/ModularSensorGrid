import java.io.IOException;

import net.thenumenorean.modularsensorgrid.GridCreator;
import net.thenumenorean.modularsensorgrid.ModularSensorGrid;
import net.thenumenorean.modularsensorgrid.sensor.LightSensor;

public class Tester {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		ModularSensorGrid msg = GridCreator.constructNewNetworkGrid(10);
		
		LightSensor l = (LightSensor) msg.getSensor("LightSensor1");

		while (l.isConnected() && (System.in.available() <= 0 || System.in.read() != '\n')) {
			
			long mil = System.currentTimeMillis();
			double num = l.getValue();
			mil = System.currentTimeMillis() - mil;
			System.out.println(num + " " + mil);

			if (num > 600)
				l.setLight(true);
			else
				l.setLight(false);

			Thread.sleep(100);
		}

		l.close();

	}

}
