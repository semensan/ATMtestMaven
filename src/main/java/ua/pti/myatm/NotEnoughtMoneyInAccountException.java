package ua.pti.myatm;

public class NotEnoughtMoneyInAccountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6462344412993886420L;

	public NotEnoughtMoneyInAccountException(String message) {
		super(message);
	}
}
