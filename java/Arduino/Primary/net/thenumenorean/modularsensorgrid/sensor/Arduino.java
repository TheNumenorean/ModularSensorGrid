package net.thenumenorean.modularsensorgrid.sensor;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class Arduino extends EthernetSensor {

	private String name;
	private double version;

	public Arduino(String name, double version, String ip, int port)
			throws UnknownHostException, IOException {
		super(ip, port);
		setName(name);
		this.version = version;
		
	}
	
	protected String sendCommand(String s){
		return sendCommand(s, false);
	}

	protected String sendCommand(String s, boolean wait) {
		
		s = s + "\n";

		try {
			if (!isConnected())
				reconnect();

			Socket sock = this.getSocket();
			PrintWriter out = new PrintWriter(new BufferedOutputStream(
					sock.getOutputStream()));

			out.write(s);
			out.flush();

			Thread.sleep(100);
			InputStream is = sock.getInputStream();
			
			if(wait)
				while(is.available() == 0)
					Thread.sleep(50);

			
			String resp = "";
			while (is.available() > 0)
				resp = resp + (char)is.read();

			return resp;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the version
	 */
	public double getVersion() {
		return version;
	}

}
