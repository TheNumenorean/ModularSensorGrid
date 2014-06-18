package net.thenumenorean.modularsensorgrid.usb.ardulink;

import org.zu.ardulink.Link;

import net.thenumenorean.modularsensorgrid.connector.AnalogChangeListener;
import net.thenumenorean.modularsensorgrid.connector.Connector;
import net.thenumenorean.modularsensorgrid.connector.DigitalChangeListener;

public class ArdulinkConnector implements Connector {
	
	private String port;
	private Link link;
	
	public ArdulinkConnector(Link l, String port){
		this.port = port;
		link = l;
		//TODO:
	}
	
	public String getPort(){
		return port;
	}

	@Override
	public void setDigitalPin(int pin, boolean on) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPWM(int pin, int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTimeZero() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReportAnalogChanges(int pin, boolean report) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addAnalogChangeListener(AnalogChangeListener acl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAnalogChangeListener(AnalogChangeListener acl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearAnalogChangeListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setReportDigitalChanges(int pin, boolean report) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDigitalChangeListener(DigitalChangeListener dcl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeDigitalChangeListener(DigitalChangeListener dcl) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clearDigitalChangeListeners() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		link.disconnect();
		Link.destroyInstance(link.getName());
	}

}
