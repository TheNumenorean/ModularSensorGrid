package net.thenumenorean.modularsensorgrid;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class InternetScanner {

	private RunnableScanner scanner;

	public InternetScanner() {

	}

	public void startScan(int seconds) {
		scanner = new RunnableScanner(seconds);
		scanner.start();
	}

	public void stopScanner() {
		scanner.end();
	}

	private class RunnableScanner extends Thread {

		DatagramSocket socket;
		private long total_time;

		public RunnableScanner(int seconds) {
			total_time = seconds * 1000;
		}

		public void end() {
			socket.close();
		}

		@Override
		public void run() {

			long start = System.currentTimeMillis();
			
			//TODO: Start task to terminate in time

			while (System.currentTimeMillis() - start < total_time) {
				try {
					socket = new DatagramSocket(8888,
							InetAddress.getByName("0.0.0.0"));

					socket.setBroadcast(true);

					byte[] recvBuf = new byte[15000];

					DatagramPacket packet = new DatagramPacket(recvBuf,
							recvBuf.length);

					try {
						socket.receive(packet);
					} catch (SocketException e) {
						System.out.println("Scanner terminated.");
						break;
					}
					
					String message = new String(packet.getData()).trim();
					
					System.out.println("Received message: " + message);

				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
