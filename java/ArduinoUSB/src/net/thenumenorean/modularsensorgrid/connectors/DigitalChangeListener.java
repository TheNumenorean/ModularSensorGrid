package net.thenumenorean.modularsensorgrid.connectors;

public interface DigitalChangeListener {
	
	public void onDigitalChange(int pin, boolean value, long timestamp);

}
