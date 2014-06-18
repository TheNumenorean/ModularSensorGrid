package net.thenumenorean.modularsensorgrid.sensors;

import net.thenumenorean.modularsensorgrid.connectors.AnalogChangeListener;
import net.thenumenorean.modularsensorgrid.connectors.Connector;
import net.thenumenorean.modularsensorgrid.datacapture.DataCaptureTool;

public class LightSensor extends Sensor {

	private boolean running;
	private int currentValue;

	public LightSensor(Connector c) {
		super(c);
		running = false;
	}

	@Override
	public void start() {
		Connector c = getConnector();
		c.setReportAnalogChanges(0, true);
		c.addAnalogChangeListener(new AnalogChangeListener(){

			@Override
			public void onAnalogChange(int pin, int value, long timestamp) {
				if(pin == 0)
				currentValue = value;
				
				for(DataCaptureTool dct : LightSensor.this.getDataCaptureTools()){
					
					dct.addData(LightSensor.this, "Light Intensity", timestamp, value);
					
					
					/*EventQueue.invokeLater(new Runnable(){

						@Override
						public void run() {
							dct.addData(LightSensor.this, "Light Intensity", timestamp, value);
							
						}
					});*/
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
	
	public int getLightIntensity(){
		return currentValue;
	}

}
