package net.thenumenorean.modularsensorgrid.connector;

public interface AnalogChangeListener {
	
	public void onAnalogChange(int pin, int value, long timestamp);

}
