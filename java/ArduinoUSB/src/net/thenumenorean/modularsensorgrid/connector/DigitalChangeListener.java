package net.thenumenorean.modularsensorgrid.connector;

public interface DigitalChangeListener {
	
	public void onDigitalChange(int pin, boolean value, long timestamp);

}
