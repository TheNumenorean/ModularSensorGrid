/**
 * 
 */
package net.thenumenorean.modularsensorgrid.populator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import net.thenumenorean.modularsensorgrid.ModularSensorGrid;
import net.thenumenorean.modularsensorgrid.sensor.EthernetSensor;
import net.thenumenorean.modularsensorgrid.sensor.LightSensor;
import net.thenumenorean.modularsensorgrid.sensor.Sensor;

/**
 * @author The Numenorean
 *
 */
public class UDPGridPopulator extends GridPopulator {

	private Populator p;
	private int port;

	public UDPGridPopulator(ModularSensorGrid msg, int port) {
		super(msg);
		this.port = port;
	}

	/* (non-Javadoc)
	 * @see net.thenumenorean.modularsensorgrid.populator.GridPopulator#start()
	 */
	@Override
	public void start() {
		start(0);
	}
	
	@Override
	public void start(int sensorAmt) {
		
		p = new Populator(getMSG(), port, sensorAmt);
		p.start();

	}

	@Override
	public void stop() {
		p.halt();
		
	}
	
	private class Populator extends Thread {

		private ModularSensorGrid msg;
		private boolean stop;
		private DatagramSocket dsocket;
		private int sensorAmt, found;
		
		public Populator(ModularSensorGrid msg, int port, int sensorAmt) {
			this.msg = msg;
			stop = false;
			try {
				dsocket = new DatagramSocket(port);
				dsocket.setSoTimeout(0);
			} catch (SocketException e) {
				e.printStackTrace();
			}
			
			found = 0;
			this.sensorAmt = sensorAmt;
		}

		@Override
		public void run(){
			try {
	            
	            byte[] buffer = new byte[2048];
	 
	            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	            while (!stop && (sensorAmt <= 0 || sensorAmt > found)) {
	                try{
	                	dsocket.receive(packet);
	                } catch (SocketTimeoutException e){
	                	System.out.println(e.getMessage());
	                	continue;
	                }
	                String message = new String(buffer, 0, packet.getLength());
	                System.out.println(packet.getAddress().getHostName() + ": " + message);
	                packet.setLength(buffer.length);
	                
	                EthernetSensor s = construct(message);
	                
	                if(s != null && msg.getSensor(s.getName()) == null){
	                	msg.addSensor(s);
	                	System.out.println("Adding sensor " + s);
	                	found++;
	                }
	            }
	            
	            dsocket.close();
	            stop = true;
	            
	        } catch (SocketException e) {
	        	if(!stop)
	        		e.printStackTrace();
	        } catch (IOException e) {
				e.printStackTrace();
			}

		}

		public void halt() {
			stop = true;
			System.out.println("Killing");
			dsocket.close();
		}

	}
	
	public EthernetSensor construct(String s){
		
		EthernetSensor sensor = null;
		
		String[] args = s.split(" ");
		
		if(args.length < 6)
			return null;
		
		if(args[0].equals("Arduino")){
			
			if(args[1].equals("LightSensor")){
				
				try {
					sensor = new LightSensor(args[3], Double.parseDouble(args[2]), args[4], Integer.parseInt(args[5]));
				} catch (IOException e){
					System.out.println("Error loading sensor: " + e.getCause() + ": " + e.getMessage());
				}
				
			}
			
		}
		
		
		return sensor;
	}

	@Override
	public boolean running() {
		return !p.stop;
	}

}
