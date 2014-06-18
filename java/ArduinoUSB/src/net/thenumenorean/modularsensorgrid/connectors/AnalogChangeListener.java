package net.thenumenorean.modularsensorgrid.connectors;

public interface AnalogChangeListener {
	
	public void onAnalogChange(int pin, int value, long timestamp);

}
