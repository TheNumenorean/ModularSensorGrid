import java.io.IOException;

import net.thenumenorean.modularsensorgrid.ModularSensorGrid;
import net.thenumenorean.modularsensorgrid.datacapture.GraphicalCaptureTool;
import net.thenumenorean.modularsensorgrid.sensors.LightSensor;
import net.thenumenorean.modularsensorgrid.usb.ardulink.ArdulinkGridPopulator;

public class USBTester {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		ModularSensorGrid msg = new ModularSensorGrid();
		ArdulinkGridPopulator.populate(msg);
		LightSensor l = (LightSensor) msg.getSensor("Sensor Name");
		
		msg.addDataCaptureTool(new GraphicalCaptureTool());
		msg.start();
		
		boolean lastStatus = false;
		
		while (System.in.available() < 1) {
			
			double num = l.getLightIntensity();
			//System.out.println(num);

			if (num > 700 && !lastStatus){
				lastStatus = true;
				l.setStatusLight(true);
			}else if(num <= 700 && lastStatus){
				lastStatus = false;
				l.setStatusLight(false);
			}

			Thread.sleep(100);
		}

		msg.destroy();
		
		System.exit(0);

	}

}
