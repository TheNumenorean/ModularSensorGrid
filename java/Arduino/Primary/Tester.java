import java.io.IOException;

import net.thenumenorean.modularsensorgrid.ModularSensorGrid;
import net.thenumenorean.modularsensorgrid.populator.UDPGridPopulator;
import net.thenumenorean.modularsensorgrid.sensor.LightSensor;


public class Tester {
	
	public static void main(String[] args) {
		ModularSensorGrid msg = new ModularSensorGrid();
		
		new UDPGridPopulator(msg, 22001).start();
		
		LightSensor l = (LightSensor)msg.getSensor("LightSensor1");
		while(l == null){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			l = (LightSensor)msg.getSensor("LightSensor1");
		}
		
		try {
			while (System.in.available() <= 0 || System.in.read() != '\n'){
				
				double num = l.getValue();
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
