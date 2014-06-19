package net.thenumenorean.modularsensorgrid.usb.ardulink;

import java.util.ArrayList;

import net.thenumenorean.modularsensorgrid.connector.AnalogChangeListener;
import net.thenumenorean.modularsensorgrid.connector.Connector;
import net.thenumenorean.modularsensorgrid.connector.DigitalChangeListener;

import org.zu.ardulink.Link;
import org.zu.ardulink.event.AnalogReadChangeEvent;
import org.zu.ardulink.event.AnalogReadChangeListener;
import org.zu.ardulink.event.DigitalReadChangeEvent;
import org.zu.ardulink.event.DigitalReadChangeListener;
import org.zu.ardulink.protocol.IProtocol;

public class ArdulinkConnector implements Connector {

	private String port;
	private Link link;
	private long startTime; // Temporary TODO
	private ArrayList<AnalogListenerConverter> analogListeners;
	private ArrayList<DigitalListenerConverter> digitalListeners;

	public ArdulinkConnector(Link l, String port) {
		this.port = port;
		link = l;
		analogListeners = new ArrayList<AnalogListenerConverter>();
		// TODO:
	}

	public String getPort() {
		return port;
	}

	@Override
	public void setDigitalPin(int pin, boolean on) {
		link.sendPowerPinSwitch(pin, on ? IProtocol.HIGH : IProtocol.LOW);
	}

	@Override
	public void setPWM(int pin, int value) {
		link.sendPowerPinIntensity(pin, value);
	}

	@Override
	public void setTimeZero() {
		link.sendCustomMessage("resetTime");
		startTime = System.currentTimeMillis(); // TODO
	}

	@Override
	public void setReportAnalogChanges(int pin, boolean report) {
		if (report)
			link.startListenAnalogPin(pin);
		else
			link.stopListenAnalogPin(pin);
	}

	@Override
	public void addAnalogChangeListener(final AnalogChangeListener acl) {

		AnalogListenerConverter conv = new AnalogListenerConverter(acl);
		analogListeners.add(conv);
		link.addAnalogReadChangeListener(conv);

	}

	@Override
	public void removeAnalogChangeListener(AnalogChangeListener acl) {
		for (AnalogListenerConverter alc : analogListeners)
			if (alc.getAnalogChangeListener().equals(acl))
				link.removeAnalogReadChangeListener(alc);

	}

	@Override
	public void clearAnalogChangeListeners() {
		for (AnalogListenerConverter alc : analogListeners)
			link.removeAnalogReadChangeListener(alc);
	}

	@Override
	public void setReportDigitalChanges(int pin, boolean report) {
		if (report)
			link.startListenDigitalPin(pin);
		else
			link.stopListenDigitalPin(pin);
	}

	@Override
	public void addDigitalChangeListener(DigitalChangeListener dcl) {
		DigitalListenerConverter conv = new DigitalListenerConverter(dcl);
		digitalListeners.add(conv);
		link.addDigitalReadChangeListener(conv);
	}

	@Override
	public void removeDigitalChangeListener(DigitalChangeListener dcl) {
		for (DigitalListenerConverter dlc : digitalListeners)
			if (dlc.getDigitalChangeListener().equals(dcl))
				link.removeDigitalReadChangeListener(dlc);

	}

	@Override
	public void clearDigitalChangeListeners() {
		for (DigitalListenerConverter dlc : digitalListeners)
			link.removeDigitalReadChangeListener(dlc);

	}

	@Override
	public void destroy() {
		link.disconnect();
		Link.destroyInstance(link.getName());
	}

	private class AnalogListenerConverter implements AnalogReadChangeListener {

		private AnalogChangeListener acl;

		public AnalogListenerConverter(AnalogChangeListener acl) {
			this.acl = acl;
		}

		public AnalogChangeListener getAnalogChangeListener() {
			return acl;
		}

		@Override
		public int getPinListening() {
			return AnalogReadChangeListener.ALL_PINS;
		}

		@Override
		public void stateChanged(AnalogReadChangeEvent arg0) {
			acl.onAnalogChange(arg0.getPin(), arg0.getValue(),
					System.currentTimeMillis() - startTime); // TODO

		}

	}

	private class DigitalListenerConverter implements DigitalReadChangeListener {

		private DigitalChangeListener dcl;

		public DigitalListenerConverter(DigitalChangeListener dcl) {
			this.dcl = dcl;
		}

		public DigitalChangeListener getDigitalChangeListener() {
			return dcl;
		}

		@Override
		public int getPinListening() {
			return AnalogReadChangeListener.ALL_PINS;
		}

		@Override
		public void stateChanged(DigitalReadChangeEvent arg0) {
			dcl.onDigitalChange(arg0.getPin(),
					arg0.getValue() == IProtocol.HIGH,
					System.currentTimeMillis() - startTime); // TODO

		}

	}

}
