package net.thenumenorean.modularsensorgrid;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import net.thenumenorean.modularsensorgrid.datacapture.DataCaptureTool;
import net.thenumenorean.modularsensorgrid.sensor.EthernetSensor;
import net.thenumenorean.modularsensorgrid.sensor.Sensor;

public class NetworkedModularSensorGrid extends ModularSensorGrid {

	public static final int DEFAULT_TIME_PORT = 22002;
	
	private int timePort;
	private long start_time;
	private DatagramSocket timeSock;
	private TimeSynchronizer syncer;

	private int sync_interval;
	
	public NetworkedModularSensorGrid() throws SocketException{
		this(DEFAULT_TIME_PORT, 5000);
	}
	
	public NetworkedModularSensorGrid(int timeSyncPort, int sync_interval) throws SocketException{
		timePort = timeSyncPort;
		this.sync_interval = sync_interval;
		start_time = 0;
		
		timeSock = new DatagramSocket(timePort);
	}
	
	@Override
	public void addSensor(Sensor s){
		
		if(started)
			throw new SensorGridException("Cannot add sensor after start!");
		
		if(!(s instanceof EthernetSensor))
			return;
		super.addSensor(s);
	}
	
	public boolean synchronizeTime(){
		
		if(!started)
			throw new SensorGridException("Ethernet grid must be started to sync time!");
		
		try {
			
			byte[] data = ByteBuffer.allocate(4).putInt((int) getCurrentTime()).array();
			
			timeSock.send(new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), timePort));
			
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private long getCurrentTime() {
		return System.currentTimeMillis() - start_time;
	}

	public void start(){
		
		super.start();
		
		
		
		start_time = System.currentTimeMillis();
		
		syncer = new TimeSynchronizer(sync_interval);
		syncer.start();
		
	}
	
	public void stop(){
		syncer.halt();
		super.stop();
		
	}

	/**
	 * @return the timePort
	 */
	public int getTimePort() {
		return timePort;
	}
	
	public int getTimeSyncInterval(){
		return sync_interval;
	}
	
	/**
	 * Will only take affect on the next call to start() does not affect current 
	 * running synchonizinig job.
	 * @param interval Interval to set it to > 0 in millis
	 */
	public void setTimeSyncInterval(int interval){
		
		if(interval <= 0)
			throw new InvalidParameterException("Time sync interval cannot be 0 or less");
		
		sync_interval = interval;
	}
	
	/**
	 * @author Francesco
	 *
	 */
	private class TimeSynchronizer extends Thread {
		
		private boolean stopped;
		private int interval;
		
		public TimeSynchronizer(int interval){
			this.interval = interval;
		}

		@Override
		public void run(){
			while(!stopped){
				
				try {
					synchronizeTime();
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (SensorGridException e){
					break;
				}
			}
		}
		
		public void halt(){
			stopped = true;
		}
		
		public boolean getStopped(){
			return stopped;
		}
		
		public int getInterval(){
			return interval;
		}
	}
}
