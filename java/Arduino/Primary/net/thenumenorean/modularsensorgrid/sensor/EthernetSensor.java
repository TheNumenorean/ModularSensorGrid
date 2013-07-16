package net.thenumenorean.modularsensorgrid.sensor;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class EthernetSensor implements Sensor {

	private Socket sock;
	private String ip;
	private int port;
	
	public EthernetSensor(String ip, int port) throws UnknownHostException, IOException {
		this.ip = ip;
		this.port = port;
		reconnect();
	}
	
	public boolean isConnected(){
		return sock.isConnected() && !sock.isClosed();
	}
	
	public void reconnect() throws UnknownHostException, IOException{
		sock = new Socket(ip, port);
	}
	
	protected Socket getSocket(){
		return sock;
	}
	
	public String getIP(){
		return ip;
	}
	
	public int getPort(){
		return port;
	}

}
