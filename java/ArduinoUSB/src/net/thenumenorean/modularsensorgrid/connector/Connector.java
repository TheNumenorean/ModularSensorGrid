package net.thenumenorean.modularsensorgrid.connector;

public interface Connector {
	
	public void setDigitalPin(int pin, boolean on);
	public void setPWM(int pin, int value);
	public void setTimeZero();
	
	public void setReportAnalogChanges(int pin, boolean report);
	public void addAnalogChangeListener(AnalogChangeListener acl);
	public void removeAnalogChangeListener(AnalogChangeListener acl);
	public void clearAnalogChangeListeners();
	
	public void setReportDigitalChanges(int pin, boolean report);
	public void addDigitalChangeListener(DigitalChangeListener dcl);
	public void removeDigitalChangeListener(DigitalChangeListener dcl);
	public void clearDigitalChangeListeners();
	
	public void destroy();
	
	
	

}
