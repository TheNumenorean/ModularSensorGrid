package net.thenumenorean.modularsensorgrid;

public class SensorGridException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1321508129263547160L;

	public SensorGridException() {
		super("Unknown error!");
	}

	public SensorGridException(String arg0) {
		super(arg0);
	}

	public SensorGridException(Throwable arg0) {
		super(arg0);
	}

	public SensorGridException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
