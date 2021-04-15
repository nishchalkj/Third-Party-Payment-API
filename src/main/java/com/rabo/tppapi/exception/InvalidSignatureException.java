package com.rabo.tppapi.exception;

/**
 * InvalidSignatureException custom exception
 * @author Nishchal
 *
 */
public class InvalidSignatureException extends PaymentInitiationException {

	private static final long serialVersionUID = -7651825140044134482L;

	/**
	 * {@link InvalidSignatureException} constructor.
	 *
	 * @param message
	 *            exception message
	 * 
	 */
	public InvalidSignatureException(final String message) {
		super(message);
	}
	
	/**
	 * {@link InvalidSignatureException} constructor.
	 *
	 * @param message
	 *            exception message
	 * @param statusCode  
	 *            error statuscode
	 *  
	 */
	public InvalidSignatureException(final String message, final int statusCode) {
		super(message,statusCode);
	}

	
}
