package net.thenumenorean.modularsensorgrid;

import java.net.SocketException;

import net.thenumenorean.modularsensorgrid.populator.UDPGridPopulator;

public class GridCreator {
	
	private static final int BROADCAST_PORT = 22001;
	
	public static ModularSensorGrid constructNewNetworkGrid(int scan_time){
		NetworkedModularSensorGrid grid;
		try {
			grid = new NetworkedModularSensorGrid();
		} catch (SocketException e1) {
			e1.printStackTrace();
			return null;
		}

		UDPGridPopulator pop = new UDPGridPopulator(grid, BROADCAST_PORT);
		pop.start();
		
		try {
			Thread.sleep(scan_time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		pop.stop();
		
		return grid;
	}

}
