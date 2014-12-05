package ua.pti.myatm;

public class NoCardInsertedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1408032153025466252L;

	public NoCardInsertedException(String message) {
		super(message);
	}
}
