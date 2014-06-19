package net.thenumenorean.modularsensorgrid.datacapture;

import java.awt.EventQueue;
import java.util.List;

import net.thenumenorean.modularsensorgrid.sensors.Sensor;

public class DataCaptureToolHelper {

	public static void addData(final List<DataCaptureTool> dcts,
			final Sensor s, final String dataName, final long time,
			final int value) {

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				for (DataCaptureTool dct : dcts) {
					dct.addData(s, dataName, time, value);
				}
			}
		});
	}

	public static void addData(final List<DataCaptureTool> dcts, final Sensor s,
			final String dataName, final long time, final boolean value) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				for (DataCaptureTool dct : dcts) {
					dct.addData(s, dataName, time, value);
				}
			}
		});
	}

	public static void addData(final List<DataCaptureTool> dcts, final Sensor s,
			final String dataName, final long time, final long value) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				for (DataCaptureTool dct : dcts) {
					dct.addData(s, dataName, time, value);
				}
			}
		});
	}

	public static void addData(final List<DataCaptureTool> dcts, final Sensor s,
			final String dataName, final long time, final double value) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				for (DataCaptureTool dct : dcts) {
					dct.addData(s, dataName, time, value);
				}
			}
		});
	}

}
