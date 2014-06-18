import java.io.IOException;

import net.thenumenorean.modularsensorgrid.ModularSensorGrid;
import net.thenumenorean.modularsensorgrid.datacapture.GraphicalCaptureTool;
import net.thenumenorean.modularsensorgrid.sensors.LightSensor;

public class USBTester {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		ModularSensorGrid msg = new ModularSensorGrid();
		LightSensor l = (LightSensor) msg.getSensor("LightSensor1");
		
		msg.addDataCaptureTool(new GraphicalCaptureTool());
		msg.start();
		
		while (System.in.available() < 1) {
			
			long mil = System.currentTimeMillis();
			double num = l.getLightIntensity();
			if(num == -1)
				break;
			mil = System.currentTimeMillis() - mil;
			System.out.println(num + " " + mil);

			if (num > 700)
				l.setStatusLight(true);
			else
				l.setStatusLight(false);

			Thread.sleep(100);
		}

		msg.destroy();
		
		System.exit(0);

	}

}
