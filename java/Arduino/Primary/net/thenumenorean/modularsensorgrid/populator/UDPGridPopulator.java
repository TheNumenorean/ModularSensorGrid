/**
 * 
 */
package net.thenumenorean.modularsensorgrid.populator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import net.thenumenorean.modularsensorgrid.ModularSensorGrid;
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
		
		p = new Populator(getMSG(), port);
		p.start();

	}

	@Override
	public void stop() {
		p.halt();
		
	}
	
	private class Populator extends Thread {

		private ModularSensorGrid msg;
		private int port;
		private boolean stop;
		private DatagramSocket dsocket;

		public Populator(ModularSensorGrid msg, int port) {
			this.msg = msg;
			this.port = port;
			stop = false;
		}
		
		@Override
		public void run(){
			try {
	            dsocket = new DatagramSocket(port);
	            byte[] buffer = new byte[2048];
	 
	            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
	            while (!stop) {
	                dsocket.receive(packet);
	                String message = new String(buffer, 0, packet.getLength());
	                System.out.println(packet.getAddress().getHostName() + ": " + message);
	                packet.setLength(buffer.length);
	                
	                Sensor s = construct(message);
	                
	                if(s != null){
	                	msg.addSensor(s);
	                	System.out.println("Adding sensor " + s);
	                }
	            }
	            
	            dsocket.close();
	            
	        } catch (SocketException e) {
	        	if(!stop)
	        		e.printStackTrace();
	        } catch (IOException e) {
				e.printStackTrace();
			}

		}

		public void halt() {
			stop = true;
			dsocket.close();
		}

	}
	
	public Sensor construct(String s){
		
		Sensor sensor = null;
		
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

}
