import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class OtherTEst {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket s = new Socket("192.168.1.177", 80);
		Scanner scan = new Scanner(s.getInputStream());
		while(s.isConnected()){
			
			System.out.println((scan.nextInt() - System.currentTimeMillis()));
		}

	}

}
