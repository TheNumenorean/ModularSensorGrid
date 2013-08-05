import java.io.IOException;

import net.thenumenorean.modularsensorgrid.GridCreator;
import net.thenumenorean.modularsensorgrid.ModularSensorGrid;
import net.thenumenorean.modularsensorgrid.sensor.LightSensor;

public class Tester {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		ModularSensorGrid msg = GridCreator.constructNewNetworkGrid(10000, 1);
		System.err.println("Done");
		LightSensor l = (LightSensor) msg.getSensor("LightSensor1");
		
		msg.start();
		
		Thread.sleep(2000);

		while (l.isConnected() && (System.in.available() <= 0 || System.in.read() != '\n')) {
			
			long mil = System.currentTimeMillis();
			double num = l.getValue();
			if(num == -1)
				break;
			mil = System.currentTimeMillis() - mil;
			System.out.println(num + " " + mil);

			if (num > 600)
				l.setLight(true);
			else
				l.setLight(false);

			Thread.sleep(100);
		}
		
		msg.stop();

		msg.destroy();

	}

}
