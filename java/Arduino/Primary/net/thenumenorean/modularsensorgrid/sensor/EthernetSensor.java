package net.thenumenorean.modularsensorgrid.sensor;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.thenumenorean.modularsensorgrid.datacapture.DataCaptureTool;

public abstract class EthernetSensor implements Sensor {

	private Socket sock;
	private String ip;
	private int port;
	protected final String hello;
	private ArrayList<DataCaptureTool> captureTools;

	public EthernetSensor(String ip, int port) throws UnknownHostException,
			IOException {
		this.ip = ip;
		this.port = port;
		hello = reconnect();

	}

	public boolean isConnected() {
		return sock.isConnected() && !sock.isClosed();
	}

	@Override
	public boolean isAvailible() {
		return isConnected();
	}

	public String reconnect() throws UnknownHostException, IOException {
		
		sock = new Socket(ip, port);

		while (sock.getInputStream().available() == 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		String tmp = "";

		while (sock.getInputStream().available() > 0)
			tmp = tmp + (char) sock.getInputStream().read();

		return tmp;
	}

	protected Socket getSocket() {
		return sock;
	}

	public String getIP() {
		return ip;
	}

	public int getPort() {
		return port;
	}
	
	public void close(){
		try {
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void destroy(){
		try {
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		return this.getClass().getName() + " " + getName();
	}
	
	@Override
	public void addDataCaptureTool(DataCaptureTool d){
		captureTools.add(d);
	}
	
	@Override
	public boolean removeDataCaptureTool(DataCaptureTool d){
		return captureTools.remove(d);
	}
	
	@Override
	public ArrayList<DataCaptureTool> getDataCaptureTools(){
		return captureTools;
	}
	
	@Override
	public String getType(){
		return this.getClass().getName();
	}

}
