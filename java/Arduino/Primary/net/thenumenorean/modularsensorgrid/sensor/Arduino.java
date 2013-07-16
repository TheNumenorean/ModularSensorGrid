package net.thenumenorean.modularsensorgrid.sensor;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class Arduino extends EthernetSensor {

	private String name;

	public Arduino(String name, String ip, int port)
			throws UnknownHostException, IOException {
		super(ip, port);
		setName(name);
	}

	protected String sendCommand(String s) {
		
		s = s + "\n";

		try {
			if (!isConnected())
				reconnect();

			Socket sock = this.getSocket();
			PrintWriter out = new PrintWriter(new BufferedOutputStream(
					sock.getOutputStream()));

			out.write(s);
			out.flush();

			Thread.sleep(1000);

			InputStream is = sock.getInputStream();
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

}
