package net.thenumenorean.modularsensorgrid;

import java.net.SocketException;
import java.security.InvalidParameterException;

import net.thenumenorean.modularsensorgrid.populator.UDPGridPopulator;

public class GridCreator {

	private static final int BROADCAST_PORT = 22001;

	/**
	 * Constructs a new EthernetSensorGrid, scanning for Timeout milliseconds or
	 * until it has aquired sensorAmt of sensors.
	 * 
	 * @param timeout
	 *            How long to wait
	 * @param sensors
	 *            if 0 it waits until the timeout is up, or if it is > 0 it
	 *            returns early if it reaches that number
	 * @return a new EthernetSensorGrid.
	 */
	public static ModularSensorGrid constructNewNetworkGrid(long timeout,
			int sensorAmt) {

		if (sensorAmt < 0)
			throw new InvalidParameterException(
					"Sensor amount cannot be less than 0!");

		NetworkedModularSensorGrid grid;
		try {
			grid = new NetworkedModularSensorGrid();
		} catch (SocketException e1) {
			e1.printStackTrace();
			return null;
		}

		UDPGridPopulator pop = new UDPGridPopulator(grid, BROADCAST_PORT);

		pop.start(sensorAmt);
		try {
			if (sensorAmt == 0) {
				Thread.sleep(timeout);
			} else {
				while (pop.running())
					Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		pop.stop();

		return grid;
	}
}
