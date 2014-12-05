package ua.pti.myatm;

public class NotEnoughtMoneyInATMexception extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5957684319476493972L;

	public NotEnoughtMoneyInATMexception(String message) {
		super(message);
	}
}
