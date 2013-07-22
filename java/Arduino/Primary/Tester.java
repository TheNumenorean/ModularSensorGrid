import java.io.IOException;
import net.thenumenorean.modularsensorgrid.ModularSensorGrid;
import net.thenumenorean.modularsensorgrid.sensor.LightSensor;


public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ModularSensorGrid msg = new ModularSensorGrid();
		
		//InternetScanner is = new InternetScanner();
		//is.startScan(10);
		
		try {
			msg.addSensor(new LightSensor("light", "192.168.1.177", 80));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		LightSensor l = (LightSensor)msg.getSensor("light");
		
		
		
		while (true){
			
			double num = l.getCurrent();
			System.out.println(num);
			
			if(num > 600)
				l.setLight(true);
			else
				l.setLight(false);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
