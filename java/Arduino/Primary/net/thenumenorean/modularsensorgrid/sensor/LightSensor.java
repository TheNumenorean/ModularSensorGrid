package net.thenumenorean.modularsensorgrid.sensor;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import net.thenumenorean.modularsensorgrid.datacapture.DataCaptureTool;

public class LightSensor extends Arduino {

	private SensorRunner runner;
	private int lastSensorValue;
	private long lastSensorRead;

	public LightSensor(String name, double version, String ip, int port, int dataPort)
			throws UnknownHostException, IOException {
		super(name, version, ip, port, dataPort);
		
		lastSensorValue = -1;
		lastSensorRead = -1;
		
	}
	
	public double getValue(){
		
		//if(!this.isConnected())
		//	this.reconnect();
		
		if(this.isRunning())
			return lastSensorValue;
		
		if(this.isConnected())
			return Double.parseDouble(sendCommand("data", true).trim());
		return -1;
	}
	
	public void toggleLight(){
		sendCommand("light");
	}
	
	public void setLight(boolean on){
		sendCommand("light " + (on ? "on" : "off"));
	}

	@Override
	public void start() {
		/*if(!this.isConnected()){
			try {
				this.reconnect();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		
		try {
			runner = new SensorRunner(this.getDataSocket());
		} catch (Exception e) {
			System.out.println("Couldn't connect data socket!");
			e.printStackTrace();
			return;
		}
		
		runner.start();
	}

	@Override
	public void stop() {
		if(runner != null)
			runner.end();
	}
	
	private class SensorRunner extends Thread{
		
		private boolean stop;
		private InputStream in;
		private Socket sock;
		
		public SensorRunner(Socket sock){
			stop = true;
			this.sock = sock;
		}

		@Override
		public void run(){
			stop = false;
			try {
				in = sock.getInputStream();
				String data = "";
				int ch;
				while(!stop && (ch = in.read()) != -1){
					if(ch == '\n'){
						
						interpretData(data);
						data = "";
						
					} else {
						data = data + (char)ch;
					}
				}
				
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				stop = true;
			}
			
		}

		public void end() {
			stop = true;
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	private void interpretData(String data) {
		//Data should be like this:
		//<timeMS> <value>
		String[] values = data.trim().split(" ");
		
		if(values.length < 2){
			System.out.println("Invalid sensor data! Invalid number of args");
			return;
		}
		
		long time;
		try {
			time = Long.parseLong(values[0]);
		} catch (NumberFormatException e){
			System.out.println("Invalid sensor data! Invalid time!");
			return;
		}
		
		int value;
		try {
			value = Integer.parseInt(values[1]);
		} catch (NumberFormatException e){
			System.out.println("Invalid sensor data! Invalid value!");
			return;
		}
		
		lastSensorValue = value;
		lastSensorRead = time;
		
		for(DataCaptureTool tool : this.getDataCaptureTools()){
			tool.addData(this, "Light Resistor", time, value);
		}
		
		System.out.println("Added data with time " + time + " and value " + value);
		
	}

	@Override
	public boolean isRunning() {
		if(runner == null)
			return false;
		return runner.isAlive();
	}

}
