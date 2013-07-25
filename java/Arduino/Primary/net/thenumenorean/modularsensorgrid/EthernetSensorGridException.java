package net.thenumenorean.modularsensorgrid;

public class EthernetSensorGridException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1321508129263547160L;

	public EthernetSensorGridException() {
		super("Unknown error!");
	}

	public EthernetSensorGridException(String arg0) {
		super(arg0);
	}

	public EthernetSensorGridException(Throwable arg0) {
		super(arg0);
	}

	public EthernetSensorGridException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
