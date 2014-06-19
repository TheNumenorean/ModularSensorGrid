package net.thenumenorean.modularsensorgrid.sensors;

import net.thenumenorean.modularsensorgrid.connector.AnalogChangeListener;
import net.thenumenorean.modularsensorgrid.connector.Connector;
import net.thenumenorean.modularsensorgrid.datacapture.DataCaptureToolHelper;

public class LightSensor extends Sensor {

	private boolean running;
	private int currentValue;
	private long startTime;

	public LightSensor(String name, Connector c) {
		super(name, c);
		running = false;
	}

	@Override
	public void start() {
		startTime = System.currentTimeMillis();
		Connector c = getConnector();
		c.setReportAnalogChanges(0, true);
		c.addAnalogChangeListener(new AnalogChangeListener() {

			@Override
			public void onAnalogChange(int pin, int value, long timestamp) {
				if (pin == 0) {
					currentValue = value;
					DataCaptureToolHelper.addData(getDataCaptureTools(),
							LightSensor.this, "Light Intensity", timestamp,
							value);
					System.out.println(timestamp + "\t" + value);
				}
			}

		});
		running = true;

	}

	@Override
	public void stop() {
		getConnector().clearAnalogChangeListeners();
		getConnector().setReportAnalogChanges(0, false);
		running = false;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

	@Override
	public String getType() {
		return "Light Sensor";
	}

	public int getLightIntensity() {
		return currentValue;
	}

	@Override
	public void setStatusLight(boolean on) {
		super.setStatusLight(on);
		DataCaptureToolHelper.addData(getDataCaptureTools(), this,
				"Status Light", System.currentTimeMillis() - startTime, on);
	}

}
