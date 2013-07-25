package net.thenumenorean.modularsensorgrid;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import net.thenumenorean.modularsensorgrid.datacapture.DataCaptureTool;
import net.thenumenorean.modularsensorgrid.sensor.EthernetSensor;
import net.thenumenorean.modularsensorgrid.sensor.Sensor;

public class NetworkedModularSensorGrid extends ModularSensorGrid {
	
	public static final int DEFAULT_TIME_PORT = 22002;
	
	private int timePort;
	private long start_time;
	private DatagramSocket timeSock;
	
	public NetworkedModularSensorGrid() throws SocketException{
		this(DEFAULT_TIME_PORT);
	}
	
	public NetworkedModularSensorGrid(int timeSyncPort) throws SocketException{
		timePort = timeSyncPort;
		start_time = 0;
		
		timeSock = new DatagramSocket(timePort);
	}
	
	@Override
	public void addSensor(Sensor s){
		
		if(started)
			throw new EthernetSensorGridException("Cannot add sensor after start!");
		
		if(!(s instanceof EthernetSensor))
			return;
		super.addSensor(s);
	}
	
	public boolean synchronizeTime(){
		
		if(!started)
			throw new EthernetSensorGridException("Ethernet grid must be started to sync time!");
		
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
		synchronizeTime();
	}
	
	public void stop(){
		super.stop();
	}

	/**
	 * @return the timePort
	 */
	public int getTimePort() {
		return timePort;
	}

}
