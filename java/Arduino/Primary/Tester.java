import java.io.IOException;
import java.net.UnknownHostException;

import net.thenumenorean.modularsensorgrid.InternetScanner;
import net.thenumenorean.modularsensorgrid.ModularSensorGrid;
import net.thenumenorean.modularsensorgrid.sensor.LightSensor;


public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ModularSensorGrid msg = new ModularSensorGrid();
		
		InternetScanner is = new InternetScanner();
		is.startScan(10);
		
		try {
			msg.addSensor(new LightSensor("light", "192.168.1.177", 80));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		LightSensor l = (LightSensor)msg.getSensor("light");
		
		System.out.println(l.getCurrent());
		
		while (true){
			l.toggleLight();
			System.out.println(l.getCurrent());
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
